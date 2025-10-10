package com.example.shardingsphere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * Application
 * </p>
 *  如何在 Spring Boot 中快速实现数据的加解密功能
 */
@SpringBootApplication
@MapperScan("com.example.shardingsphere.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
