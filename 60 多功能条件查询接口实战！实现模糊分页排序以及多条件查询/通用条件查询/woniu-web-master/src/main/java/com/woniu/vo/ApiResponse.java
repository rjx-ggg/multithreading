package com.woniu.vo;

import lombok.Data;

@Data
public class ApiResponse<T> {

    private T data;
    private String message;
    private String code;

    public ApiResponse(T data, String message, String code) {
        this.data = data;
        this.message = message;
        this.code = code;
    }


    public static<T> ApiResponse<T> fail(T data, MessageEnum messageEnum) {
        return new ApiResponse(data, messageEnum.getMessage(), messageEnum.getCode());
    }

    public static<T> ApiResponse<T> ok(T data) {
        return new ApiResponse(data, "响应正常", "200");
    }
}
