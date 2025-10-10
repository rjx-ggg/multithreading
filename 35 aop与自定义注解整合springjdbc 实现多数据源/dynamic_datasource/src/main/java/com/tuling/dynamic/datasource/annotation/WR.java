package com.tuling.dynamic.datasource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * @Author 蜗牛
 */
@Target({ElementType.METHOD,ElementType.TYPE})
// 保留方式
@Retention(RetentionPolicy.RUNTIME)
public @interface WR {
    String value() default "W";
}
