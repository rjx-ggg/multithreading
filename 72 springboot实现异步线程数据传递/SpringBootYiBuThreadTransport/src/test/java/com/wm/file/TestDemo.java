package com.wm.file;


import com.wm.file.util.util.UserUtils;
import com.wm.file.util.util.UserUtilsTran;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExportExcelDataApplication.class)//springboot注解
@Slf4j
public class TestDemo {

    /**
     * 如何优雅的实现Spring Boot 异步线程间数据传递
     */
    @Test
    public void handlerAsync() {
        //1. 获取父线程的userId
        UserUtils.setUserId("232");
        String userId = UserUtils.getUserId();
        log.info("父线程的值：{}", userId);
        CompletableFuture.runAsync(() -> {
            //2. 设置子线程的值，复用
            UserUtils.setUserId(userId);
            log.info("子线程的值：{}", UserUtils.getUserId());
        });
    }


    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 线程池设置TaskDecorator
     */
    @Test
    public void handlerAsync2() {
        UserUtils.setUserId("232");
        log.info("父线程的用户信息：{}", UserUtils.getUserId());
        //执行异步任务，需要指定的线程池
        CompletableFuture.runAsync(() ->
                        log.info("子线程的用户信息：{}", UserUtils.getUserId()),
                threadPoolTaskExecutor);
    }

    /**
     * TransmittableThreadLocal
     */

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Test
    public void handlerAsync3() {
        UserUtilsTran.set("213");
        log.info("父线程的用户信息：{}", UserUtilsTran.get());

        CompletableFuture.runAsync(()->
                        log.info("子线程的用户信息：{}", UserUtilsTran.get()),
                threadPoolExecutor);
    }



}





