package com.springboot.sample.controller;

import com.springboot.sample.bean.User;
import com.springboot.sample.service.impl.UserWrapBatchQueueService;
import com.springboot.sample.service.impl.UserWrapBatchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/*
 * 蜗牛哥
 * */
@RestController
@RequestMapping("/async")
public class AsyncController {

    @Resource
    private UserWrapBatchService userBatchService;

    @Resource
    private UserWrapBatchQueueService userWrapBatchQueueService;


    /**
     * 接口多线程并发执行 可能会出现的问题？
     * <p>
     * 异步，开启多线程，会阻塞Tomcat的线程
     *
     * @param userId
     * @return
     */
    @RequestMapping("/async1")
    public User async1(Long userId) {
        return userBatchService.queryUserAsync(userId);
    }


    /***
     * 接口请求批量调用结合多线程 优化系统性能
     * */
    @RequestMapping("/merge")
    public User merge(Long userId) {
        // 使用 future.get() 阻塞获取
//        return userBatchService.queryUser(userId);
        // 优化使用队列，添加超时时间处理，避免阻塞，超高并发调用接口导致资源耗尽
        return userWrapBatchQueueService.queryUser(userId);
    }

}
