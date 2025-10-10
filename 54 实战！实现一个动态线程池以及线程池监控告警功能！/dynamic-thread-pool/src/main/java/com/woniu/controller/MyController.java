package com.woniu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;

@RestController
public class MyController {

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * 实战！实现一个动态线程池以及线程池监控功能！
     * @return
     */
    @RequestMapping("/pool")
    public String pool() {
        int core = threadPoolExecutor.getCorePoolSize();
        int max = threadPoolExecutor.getMaximumPoolSize();
        int queue = threadPoolExecutor.getQueue().remainingCapacity();
        return core + " -- " + max + " -- " + queue;
    }
}