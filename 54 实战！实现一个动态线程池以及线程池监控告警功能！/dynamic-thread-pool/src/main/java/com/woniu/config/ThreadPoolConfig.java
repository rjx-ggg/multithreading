package com.woniu.config;

import com.woniu.dynamic.DynamicThreadPoolExecutor;
import com.woniu.dynamic.ResizeLinkedBlockingQueue;
import com.woniu.properties.ThreadPoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {

    @Autowired
    private ThreadPoolProperties threadPoolProperties;

    /**
     * J.U.C提供的线程池
     * @return
     */
    //@RefreshScope
    @Bean
    public ThreadPoolExecutor threadPoolExecutor () {
        //基于Executor框架实现线程池
        ThreadPoolExecutor threadPoolExecutor = new DynamicThreadPoolExecutor(
                threadPoolProperties.getCorePoolSize(),
                threadPoolProperties.getMaximumPoolSize(),
                15,
                TimeUnit.SECONDS,
                new ResizeLinkedBlockingQueue<Runnable>(threadPoolProperties.getWorkQueueSize()),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        return threadPoolExecutor;
    }
}