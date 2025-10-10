package com.woniu.tlog.example.controller;


import com.woniu.tlog.example.service.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//SpringBoot 如何实现异步编程？大神都是这么玩的！
@RestController
@RequestMapping("/async")
@Slf4j
public class AsyncController {
    @Autowired
    private AsyncTask asyncTask;

    @RequestMapping("/task")
    public void task() throws InterruptedException {
        long t1 = System.currentTimeMillis();
            asyncTask.doTask1();
            asyncTask.doTask2();
        Thread.sleep(1000);
        long t2 = System.currentTimeMillis();
        log.info("main cost {} ms", t2 - t1);
    }
}