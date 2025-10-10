package com.woniu.scheduleTask.config;


import com.woniu.scheduleTask.service.QuartzTestJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 问题是配置写死了，没有办法动态调整，所以我们开始写接口，把上面这个任务配置类去掉。
 */
//@Configuration
public class QuartzTestJobConfig {
    @Bean
    public JobDetail quartzTestJobDetail() {
        return JobBuilder.newJob(QuartzTestJob.class)
                .withIdentity(QuartzTestJob.class.getSimpleName())
                .storeDurably()
                .usingJobData("data", "test")
                .build();
    }

    @Bean
    public Trigger quartzTestJobTrigger() {
        // 0/1 * * * * ?
        return TriggerBuilder.newTrigger()
                .forJob(QuartzTestJob.class.getSimpleName())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                .build();
    }
}
