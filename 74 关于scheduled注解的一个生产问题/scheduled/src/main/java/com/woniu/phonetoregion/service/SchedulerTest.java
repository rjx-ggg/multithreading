package com.woniu.phonetoregion.service;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 生产问题
 */
@Component
public class SchedulerTest {

    // 初始化5秒，之后每隔5秒执行一次
    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    public void scheduled1() throws InterruptedException {
        while (true) {
            System.out.println("I am scheduled1, thread = " + Thread.currentThread());
            Thread.sleep(5000);
        }
    }

    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    public void scheduled2() {
        System.out.println("I am scheduled2, thread = " + Thread.currentThread());
    }

    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    public void scheduled3() {
        System.out.println("I am scheduled3, thread = " + Thread.currentThread());
    }
}
