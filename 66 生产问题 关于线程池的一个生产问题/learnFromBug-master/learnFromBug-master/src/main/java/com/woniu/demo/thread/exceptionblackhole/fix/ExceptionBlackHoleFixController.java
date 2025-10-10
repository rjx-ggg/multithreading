package com.woniu.demo.thread.exceptionblackhole.fix;

import com.woniu.demo.model.RestResult;
import com.woniu.demo.thread.exceptionblackhole.SaveOperationLogTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

@RequestMapping("thread/exceptionblackhole/fix")
@RestController
@Slf4j
public class ExceptionBlackHoleFixController {
    private ExecutorService executorService;
    private ExecutorService executorServiceV2;


    @PostConstruct
    public void init(){
        executorService = new ThreadPoolExecutor(4, 4,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(20),
                new BasicThreadFactory.Builder()
                        .namingPattern("BlackHole_thread-%d")
                        .build(),
                new ThreadPoolExecutor.AbortPolicy());

        executorServiceV2 = new ThreadPoolExecutor(4, 4,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(20),
                new BasicThreadFactory.Builder()
                        .namingPattern("BlackHole_thread-%d")
                        .uncaughtExceptionHandler((t, e) -> log.error("Failed to run task", e))
                        .build(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    @GetMapping("createOrder1")
    public RestResult<String> createOrder1(Integer taskId){
        log.info("begin to create Order {}", taskId);
        // 创建订单
        doCreateOrder(taskId);
        log.info("end to create Order {}", taskId);

        // 异步保存操作日志
        log.info("Begin to Submit Task {}", taskId);
        this.executorService.execute(new SaveOperationLogTask1(taskId));
        log.info("Success to Submit Task {}", taskId);
        return RestResult.success("提交成功");
    }

    @GetMapping("createOrder2")
    public RestResult<String> createOrder2(Integer taskId){
        log.info("begin to create Order {}", taskId);
        // 创建订单
        doCreateOrder(taskId);
        log.info("end to create Order {}", taskId);

        // 异步保存操作日志
        log.info("Begin to Submit Task {}", taskId);
        Runnable task = new SaveOperationLogTask(taskId);
        this.executorService.execute(new LogBasedTaskWrapper(task));
        log.info("Success to Submit Task {}", taskId);
        return RestResult.success("提交成功");
    }


    @GetMapping("createOrder3")
    public RestResult<String> createOrder3(Integer taskId){
        log.info("begin to create Order {}", taskId);
        // 创建订单
        doCreateOrder(taskId);
        log.info("end to create Order {}", taskId);

        // 异步保存操作日志
        log.info("Begin to Submit Task {}", taskId);
        this.executorServiceV2.execute(new SaveOperationLogTask(taskId));
        log.info("Success to Submit Task {}", taskId);
        return RestResult.success("提交成功");
    }

    @GetMapping("createOrder4")
    public RestResult<String> createOrder4(Integer taskId){
        log.info("begin to create Order {}", taskId);
        // 创建订单
        doCreateOrder(taskId);
        log.info("end to create Order {}", taskId);

        // 异步保存操作日志
        log.info("Begin to Submit Task {}", taskId);
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(new SaveOperationLogTask(taskId), this.executorService);
        completableFuture.exceptionally(e -> {
                log.error("Failed to Run Task", e);
                return null;
            }
        );
        log.info("Success to Submit Task {}", taskId);
        return RestResult.success("提交成功");
    }

    private void doCreateOrder(Integer taskId) {

    }
}
