package com.woniu.demo.local;


import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DefaultLocalCache<V> implements LocalCache<V> {
    // 默认容量
    private static final int DEFAULT_CAPACITY = 1024;
    private static final int MAX_CAPACITY = 100000;

    private static final int DEFAULT_THREAD_SIZE = 1;

    private final int maxSize;

    //数据map
    private volatile ConcurrentHashMap<String, V> dataMap;
    //过期时间
    private final ConcurrentHashMap<String, Long> timeOutMap;

    //定时任务
    private final ScheduledExecutorService executorService;

    public DefaultLocalCache() {
        maxSize = MAX_CAPACITY;
        dataMap = new ConcurrentHashMap<>(DEFAULT_CAPACITY);
        timeOutMap = new ConcurrentHashMap<>(DEFAULT_CAPACITY);
        executorService = new ScheduledThreadPoolExecutor(DEFAULT_THREAD_SIZE);
    }

    public DefaultLocalCache(int size) {
        maxSize = size;
        dataMap = new ConcurrentHashMap<>(DEFAULT_CAPACITY);
        timeOutMap = new ConcurrentHashMap<>(DEFAULT_CAPACITY);
        executorService = new ScheduledThreadPoolExecutor(DEFAULT_THREAD_SIZE);
    }

    @Override
    public boolean put(String key, V value) {
        //检查容量
        if (checkCapacity()) {
            dataMap.put(key, value);
            return true;
        }
        return false;
    }

    private boolean checkCapacity() {
        return dataMap.size() < maxSize;
    }


    @Override
    public boolean put(String key, V value, int seconds) {
        if (checkCapacity()) {
            dataMap.put(key, value);
            if (seconds >= 0) {
                timeOutMap.put(key, getTimeOut(seconds));
                ClearTask task = new ClearTask(key);
                executorService.schedule(task, seconds, TimeUnit.SECONDS);
            }
        }
        return false;
    }

    private long getTimeOut(int seconds) {
        long time = new Date().getTime();
        return time + 1000 * seconds;
    }

    @Override
    public boolean containKey(String key) {
        return false;
    }

    @Override
    public V get(String key) {
        return dataMap.get(key);
    }

    @Override
    public void remove(String key) {
        dataMap.remove(key);
        timeOutMap.remove(key);
    }

    @Override
    public void shutdownNow() {
        executorService.shutdown();
    }


    class ClearTask implements Runnable {
        private String key;

        public ClearTask(String key) {
            this.key = key;
        }

        @Override
        public void run() {
            //判断缓存中是否有key
            if (timeOutMap.containsKey(key)) {
                //获取失效时间
                Long expire = timeOutMap.get(key);
                //如果失效时间大于0，并且比当前时间小，则删除缓存
                if (expire > 0) {
                    long now = System.currentTimeMillis();
                    if (now >= expire) {
                        remove(key);
                    }
                }
            }
        }
    }
}