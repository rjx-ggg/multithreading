package com.woniu.demo.thread.exceptionblackhole.bug;

import com.woniu.demo.model.RestResult;
import com.woniu.demo.thread.exceptionblackhole.SaveOperationLogTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RequestMapping("thread/exceptionblackhole/bug")
@RestController
@Slf4j
public class ExceptionBlackHoleBugController {
    private ExecutorService executorService;

    @PostConstruct
    public void init(){
        executorService = new ThreadPoolExecutor(4, 4,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(20),
                new BasicThreadFactory.Builder()
                        .namingPattern("BlackHole_thread-%d")
                        .build(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    @GetMapping("createOrder")
    public RestResult<String> createOrder(Integer taskId){
        log.info("begin to create Order {}", taskId);
        // 创建订单
        doCreateOrder(taskId);
        log.info("end to create Order {}", taskId);

        // 异步保存操作日志
        log.info("Begin to Submit Task {}", taskId);
        this.executorService.execute(new SaveOperationLogTask(taskId));
        log.info("Success to Submit Task {}", taskId);
        return RestResult.success("提交成功");
    }

    private void doCreateOrder(Integer taskId) {

    }
}
