package com.woniu.connection.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
