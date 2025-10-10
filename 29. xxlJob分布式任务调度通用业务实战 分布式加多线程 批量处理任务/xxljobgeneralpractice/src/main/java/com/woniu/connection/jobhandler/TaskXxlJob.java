package com.woniu.connection.jobhandler;

import com.woniu.connection.entity.Task;
import com.woniu.connection.mapper.TaskMapper;
import com.woniu.connection.service.TaskService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * XxlJob开发示例（Bean模式）
 */
@Component
public class TaskXxlJob {
    private static Logger logger = LoggerFactory.getLogger(TaskXxlJob.class);

    @Autowired
    private TaskService taskService;

    /**
     * xxlJob分布式任务调度通用业务实战
     * 分片广播任务
     */
    @XxlJob("shardingJobHandler")
    public void shardingJobHandler() throws Exception {

        // 分片参数
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();

        logger.info("分片参数：当前分片序号 = {}, 总分片数 = {}", shardIndex, shardTotal);

        //查询待处理任务,一次处理的任务数和cpu核心数一样
        int count = Runtime.getRuntime().availableProcessors();

        List<Task> tasks = taskService.getTaskList(shardIndex, shardTotal, count);

        if (CollectionUtils.isEmpty(tasks)) {
            logger.info("没有获取到任务！");
            return;
        }

        //要处理的任务数
        int size = tasks.size();

        //创建size个线程数量的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(size);

        //计数器
        CountDownLatch countDownLatch = new CountDownLatch(size);

        //遍历tasks，将任务放入线程池
        for (Task task : tasks) {
            threadPool.execute(() -> {
                // 开启任务 利用数据库乐观锁 分布式锁 确保一个任务只有一个线程执行
                boolean flag = taskService.startTask(task);
                if (!flag) {
                    // 没有获取到锁
                    logger.info("没有获取到锁！");
                    return;
                }
                try {
                    // 处理任务 模拟耗时操作
                    dealTask();
                    // 执行成功 记录成功的状态 把任务插入归档表
                    taskService.saveTaskFinishStatus(task.getId(), 2, null);
                } catch (Exception e) {
                    logger.error("任务执行出错了,{}", e.getMessage());
                    // 记录出错信息
                    taskService.saveTaskFinishStatus(task.getId(), 3, e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await(5, TimeUnit.MINUTES);

    }

    /**
     * 模拟处理任务
     */
    private void dealTask() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            logger.error("InterruptedException");
        }
    }


    public void init() {
        logger.info("init");
    }

    public void destroy() {
        logger.info("destroy");
    }


}
