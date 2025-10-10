package com.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @description:
 * @author: 程序员蜗牛
 * @date: 2025/3/13
 */
@Aspect   //定义一个切面
@Configuration
public class SeheduleTaskAspect {

    // 定义定时任务切点Pointcut
    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void seheduleTask() {
    }

    @Around("seheduleTask()")
    public void doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            String traceId = UUID.randomUUID().toString().replace("-", "");
            //用于日志链路追踪，logback配置:%X{traceId}
            MDC.put("traceId", traceId);
            //执行定时任务方法
            joinPoint.proceed();
        } finally {
            //请求处理完成后，清除MDC中的traceId，以免造成内存泄漏
            MDC.remove("traceId");
        }
    }

}