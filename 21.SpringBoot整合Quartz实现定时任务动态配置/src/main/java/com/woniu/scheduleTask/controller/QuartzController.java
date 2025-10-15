package com.woniu.scheduleTask.controller;

import com.woniu.scheduleTask.param.QuartzCreateParam;
import com.woniu.scheduleTask.param.QuartzDetailParam;
import com.woniu.scheduleTask.param.QuartzUpdateParam;
import com.woniu.scheduleTask.service.QuartzServiceImpl;
import com.woniu.scheduleTask.vo.QuartzJobDetailDto;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuartzController {
    @Autowired
    private QuartzServiceImpl quartzService;

    /**
     * 添加任务
     * @param param
     * @throws SchedulerException
     */
    @PostMapping("/quartz/addJob")
    public void addJob(@RequestBody QuartzCreateParam param) throws SchedulerException {
        quartzService.addJob(param);
    }


    /**
     * 更新任务
     * @param param
     * @throws SchedulerException
     */
    @PostMapping("/quartz/updateJob")
    public void updateJob(@RequestBody QuartzUpdateParam param) throws SchedulerException {
        quartzService.updateJob(param);
    }

    @PostMapping("/quartz/pauseJob")
    public void pauseJob(@RequestBody QuartzDetailParam param) throws SchedulerException {
        quartzService.pauseJob(param);
    }

    /**
     * 重启定时任务
     * @param param
     * @throws SchedulerException
     */
    @PostMapping("/quartz/resumeJob")
    public void resumeJob(@RequestBody QuartzDetailParam param) throws SchedulerException {
        quartzService.resumeJob(param);
    }

    /**
     * 删除任务
     * @param param
     * @throws SchedulerException
     */
    @PostMapping("/quartz/deleteJob")
    public void deleteJob(@RequestBody QuartzDetailParam param) throws SchedulerException {
        quartzService.deleteJob(param);
    }

    /**
     * 查询任务列表
     * @return
     * @throws SchedulerException
     */
    @PostMapping("/quartz/jobList")
    public List<QuartzJobDetailDto> jobList() throws SchedulerException {
        return quartzService.jobList();
    }

    /**
     * 查询任务详情
     * @param param
     * @return
     * @throws SchedulerException
     */
    @PostMapping("/quartz/jobDetail")
    public QuartzJobDetailDto jobDetail(@RequestBody QuartzDetailParam param) throws SchedulerException {
        return quartzService.jobDetail(param);
    }
}

