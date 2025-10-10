package com.yomahub.tlog.example.feign.controller;

import com.yomahub.tlog.example.feign.domain.ConsumerDomain;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//自定义注解实现feign远程调用重试功能，为不同调用实现不同的重试机制！
@RestController
public class TlogFeignConsumerController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConsumerDomain consumerDomain;


    @RequestMapping("/hi")
    public String sayHello(@RequestParam String name){
        return consumerDomain.sayHello(name);
    }
}
