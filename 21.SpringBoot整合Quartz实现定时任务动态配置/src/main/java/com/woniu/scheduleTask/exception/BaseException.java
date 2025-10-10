package com.woniu.scheduleTask.exception;


public class BaseException extends RuntimeException {
    String message;
    public BaseException(String message) {
        this.message = message;
    }
}
