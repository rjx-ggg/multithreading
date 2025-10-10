package com.woniu.demo.retry;

/**
 * @className: Callback
 * @author: woniu
 * @date: 2024/4/9
 **/
public abstract class Callback {
    public abstract RetryResult doProcess();
}