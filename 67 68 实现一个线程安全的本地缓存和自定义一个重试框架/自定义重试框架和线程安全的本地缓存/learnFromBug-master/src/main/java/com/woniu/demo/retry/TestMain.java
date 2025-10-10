package com.woniu.demo.retry;


import lombok.extern.slf4j.Slf4j;

/**
 * 自定义重试工具类
 */
@Slf4j
public class TestMain {
    public static void main(String[] args) {
         RetryExecutor.execute(3, new Callback() {
            @Override
            public RetryResult doProcess() {
                // 执行需要重试的逻辑
                log.info("retry");
                // 如果需要重试，返回 RetryResult.ofResult(true)
                // 如果不需要重试，返回 RetryResult.ofResult(false, result)
                return RetryResult.ofResult(true);
            }
        });
    }
}
