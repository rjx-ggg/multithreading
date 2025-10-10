package com.example.rabbitmq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>
 * RabbitConfig
 * </p>
 * @author 程序员蜗牛
 */
@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
@ComponentScan(basePackages = "com.example.rabbitmq")
@MapperScan(basePackages = "com.example.rabbitmq.**.dao")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
