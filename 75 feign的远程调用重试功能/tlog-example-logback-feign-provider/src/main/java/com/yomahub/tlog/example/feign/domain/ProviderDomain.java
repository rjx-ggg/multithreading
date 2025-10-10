package com.yomahub.tlog.example.feign.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ProviderDomain {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private ExecutorService pool = Executors.newFixedThreadPool(5);

    public String sayHello(String name){
        log.info("invoke ProviderDomain sayHello");
        new AsynDomain().start();
        pool.submit(new AsynPoolDomain());
        return "hello," + name;
    }
}
