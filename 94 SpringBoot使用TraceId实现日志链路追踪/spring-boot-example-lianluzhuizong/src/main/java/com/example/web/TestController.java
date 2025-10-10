package com.example.web;

/**
 * @description:
 * @author: 程序员蜗牛
 * @date: 2025/3/13
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

//  SpringBoot使用TraceId实现日志链路追踪！
    @GetMapping(value = "/signLog")
    public String signLog() {
        log.info("这是一行info日志");
        return "success";
    }


    @GetMapping(value = "/signAsynLog")
    @Async("taskExecutor")
    public String signAsynLog() {
        log.info("这是一行异步info日志");
        return "success";
    }


}