package com.woniu.demo.retry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetryResult {
    private Boolean retry;
    private Object obj;


    public static RetryResult ofResult(Boolean retry, Object obj) {
        return new RetryResult(retry, obj);
    }

    public static RetryResult ofResult(Boolean retry) {
        return new RetryResult(retry, null);
    }
}