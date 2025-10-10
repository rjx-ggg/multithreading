package com.woniu;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(exclude = MybatisAutoConfiguration.class)
public class WoNiuApplication {

    public static void main(String[] args) {
        SpringApplication.run(WoNiuApplication.class, args);
    }


}
