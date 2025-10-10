package com.woniu.scheduleTask.service;


import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * SpringBoot整合Quartz实现定时任务动态配置
 */
@Component
@Slf4j
public class QuartzTestJob extends QuartzJobBean {
    @Override
    protected void executeInternal(org.quartz.JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Quartz Test Job");
    }
}

