package com.springboot.sample.thread;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 手写线程池
 */
@Slf4j
public class Main {
    public static void main(String[] args) {

        new ThreadPool(new BlockQueue<>(5), 2, 5, TimeUnit.SECONDS,
                (queue, task) -> {
                    // 一直等
//                    queue.put(task);
                    // 调用者线程执行
//                    task.run();
                    // 直接抛出异常
//                    throw new RuntimeException("saa");
                    // 丢弃这个任务
                    log.debug("丢弃这个任务{}",task);
                });

    }
}
