package com.woniu.vo;

/**
 * @className: MessageEnum
 * @author: woniu
 * @date: 2023/6/25
 **/


public enum MessageEnum {
    ParamException("400","参数错误！");

    private String code;
    private String message;

    MessageEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
