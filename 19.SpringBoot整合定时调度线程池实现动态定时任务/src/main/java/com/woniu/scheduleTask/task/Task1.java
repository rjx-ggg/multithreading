package com.woniu.scheduleTask.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component("taskDemo")
public class Task1 {
    public void taskByParams(String params) {
        log.info("taskByParams执行时间:{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        log.info("taskByParams执行有参示例任务：{}", params);
    }

    public void taskNoParams() {
        log.info("taskByParams执行时间:{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        log.info("taskNoParams执行无参示例任务");
    }

    public void test(String params) {
        log.info("test执行时间:{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        log.info("test执行有参示例任务：{}", params);
    }
}