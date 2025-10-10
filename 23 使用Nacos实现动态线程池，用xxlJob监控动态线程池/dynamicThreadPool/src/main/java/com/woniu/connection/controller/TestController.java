package com.woniu.connection.controller;

import com.woniu.connection.config.ThreadPoolProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController类
 */
@RestController
public class TestController {
    @Autowired
    private ThreadPoolProperty threadPoolProperty;
    @GetMapping("/get")
    public String getValue(){
        return threadPoolProperty.getMaximumPoolSize() +"  "+
        threadPoolProperty.getCorePoolSize()+"  "+
                threadPoolProperty.getQueueCapacity();
    }
}
