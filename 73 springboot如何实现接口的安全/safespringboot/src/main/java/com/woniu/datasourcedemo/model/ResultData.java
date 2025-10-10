package com.woniu.datasourcedemo.model;


import java.io.Serializable;

public class ResultData<T>  implements Serializable {

    // 标识代码,2000表示成功，其它数值表示出错
    private String code;
    // 返回的数据
    private T data;
    // 提示信息,供报错时使用
    private String message;

    public ResultData(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> ResultData<T> fail(String code, String message) {
        return new ResultData(code, message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
