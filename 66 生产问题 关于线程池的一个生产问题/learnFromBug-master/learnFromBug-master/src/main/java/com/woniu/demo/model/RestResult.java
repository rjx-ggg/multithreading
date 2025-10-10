package com.woniu.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestResult<T> {
    private T data;
    private String code;
    private String msg;


    public static RestResult<String> success(String msg) {
        return new RestResult<>(null,"200",msg);
    }
}
