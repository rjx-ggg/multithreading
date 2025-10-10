package com.wm.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ExportExcelDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExportExcelDataApplication.class, args);
    }
}
