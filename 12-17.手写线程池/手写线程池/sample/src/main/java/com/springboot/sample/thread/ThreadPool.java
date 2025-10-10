package com.springboot.sample.thread;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

    BlockQueue<Runnable> blockQueue;

    final Set<Worker> set = new HashSet<>();


    // 核心线程数
    int corePoolSize;

    long timeOut;

    TimeUnit timeUnit;

    RejectPolicy<Runnable> rejectPolicy;


    public ThreadPool(BlockQueue<Runnable> blockQueue, int corePoolSize, long timeOut, TimeUnit timeUnit, RejectPolicy<Runnable> rejectPolicy) {
        this.blockQueue = blockQueue;
        this.corePoolSize = corePoolSize;
        this.timeOut = timeOut;
        this.timeUnit = timeUnit;
        this.rejectPolicy = rejectPolicy;
    }

    // 处理任务
    public void execute(Runnable task) {
        // 当任务数 小于核心线程数
        synchronized (set) {
            if (set.size() < corePoolSize) {
                // 创建新的线程
                Worker worker = new Worker(task);
                set.add(worker);
                worker.start();
            } else {
//                blockQueue.put(task);
                // 当任务队列满了
                // 比如可以阻塞等待
                // 让调用者抛异常
                // 让调用者来执行这个任务
                // offer 带超时时间的阻塞添加
                blockQueue.tryPut(rejectPolicy,task);
            }
        }

    }


    class Worker extends Thread {

        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            while (task != null || (task = blockQueue.poll(timeOut, timeUnit)) != null) {
                try {
                    task.run();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    task = null;
                }
            }
            synchronized (set) {
                set.remove(this);
            }

        }
    }


}
