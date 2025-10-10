package com.woniu.threaddemo;

import lombok.Data;

@Data
public class BizApiException extends RuntimeException {
    public BizApiException() {
    }

    public BizApiException(String message) {
        super(message);
    }
}
