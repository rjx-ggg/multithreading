package com.example.jwt.anno;

import com.example.jwt.inte.HandleReturn;
import com.example.jwt.web.MergeFunction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SplitWorkAnnotation {


    /**
     * 返回值处理
     *
     * @return {@link Class}<{@link ?} {@link extends} {@link HandleReturn}>
     */
    Class<? extends HandleReturn> handlerReturnClass() default MergeFunction.class;

    /**
     * 超过多少开始拆分 >
     *
     * @return int
     */
    int splitLimit() default 1000;

    /**
     * 拆分后每组多少
     *
     * @return int
     */
    int splitGroupNum() default 100;
}