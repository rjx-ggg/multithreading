package com.yomahub.tlog.example.feign.domain;

import com.yomahub.tlog.example.feign.client.TLogFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerDomain {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TLogFeignClient tLogFeignClient;

    public String sayHello(String name){
        return tLogFeignClient.sayHello(name);
    }
}
