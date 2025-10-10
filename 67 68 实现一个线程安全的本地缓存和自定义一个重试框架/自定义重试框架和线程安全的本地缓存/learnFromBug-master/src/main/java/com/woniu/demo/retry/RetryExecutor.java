package com.woniu.demo.retry;

public class RetryExecutor {
    public static Object execute(int retryCount, Callback callback) {
        for (int curRetryCount = 0; curRetryCount < retryCount; curRetryCount++) {
            RetryResult retryResult = callback.doProcess();
            if (retryResult.getRetry()) {
                continue;
            }
            return retryResult.getObj();
        }
        return null;
    }
}