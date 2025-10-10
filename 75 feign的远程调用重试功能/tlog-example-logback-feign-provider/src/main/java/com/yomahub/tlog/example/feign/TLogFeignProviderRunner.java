package com.yomahub.tlog.example.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * springboot实现分布式链路日志追踪！用Tlog就行了！
 */
@SpringBootApplication
public class TLogFeignProviderRunner {

    public static void main(String[] args) {
        SpringApplication.run(TLogFeignProviderRunner.class, args);
    }
}
