package com.woniu.dynamic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class DynamicThreadPoolExecutorManager {

    @Autowired
    private ApplicationContext applicationContext;

    public synchronized void refreshThreadPoolExecutor(Properties properties) {
        applicationContext.getBeansOfType(DynamicThreadPoolExecutor.class).forEach((beanName, executor) -> {
            executor.setCorePoolSize(Integer.parseInt(properties.getProperty("thread.pool.core-pool-size", "16")));
            executor.setMaximumPoolSize(Integer.parseInt(properties.getProperty("thread.pool.maximum-pool-size", "16")));
            if (executor.getQueue() instanceof ResizeLinkedBlockingQueue) {
                ((ResizeLinkedBlockingQueue)executor.getQueue()).setCapacity(Integer.parseInt(properties.getProperty("thread.pool.work-queue-size", "1024")));
            }
        });
    }
}