package com.woniu.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScheduleService {


    @Autowired
    MonitorService monitorService;


    @Scheduled(cron = "1/5 * * * * *")
    public void testSchedule() {
       log.info("定时任务开始, 当前时间：{}",System.currentTimeMillis());
        monitorService.monitor();
    }

}
