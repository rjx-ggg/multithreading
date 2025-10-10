package com.woniu.async.controller;

import com.woniu.async.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 万能通用的异步实战 涉及多线程 mq 数据库的整合
 */
@RestController
@RequestMapping("/test/async")
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping (value = "/exec/{id}")
    public String exec(@PathVariable("id") Long id) {
        testService.exec(id);
        return "ok";
    }

}
