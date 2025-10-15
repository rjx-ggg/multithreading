package com.springboot.sample.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ExecutorServiceConfiguration {

    /**
     * 自定义线程池配置
     * 核心线程数：2 * CPU 核心数 + 1
     * 最大线程数：CPU 核心数 * 5
     * 线程空闲时间：5 分钟
     * 队列：SynchronousQueue
     * 拒绝策略：CallerRunsPolicy
     * @return
     */
    @Bean
    public ExecutorService executorService() {
//        return new ThreadPoolExecutor(
//                2 * coreSize + 1,
//                coreSize * 5,
//                5L,
//                TimeUnit.MINUTES,
//                new LinkedBlockingQueue<Runnable>(1024));
        int coreSize = Runtime.getRuntime().availableProcessors();
        return new ThreadPoolExecutor(
                2 * coreSize + 1,
                coreSize * 5,
                5L,
                TimeUnit.MINUTES,
                new SynchronousQueue<>(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }


}
