package com.woniu.demo.thread.exceptionblackhole.fix;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogBasedTaskWrapper implements Runnable {
    private final Runnable runnable;

    public LogBasedTaskWrapper(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void run() {
        try {
            this.runnable.run();
        }catch (Exception e) {
            log.error("Filed to run task {}", runnable, e);
        }
    }
}
