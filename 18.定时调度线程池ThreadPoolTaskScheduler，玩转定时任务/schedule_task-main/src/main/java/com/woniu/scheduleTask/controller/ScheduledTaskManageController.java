package com.woniu.scheduleTask.controller;

import com.woniu.scheduleTask.domain.ScheduleTask;
import com.woniu.scheduleTask.service.ScheduledTaskManageService;
import com.woniu.scheduleTask.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 17.定时调度线程池ThreadPoolTaskScheduler，玩转定时任务
 * 之前需求引入：
 * 将定时任务保存到数据库中，并在页面上实现定时任务的开关，以及更新定时任务时间后重新创建定时任务
 * @author: woniu
 * @description: 定时任务管理类
 **/
@RestController
@RequestMapping("/scheduled")
public class ScheduledTaskManageController {

    @Autowired
    private ScheduledTaskManageService manageService;

    @PostMapping("/addTask.do")
    public ResultUtil<?> addTask(@RequestBody @Validated ScheduleTask task){
        return manageService.addTask(task);
    }

    @PutMapping("/editTask.do")
    public ResultUtil<?> editTask(@RequestBody @Validated(value = ScheduleTask.Update.class) ScheduleTask task){
        return manageService.editTask(task);
    }

    @GetMapping("/deleteTask.do/{id}")
    public ResultUtil<?> deleteTask(@PathVariable("id") Integer id){
        return manageService.deleteTask(id);
    }
}