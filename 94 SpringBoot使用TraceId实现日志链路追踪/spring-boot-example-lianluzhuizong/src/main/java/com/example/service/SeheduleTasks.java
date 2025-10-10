package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @description:
 * @author: 程序员蜗牛
 * @date: 2025/3/13
 */
@Service
@Slf4j
public class SeheduleTasks {

    /**
     * 1分钟执行一次
     */
    @Scheduled(cron = "0/5 * * * * ? ")
    public void testTask() {
        log.info("执行定时任务>" + new Date());
    }

}