package com.example.jwt.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomThreadPoolConfig {
    private int maxWorkers;

    public CustomThreadPoolConfig(int maxWorkers) {
        this.maxWorkers = maxWorkers;
    }

    public ExecutorService createThreadPool() {
        return Executors.newFixedThreadPool(maxWorkers);
    }

    public int getMaxWorkers() {
        return maxWorkers;
    }

    public void setMaxWorkers(int maxWorkers) {
        this.maxWorkers = maxWorkers;
    }
}