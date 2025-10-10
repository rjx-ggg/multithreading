package com.woniu.demo.annotation;

import com.woniu.demo.service.convert.Convert;

import java.lang.annotation.*;

/**
 * @className: OperationLog
 * @author: woniu
 * @date: 2024/1/6
 **/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLog {

    Class<? extends Convert> convert();

    /**
     * @return 操作描述
     */
    String value() default "";

}
