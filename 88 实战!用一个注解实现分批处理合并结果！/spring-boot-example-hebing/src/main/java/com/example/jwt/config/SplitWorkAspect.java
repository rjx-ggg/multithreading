package com.example.jwt.config;


import com.example.jwt.anno.NeedSplitParam;
import com.example.jwt.anno.SplitWorkAnnotation;
import com.example.jwt.inte.HandleReturn;
import com.example.jwt.thread.CustomThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

@Aspect
@Component
@Slf4j
public class SplitWorkAspect {

    /**
     * 切入点表达式，拦截方法上有@NeedSplitParaAnnotation注解的所有方法
     */
    @Pointcut("@annotation( com.example.jwt.anno.SplitWorkAnnotation)")
    public void needSplit() {
    }


    /**
     * @param pjp
     * @return {@link Object}
     * @throws Throwable
     */
    @Around("needSplit()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        SplitWorkAnnotation splitWorkAnnotation = targetMethod.getAnnotation(SplitWorkAnnotation.class);
        Object[] args = pjp.getArgs();
        int splitLimit = splitWorkAnnotation.splitLimit();
        int splitGroupNum = splitWorkAnnotation.splitGroupNum();
        if (args == null || args.length == 0 || splitLimit <= splitGroupNum) {
            return pjp.proceed();
        }
        int needSplitParamIndex = -1;
        for (int i = 0; i < targetMethod.getParameters().length; i++) {
            Parameter parameter = targetMethod.getParameters()[i];
            NeedSplitParam needSplitParam = parameter.getAnnotation(NeedSplitParam.class);
            if (needSplitParam != null) {
                needSplitParamIndex = i;
                break;
            }
        }
        if (needSplitParamIndex == -1) {
            return pjp.proceed();
        }
        Object needSplitParam = args[needSplitParamIndex];
        //只能处理Object[] 和 Collection
        if (!(needSplitParam instanceof Object[]) && !(needSplitParam instanceof List) && !(needSplitParam instanceof Set)) {
            return pjp.proceed();
        }
        //如果目标参数长度小于拆分下限跳过
        boolean notMeetSplitLen = (needSplitParam instanceof Object[] && ((Object[]) needSplitParam).length < splitLimit)
                || (needSplitParam instanceof List && ((List) needSplitParam).size() < splitLimit)
                || (needSplitParam instanceof Set && ((Set) needSplitParam).size() < splitLimit);
        if (notMeetSplitLen) {
            return pjp.proceed();
        }
        // 去重，这一步看情况也可以不要
        if (needSplitParam instanceof List) {
            List<?> list = (List<?>) needSplitParam;
            if (list.size() > 1) {
                needSplitParam = new ArrayList<>(new HashSet<>(list));
            }
        }
        //算出拆分成几批次
        int batchNum = getBatchNum(needSplitParam, splitGroupNum);
        if (batchNum == 1) {
            return pjp.proceed();
        }
        CompletableFuture<?>[] futures = new CompletableFuture[batchNum];
        CustomThreadPoolConfig customThreadPoolConfig = new CustomThreadPoolConfig(5);
        ExecutorService threadPool = customThreadPoolConfig.createThreadPool();
        if (threadPool == null) {
            return pjp.proceed();
        }
        try {
            for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
                int finalNeedSplitParamIndex = needSplitParamIndex;
                int finalCurrentBatch = currentBatch;
                Object finalNeedSplitParam = needSplitParam;
                futures[currentBatch] = CompletableFuture.supplyAsync(() -> {
                    Object[] dest = new Object[args.length];
                    //这一步很重要！！！因为多线程运行不能用原理的参数列表了，不然会导致混乱
                    System.arraycopy(args, 0, dest, 0, args.length);
                    try {
                        //将其他参数保持不变，将需要拆分的参数替换为part参数
                        dest[finalNeedSplitParamIndex] = getPartParam(finalNeedSplitParam, splitGroupNum, finalCurrentBatch);
                        return pjp.proceed(dest);
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                }, threadPool);
            }
            CompletableFuture<Void> all = CompletableFuture.allOf(futures);
            all.get();
            Class<? extends HandleReturn> handleReturn = splitWorkAnnotation.handlerReturnClass();
            List<Object> resultList = new ArrayList<>(futures.length);
            for (CompletableFuture<?> future : futures) {
                resultList.add(future.get());
            }
            //获取到每个part的结果然后调用处理函数
            return handleReturn.getDeclaredMethods()[0].invoke(handleReturn.getDeclaredConstructor().newInstance(), resultList);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取批次数目
     *
     * @param needSplitParam1
     * @param splitGroupNum
     * @return {@link Integer}
     */
    public Integer getBatchNum(Object needSplitParam1, Integer splitGroupNum) {
        if (needSplitParam1 instanceof Object[]) {
            Object[] splitParam = (Object[]) needSplitParam1;
            return splitParam.length % splitGroupNum == 0 ? splitParam.length / splitGroupNum : splitParam.length / splitGroupNum + 1;
        } else if (needSplitParam1 instanceof Collection) {
            Collection<?> splitParam = (Collection<?>) needSplitParam1;
            return splitParam.size() % splitGroupNum == 0 ? splitParam.size() / splitGroupNum : splitParam.size() / splitGroupNum + 1;
        } else {
            return 1;
        }
    }

    /**
     * 获取当前批次参数
     *
     * @param needSplitParam
     * @param splitGroupNum
     * @param batch
     * @return {@link Object}
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Object getPartParam(Object needSplitParam, Integer splitGroupNum, Integer batch) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        if (needSplitParam instanceof Object[]) {
            Object[] splitParam = (Object[]) needSplitParam;
            int end = Math.min((batch + 1) * splitGroupNum, splitParam.length);
            return Arrays.copyOfRange(splitParam, batch * splitGroupNum, end);
        } else if (needSplitParam instanceof List) {
            List<?> splitParam = (List<?>) needSplitParam;
            int end = Math.min((batch + 1) * splitGroupNum, splitParam.size());
            return splitParam.subList(batch * splitGroupNum, end);
        } else if (needSplitParam instanceof Set) {
            List splitParam = new ArrayList<>((Set) needSplitParam);
            int end = Math.min((batch + 1) * splitGroupNum, splitParam.size());
            //参数具体化了
            Set<?> set = (Set<?>) needSplitParam.getClass().getDeclaredConstructor().newInstance();
            set.addAll(splitParam.subList(batch * splitGroupNum, end));
            return set;
        } else {
            return null;
        }
    }
}