package com.example.jwt.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResResult<T> {

    private String smg;
    private T data;


    public static ResResult getSysError(String msg) {
        return new ResResult(msg, null);
    }

    public static <T> ResResult success(T t) {
        return new ResResult(null, t);
    }

    public static <T> ResResult success() {
        return new ResResult("ok",null);
    }
}
