package com.springboot.sample.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class BlockQueue<T> {

    private Deque<T> deque = new ArrayDeque<>();

    // 队列的容量
    private int size;

    private ReentrantLock lock = new ReentrantLock();

    // 空的休息室
    Condition emptyCondition = lock.newCondition();

    // 满的休息室
    Condition fullCondition = lock.newCondition();

    public BlockQueue(int size) {
        this.size = size;
    }


    // 添加任务 阻塞添加
    public void put(T task) {
        lock.lock();
        try {
            while (size == deque.size()) {
                try {
                    fullCondition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            log.debug("task 添加成功 ，{}", task);
            deque.addLast(task);
            emptyCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    // 带超时时间阻塞添加
    public boolean offer(T task, long timeout, TimeUnit timeUnit) {
        lock.lock();
        try {
            long nanos = timeUnit.toNanos(timeout);
            while (deque.size() == size) {
                try {
                    if (nanos <= 0) {
                        return false;
                    }
                    log.debug("等待加入任务队列 {} ...", task);
                    nanos = fullCondition.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入任务队列 {}", task);
            deque.addLast(task);
            emptyCondition.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    // 获取任务
    public T take() {
        lock.lock();
        try {
            while (deque.isEmpty()) {
                try {
                    emptyCondition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            T t = deque.removeFirst();
            fullCondition.signal();
            log.debug("获取了任务 {}", t);
            return t;
        } finally {
            lock.unlock();
        }
    }

    // 带超时阻塞获取
    public T poll(long timeout, TimeUnit unit) {
        lock.lock();
        try {
            // 将 timeout 统一转换为 纳秒
            long nanos = unit.toNanos(timeout);
            while (deque.isEmpty()) {
                try {
                    // 返回值是剩余时间
                    if (nanos <= 0) {
                        return null;
                    }
                    nanos = emptyCondition.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = deque.removeFirst();
            fullCondition.signal();
            return t;
        } finally {
            lock.unlock();
        }


    }


    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();
        try {
            if (deque.size() == size) {
                // 队列满了 执行这个策略的方法
                rejectPolicy.reject(this,task);
            } else {
                log.debug("加入任务队列 {}", task);
                deque.addLast(task);
                emptyCondition.signal();
            }
        } finally {
            lock.unlock();
        }

    }
}
