package com.wm.file.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {


    @Override
    public String alipay(String orderSn) {
        try {
            log.info("开始查询");
            Thread.sleep(1000);
            int i = 10 / 0;
        } catch (Exception e) {
            log.error("有个错误：{}", e.getMessage());
        }
        log.info("查询结束");
        return "haha";
    }

//    @Async("MyExecutor")
    @Async
    @Override
    public void insert() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("插入操作失败");
        }
        log.info("正在执行插入操作");
    }


}
