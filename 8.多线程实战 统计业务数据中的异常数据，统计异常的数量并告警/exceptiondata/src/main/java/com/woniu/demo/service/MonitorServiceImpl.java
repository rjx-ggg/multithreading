package com.woniu.demo.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 多线程实战 第七课
 * 定时任务监控业务数据，
 * 统计异常的业务数据和数量并告警
 */
@Service
@Slf4j
public class MonitorServiceImpl implements MonitorService {

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Override
    public void monitor() {
        AtomicInteger errDataCount = new AtomicInteger(0);
        List<String> exceptionDatas = new ArrayList<>();

        // 查询业务数据 并且业务数据量比较大
        List<String> datas = getBizData();

        if (CollectionUtils.isEmpty(datas)) {
            return;
        }

        // 数据分组
        List<List<String>> groupDatas = Lists.partition(datas, 10000);
        CountDownLatch countDownLatch = new CountDownLatch(groupDatas.size());

        //  多线程处理每一组数据 并且统计业务数据的数量
        for (List<String> groupData : groupDatas) {
            executor.execute(() -> {
                // 统计异常业务数据
                try {
                    countExceptionData(errDataCount, groupData, exceptionDatas);
                } catch (Exception e) {
                    log.info("统计出错！");
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //  打印异常业务数据的数量
        log.info("异常业务数据数量：{}", errDataCount.get());

        // TODO
        // 拿到exceptionDatas 进行告警处理 比如发邮件
        log.info("异常业务数据：{}", exceptionDatas);


    }

    /**
     * 统计异常业务数据
     *
     * @param errDataCount
     * @param groupData
     * @param exceptionDatas
     */
    private void countExceptionData(AtomicInteger errDataCount, List<String> groupData, List<String> exceptionDatas) {
        // 比如数据大于 80000的是异常数据 具体根据业务来
        for (String groupDatum : groupData) {
            if (Integer.parseInt(groupDatum) > 80000) {
                errDataCount.getAndIncrement();
                exceptionDatas.add(groupDatum);
            }
        }
    }

    /**
     * 构造业务数据 正常走数据库查询
     *
     * @return
     */
    private List<String> getBizData() {
        List<String> datas = new ArrayList<>();
        // 模拟查询数据库
        for (int i = 0; i < 100000; i++) {
            datas.add(String.valueOf(i));
        }
        return datas;
    }
}
