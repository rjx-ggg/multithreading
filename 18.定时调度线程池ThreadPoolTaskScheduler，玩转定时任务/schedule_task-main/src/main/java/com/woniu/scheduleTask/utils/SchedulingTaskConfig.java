package com.woniu.scheduleTask.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author: woniu
 * @description: 需要添加ThreadPoolTaskScheduler线程池才能正常通过@Autowired使用
 **/
@Configuration
public class SchedulingTaskConfig {

    @Bean(name = "taskSchedulerPool")
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(60);
        taskScheduler.setThreadNamePrefix("scheduled2222-");
        taskScheduler.setAwaitTerminationSeconds(3000);
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        return taskScheduler;
    }
}
