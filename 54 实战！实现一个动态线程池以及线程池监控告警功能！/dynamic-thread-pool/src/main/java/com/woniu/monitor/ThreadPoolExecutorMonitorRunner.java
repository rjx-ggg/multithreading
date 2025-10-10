package com.woniu.monitor;

import com.woniu.dynamic.DynamicThreadPoolExecutor;
import com.woniu.entity.ThreadRuntimeInfo;
import com.woniu.service.ThreadRuntimeInfoService;
import com.woniu.util.CalculateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ThreadPoolExecutorMonitorRunner implements ApplicationRunner {

    //启动一个定时任务去采集线程池的运行数据
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor  = new ScheduledThreadPoolExecutor(1, (Runnable r) -> {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        Thread thread = new Thread(r, "monitor-thread-pool-" + atomicInteger.getAndIncrement());
        thread.setDaemon(true);
        return thread;
    });

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private ThreadRuntimeInfoService threadRuntimeInfoService;

    public static final long initialDelay = 5000L;

    public static final long delay = 5000L;

    @Value("${spring.application.name}")
    private String itemName;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
                    //采集数据
                    collectThreadPoolRuntimeInfo();
                },
                initialDelay,
                delay,
                TimeUnit.MILLISECONDS);
    }

    /**
     * 采集线程池运行时数据
     */
    public void collectThreadPoolRuntimeInfo() {
        Map<String, DynamicThreadPoolExecutor> beansOfTypeMap = applicationContext.getBeansOfType(DynamicThreadPoolExecutor.class);
        if (CollectionUtils.isEmpty(beansOfTypeMap)) {
            //当前项目没有线程池在运行，不需要采集数据
            return;
        }
        beansOfTypeMap.forEach((beanName, executor) -> {
            //通过executor采集数据就行了
            ThreadRuntimeInfo threadRuntimeInfo = new ThreadRuntimeInfo();
            //把数据封装到threadRuntimeInfo对象就行了

            String threadPoolName = executor.getThreadPoolName();
            // 核心线程数
            int corePoolSize = executor.getCorePoolSize();
            // 最大线程数
            int maximumPoolSize = executor.getMaximumPoolSize();
            // 线程池当前线程数 (有锁)
            int poolSize = executor.getPoolSize();
            // 活跃线程数 (有锁)
            int activeCount = executor.getActiveCount();
            // 同时进入池中的最大线程数 (有锁)
            int largestPoolSize = executor.getLargestPoolSize();
            // 线程池中执行任务总数量 (有锁)
            long completedTaskCount = executor.getCompletedTaskCount();
            // 当前负载
            double currentLoad = CalculateUtils.divide(activeCount, maximumPoolSize);
            // 峰值负载
            double peakLoad = CalculateUtils.divide(largestPoolSize, maximumPoolSize);
            // 队列元素个数
            int queueSize = executor.getQueue().size();
            // 队列类型
            String queueType = executor.getQueue().getClass().getName();
            // 队列剩余容量
            int remainingCapacity = executor.getQueue().remainingCapacity();
            // 队列容量
            int queueCapacity = queueSize + remainingCapacity;

            String threadFactoryType = executor.getThreadFactory().getClass().getName();
            String rejectedHandlerType = executor.getRejectedExecutionHandler().getClass().getName();

            threadRuntimeInfo.setCorePoolSize(corePoolSize);
            threadRuntimeInfo.setThreadPoolName(threadPoolName);
            threadRuntimeInfo.setPoolSize(poolSize);
            threadRuntimeInfo.setMaximumPoolSize(maximumPoolSize);
            threadRuntimeInfo.setActiveSize(activeCount);
            threadRuntimeInfo.setTaskCount(executor.getTaskCount());
            threadRuntimeInfo.setCurrentLoad(currentLoad);
            threadRuntimeInfo.setMaximumLoad(peakLoad);
            threadRuntimeInfo.setQueueType(queueType);
            threadRuntimeInfo.setThreadFactoryType(threadFactoryType);
            threadRuntimeInfo.setRejectedHandlerType(rejectedHandlerType);
            threadRuntimeInfo.setKeepaliveTime(executor.getKeepAliveTime(TimeUnit.SECONDS));
            threadRuntimeInfo.setQueueSize(queueSize);
            threadRuntimeInfo.setQueueCapacity(queueCapacity);
            threadRuntimeInfo.setQueueRemainingCapacity(remainingCapacity);
            threadRuntimeInfo.setLargestPoolSize(largestPoolSize);
            threadRuntimeInfo.setCompletedTaskCount(completedTaskCount);
            threadRuntimeInfo.setCreateTime(new Date());
            threadRuntimeInfo.setItemId(itemName);

            //插入到数据库
            threadRuntimeInfoService.insert(threadRuntimeInfo);
        });
    }
}