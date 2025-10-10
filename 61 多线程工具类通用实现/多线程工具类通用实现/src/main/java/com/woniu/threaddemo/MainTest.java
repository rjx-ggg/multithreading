package com.woniu.threaddemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class MainTest {
    public static void main(String[] args) {
        //CompletableFuture 多线程工具类通用实现
        CompletableFutureDemo demo = new CompletableFutureDemo();
        List<Integer> numList = demo.parallelFutureJoin(Arrays.asList(1, 2, 3), num -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("exception", e);
            }
            return num;
        }, (e, num) -> {
            log.error("我异常了，我刚才在处理数字：" + num + "，异常原因：" + e);
            return -1;
        });
        log.info("numList:{}", numList);
    }
}
