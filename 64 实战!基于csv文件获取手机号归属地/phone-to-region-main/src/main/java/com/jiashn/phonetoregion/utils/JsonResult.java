package com.jiashn.phonetoregion.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: woniu
 * @description:
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> JsonResult<T> success(T data){
        return JsonResult.<T>builder()
                .code(200)
                .msg("成功")
                .data(data).build();
    }
    public static <T> JsonResult<T> success(){
        return JsonResult.<T>builder()
                .code(200)
                .msg("成功")
                .data(null).build();
    }

    public static <T> JsonResult<T> fails(String msg){
        return JsonResult.<T>builder()
                .code(500)
                .msg(msg)
                .data(null).build();
    }
    public static <T> JsonResult<T> fails(int code,String msg){
        return JsonResult.<T>builder()
                .code(code)
                .msg(msg)
                .data(null).build();
    }
}
