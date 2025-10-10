package com.woniu.dynamicsql;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.woniu.dynamicsql.mapper")
@SpringBootApplication
public class DynamicsqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamicsqlApplication.class, args);
	}

}
