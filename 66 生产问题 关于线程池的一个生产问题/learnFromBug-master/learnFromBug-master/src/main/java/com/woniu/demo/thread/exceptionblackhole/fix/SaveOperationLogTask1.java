package com.woniu.demo.thread.exceptionblackhole.fix;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

@Slf4j
public class SaveOperationLogTask1 implements Runnable {
    private final Integer taskId;

    public SaveOperationLogTask1(Integer taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        try {
            int result = RandomUtils.nextInt() / this.taskId;
            log.info("Success to Run task {}", this.taskId);
        }catch (Exception e) {
            log.error("failed to run task {}", taskId, e);
        }
    }
}
