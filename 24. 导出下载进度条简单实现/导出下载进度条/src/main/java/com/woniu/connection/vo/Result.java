package com.woniu.connection.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private T data;

    private String message;

    private String code;

    public static <T> Result<T> success(T data) {
        return new Result<>(data, "成功", "200");
    }


}
