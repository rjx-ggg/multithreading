package com.woniu.aop;


import com.woniu.anno.NotRepeatDuplication;
import com.woniu.constant.ResponseCodeEnum;
import com.woniu.redisutil.RedisLock;
import com.woniu.res.ResultEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 防重复提交注解的实现，使用AOP。
 * </p>
 */
@Aspect
@Component
public class LockMethodAOP {
    private static final Logger log = LoggerFactory.getLogger(LockMethodAOP.class);

    @Resource
    private RedisLock redisLock;


    @Around("execution(public * *(..)) && @annotation(notRepeatDuplication)")
    public Object interceptor(ProceedingJoinPoint pjp, NotRepeatDuplication notRepeatDuplication) throws Throwable {
        // 这边lockKey 可以由 登录账号 + 类名 + 方法名 + 关键参数组合而来
        final String lockKey = generateKey(pjp);
        // 上锁
        boolean success = redisLock.Rlock(lockKey, notRepeatDuplication.delaySeconds(), TimeUnit.SECONDS);
        if (!success) {
            // 这里也可以改为自己项目自定义的异常抛出
            return ResponseEntity.badRequest().body(ResultEntity.fail(ResponseCodeEnum.FAIL.getCode(), "操作太频繁"));
        }
        return pjp.proceed();
    }

    private String generateKey(ProceedingJoinPoint pjp) {
        StringBuilder sb = new StringBuilder();
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        sb.append(pjp.getTarget().getClass().getName())//类名
                .append(method.getName());//方法名
        for (Object o : pjp.getArgs()) {
            sb.append(o.toString());
        }
        return DigestUtils.md5DigestAsHex(sb.toString().getBytes(Charset.defaultCharset()));
    }

}