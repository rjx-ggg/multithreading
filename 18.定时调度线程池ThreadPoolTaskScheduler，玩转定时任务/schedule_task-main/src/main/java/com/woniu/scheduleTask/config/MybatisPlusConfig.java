package com.woniu.scheduleTask.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: woniu
 * @description:
 **/
@Configuration
@MapperScan(value = "com.woniu.scheduleTask.**.mapper.**")
public class MybatisPlusConfig {
    /**
     * 配置分页3.2.0
     * @return 
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
