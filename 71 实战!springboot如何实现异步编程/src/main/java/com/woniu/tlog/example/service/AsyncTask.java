package com.woniu.tlog.example.service;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AsyncTask {

    @SneakyThrows
    @Async("defaultPoolTaskExecutor")
//    @Async
    public void doTask1() {
        long t1 = System.currentTimeMillis();
        Thread.sleep(2000);
        long t2 = System.currentTimeMillis();
        log.info("task1 cost {} ms,thread name {}" , (t2-t1),Thread.currentThread().getName());
    }

    @SneakyThrows
    @Async("otherTaskExecutor")
//    @Async
    public void doTask2() {
        long t1 = System.currentTimeMillis();
        Thread.sleep(3000);
        long t2 = System.currentTimeMillis();
        log.info("task2 cost {} ms,thread name {}" , (t2-t1),Thread.currentThread().getName());
    }


}