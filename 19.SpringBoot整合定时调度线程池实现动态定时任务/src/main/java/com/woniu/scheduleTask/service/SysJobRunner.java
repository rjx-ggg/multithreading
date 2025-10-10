package com.woniu.scheduleTask.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.woniu.scheduleTask.domain.ScheduleSetting;
import com.woniu.scheduleTask.mapper.ScheduleTaskMapper;
import com.woniu.scheduleTask.task.CronTaskRegistrar;
import com.woniu.scheduleTask.task.SchedulingRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysJobRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SysJobRunner.class);

    @Autowired
    ScheduleTaskMapper scheduleTaskMapper;

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    @Override
    public void run(String... args) {
        // 初始加载数据库里状态为正常的定时任务
        List<ScheduleSetting> jobList = scheduleTaskMapper.selectList(new QueryWrapper<ScheduleSetting>().eq("job_status", 1));
        if (CollectionUtils.isNotEmpty(jobList)) {
            for (ScheduleSetting job : jobList) {
                SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
                cronTaskRegistrar.addCronTask(task, job.getCronExpression());
            }
            logger.info("定时任务已加载完毕...");
        }
    }
}