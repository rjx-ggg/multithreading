package com.woniu.datasourcedemo.model;

/**
 * @className: ReturnCode
 * @author: woniu
 * @date: 2023/2/22
 **/
public enum ReturnCode {

    ILLEGAL_HEADER("请求头错误","400"),
    REPLAY_ERROR("重放错误","405"),
    ARGUMENT_ERROR("验签失败","409")
    ;

    private String message;
    private String code;


    ReturnCode(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
