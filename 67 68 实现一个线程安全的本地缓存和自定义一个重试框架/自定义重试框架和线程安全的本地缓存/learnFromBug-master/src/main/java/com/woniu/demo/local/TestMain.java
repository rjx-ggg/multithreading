package com.woniu.demo.local;

import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * ConcurrentHashMap与ScheduledThreadPoolExecutor来实现一个线程安全的本地缓存
 */
@Slf4j
public class TestMain {

    public static void main(String[] args) throws InterruptedException {
        LocalCache<String> localCache = new DefaultLocalCache<>();
        localCache.put("woniu","woniuge",5);
        log.info("value1:{}",localCache.get("woniu"));
        TimeUnit.SECONDS.sleep(6);
        log.info("value2:{}",localCache.get("woniu"));
    }
}
