package com.woniu.demo.annotation;

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

    /**
     * @return 操作描述
     */
    String value() default "";

}
