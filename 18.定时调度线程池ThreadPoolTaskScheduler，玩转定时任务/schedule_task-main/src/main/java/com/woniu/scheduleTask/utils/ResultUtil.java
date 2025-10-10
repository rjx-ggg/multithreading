package com.woniu.scheduleTask.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: woniu
 * @Description:
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultUtil<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public static <T> ResultUtil<T> success(){
        return ResultUtil.<T>builder().code(1000).msg("成功").build();
    }
    public static <T> ResultUtil<T> success(T data){
        return ResultUtil.<T>builder().code(1000).msg("成功").data(data).build();
    }

    public static <T> ResultUtil<T> error(String msg){
        return ResultUtil.<T>builder().code(5000).msg(msg).data(null).build();
    }

    public static <T> ResultUtil<T> error(int code,String msg){
        return ResultUtil.<T>builder().code(code).msg(msg).build();
    }
}