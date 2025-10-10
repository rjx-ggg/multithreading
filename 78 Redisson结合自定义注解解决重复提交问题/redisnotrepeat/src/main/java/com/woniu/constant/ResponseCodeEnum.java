package com.woniu.constant;

public enum ResponseCodeEnum {

    SUCCESS("200"),
    FAIL("400");

    private String code;

    ResponseCodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
