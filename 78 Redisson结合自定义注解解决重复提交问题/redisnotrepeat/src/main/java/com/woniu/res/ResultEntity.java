package com.woniu.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultEntity<T> {

    private String code;

    private String msg;

    private T data;

    public static ResultEntity fail(String code, String msg) {
        return new ResultEntity(code, msg, null);
    }


}