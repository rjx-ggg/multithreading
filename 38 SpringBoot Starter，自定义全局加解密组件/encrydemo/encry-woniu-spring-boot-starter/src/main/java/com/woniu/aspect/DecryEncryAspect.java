package com.woniu.aspect;

import com.woniu.annotation.SecuritySupport;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author woniu
 * @date 2024 2 19
 */
@Aspect
@Slf4j
@Component
@ConditionalOnProperty(name = "encryption.aop", havingValue = "true")
public class DecryEncryAspect {
    @Around("@annotation(support)")
    public Object around(ProceedingJoinPoint point, SecuritySupport support) throws Throwable {
        log.info("=========>进度到切面里了");
        String name = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        log.info("[类名]:{},[方法]:{}", name, methodName);
        return point.proceed();
    }
}