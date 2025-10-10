package com.yomahub.tlog.example.feign.inteferce;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @className: Backoff
 * @author: Kevin
 * @date: 2023/3/22
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Backoff {
    long delay() default 1000L;

    long maxDelay() default 0L;

    double multiplier() default 0.0D;

}


