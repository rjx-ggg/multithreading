package com.woniu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author woniu
 * @date 2024 2 19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecuritySupport {
    /**
     * 项目默认加解密实现类encryAdecryImpl
     * */
    String securityHandler() default "encryAdecryImpl";

}
