package com.woniu.scheduleTask.service.impl;

import com.woniu.scheduleTask.domain.ScheduleTask;
import com.woniu.scheduleTask.domain.UserInfo;
import com.woniu.scheduleTask.mapper.ScheduleTaskMapper;
import com.woniu.scheduleTask.service.ScheduledTaskManageService;
import com.woniu.scheduleTask.utils.ResultUtil;
import com.woniu.scheduleTask.utils.SchedulingTaskManage;
import com.woniu.scheduleTask.utils.SchedulingTaskRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: woniu
 * @description:
 * @date: 2023/1/12 10:53
 **/
@Service
public class ScheduledTaskManageServiceImpl implements ScheduledTaskManageService {
    @Autowired
    private SchedulingTaskManage taskManage;
    @Resource
    private ScheduleTaskMapper scheduleTaskMapper;

    @Override
    @Transactional
    public ResultUtil<?> addTask(ScheduleTask task) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("张三");
        userInfo.setPassword("121212121212");
        if (task.getTaskMethod().equals("noParamMethod")){
            userInfo = null;
        }
        String cron = task.getCron();
        boolean validExpression = CronExpression.isValidExpression(cron);
        if (!validExpression){
            return ResultUtil.error("无效的cron格式，请重新填写");
        }
        // 入库
        scheduleTaskMapper.insert(task);
        // 创建任务线程
        SchedulingTaskRunnable<UserInfo> taskRunnable = new SchedulingTaskRunnable<>(userInfo, task.getTaskClazz(), task.getTaskMethod());
        // 创建定时任务
        taskManage.createSchedulingTask(String.valueOf(task.getId()), taskRunnable,task.getCron());
        return ResultUtil.success();
    }

    @Override
    @Transactional
    public ResultUtil<?> deleteTask(Integer id) {
        scheduleTaskMapper.deleteById(id);
        taskManage.stopSchedulingTask(String.valueOf(id));
        return ResultUtil.success();
    }

    @Override
    @Transactional
    public ResultUtil<?> editTask(ScheduleTask task) {
        scheduleTaskMapper.updateById(task);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("张三");
        userInfo.setPassword("33333333");
        if (task.getTaskMethod().equals("noParamMethod")){
            userInfo = null;
        }
        SchedulingTaskRunnable<UserInfo> taskRunnable = new SchedulingTaskRunnable<>(userInfo, task.getTaskClazz(), task.getTaskMethod());
        taskManage.createSchedulingTask(String.valueOf(task.getId()), taskRunnable,task.getCron());
        return ResultUtil.success();
    }
}