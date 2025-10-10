package com.woniu.connection.controller;

import com.woniu.connection.product.TestProducerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/product")
@RestController
public class TestProductController {

    @Resource(name = "testProducerService")
    TestProducerService testProducerService;


    @GetMapping("/send")
    @ResponseBody
    public String send() {
        testProducerService.send("哈喽 我来了!");
        return "ok";
    }

}
