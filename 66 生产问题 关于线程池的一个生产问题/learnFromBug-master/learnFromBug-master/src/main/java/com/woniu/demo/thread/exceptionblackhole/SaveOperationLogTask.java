package com.woniu.demo.thread.exceptionblackhole;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

@Slf4j
public class SaveOperationLogTask implements Runnable {
    private final Integer taskId;

    public SaveOperationLogTask(Integer taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        log.info("Begin to save operation");
        // 保存日志
        saveLog();
        log.info("Success to Run task {}", this.taskId);
    }

    private void saveLog() {
        int result = RandomUtils.nextInt() / this.taskId;
    }
}
