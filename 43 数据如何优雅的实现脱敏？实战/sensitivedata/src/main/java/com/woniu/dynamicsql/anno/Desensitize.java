package com.woniu.dynamicsql.anno;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.woniu.dynamicsql.config.DesensitizeSerializer;
import com.woniu.dynamicsql.constant.DesensitizeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizeSerializer.class)
public @interface Desensitize {
    /**
     * 脱敏类型
     */
    DesensitizeType type() default DesensitizeType.CUSTOM;

    /**
     * 开始位置(包含)
     */
    int startIndex() default 0;

    /**
     * 结束位置(不包含)
     */
    int endIndex() default 0;
}
