package com.woniu.scheduleTask.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.woniu.scheduleTask.domain.ScheduleSetting;
import com.woniu.scheduleTask.mapper.ScheduleTaskMapper;
import com.woniu.scheduleTask.task.CronTaskRegistrar;
import com.woniu.scheduleTask.task.SchedulingRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class TestController {

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    @Autowired
    private ScheduleTaskMapper scheduleTaskMapper;

    /**
     * 添加定时任务
     * @param sysJob
     * @return
     */
    @PostMapping("add")
    @Transactional
    public boolean add(@RequestBody ScheduleSetting sysJob) {
        sysJob.setCreateTime(new Date());
        sysJob.setUpdateTime(new Date());

        int insert = scheduleTaskMapper.insert(sysJob);
        if (insert == 0) {
            return false;
        } else {
            if (sysJob.getJobStatus().equals(1)) {// 添加成功,并且状态是1，直接放入任务器
                SchedulingRunnable task = new SchedulingRunnable(sysJob.getBeanName(), sysJob.getMethodName(), sysJob.getMethodParams());
                cronTaskRegistrar.addCronTask(task, sysJob.getCronExpression());
            }
            return true;
        }
    }

    /**
     * 修改定时任务
     *
     * @param sysJob
     * @return
     */
    @PostMapping("update")
    @Transactional

    public boolean update(@RequestBody ScheduleSetting sysJob) {
        sysJob.setCreateTime(new Date());
        sysJob.setUpdateTime(new Date());

        // 查询修改前任务
        ScheduleSetting existedSysJob = scheduleTaskMapper.selectOne(new QueryWrapper<ScheduleSetting>().eq("job_id", sysJob.getJobId()));
        if (existedSysJob == null) {
            return false;
        }
        // 修改任务
        int update = scheduleTaskMapper.update(sysJob, new UpdateWrapper<ScheduleSetting>().eq("job_id", sysJob.getJobId()));
        if (update == 0) {
            return false;
        } else {
            // 修改成功,则先删除任务器中的任务,并重新添加
            SchedulingRunnable task1 = new SchedulingRunnable(existedSysJob.getBeanName(), existedSysJob.getMethodName(), existedSysJob.getMethodParams());
            cronTaskRegistrar.removeCronTask(task1);
            if (sysJob.getJobStatus().equals(1)) {// 如果修改后的任务状态是1就加入任务器
                SchedulingRunnable task = new SchedulingRunnable(sysJob.getBeanName(), sysJob.getMethodName(), sysJob.getMethodParams());
                cronTaskRegistrar.addCronTask(task, sysJob.getCronExpression());
            }
            return true;
        }
    }

    /**
     * 删除任务
     *
     * @param jobId
     * @return
     */
    @PostMapping("del/{jobId}")
    @Transactional
    public boolean del(@PathVariable("jobId") Integer jobId) {
        // 先查询要删除的任务信息
        ScheduleSetting existedSysJob = scheduleTaskMapper.selectOne(new QueryWrapper<ScheduleSetting>().eq("job_id", jobId));
        // 删除
        int del = scheduleTaskMapper.delete(new QueryWrapper<ScheduleSetting>().eq("job_id", jobId));
        if (del == 0)
            return false;
        else {// 删除成功时要清除定时任务器中的对应任务
            SchedulingRunnable task = new SchedulingRunnable(existedSysJob.getBeanName(), existedSysJob.getMethodName(), existedSysJob.getMethodParams());
            cronTaskRegistrar.removeCronTask(task);
            return true;
        }

    }

    // 停止/启动任务
    @PostMapping("changesStatus/{jobId}/{stop}")
    public boolean changesStatus(@PathVariable("jobId") Integer jobId, @PathVariable("stop") Integer stop) {

        // 查询修改后的任务信息
        ScheduleSetting existedSysJob = scheduleTaskMapper.selectOne(new QueryWrapper<ScheduleSetting>().eq("job_id", jobId));
        if (existedSysJob == null) {
            return false;
        }

        // 修改任务状态
        ScheduleSetting scheduleSetting = new ScheduleSetting();
        scheduleSetting.setJobStatus(stop);
        scheduleSetting.setJobId(jobId);
        scheduleTaskMapper.updateById(scheduleSetting);

        // 如果状态是1则添加任务
        if (existedSysJob.getJobStatus().equals(1)) {
            SchedulingRunnable task = new SchedulingRunnable(existedSysJob.getBeanName(), existedSysJob.getMethodName(), existedSysJob.getMethodParams());
            cronTaskRegistrar.addCronTask(task, existedSysJob.getCronExpression());
        } else {
            // 否则清除任务
            SchedulingRunnable task = new SchedulingRunnable(existedSysJob.getBeanName(), existedSysJob.getMethodName(), existedSysJob.getMethodParams());
            cronTaskRegistrar.removeCronTask(task);
        }
        return true;
    }

}