package com.woniu.demo.local;

/**
 * @className: LocalCache
 * @author: woniu
 * @date: 2024/4/9
 **/
public interface LocalCache<V> {
    /**
     * 插入数据，数据永久有效
     */
    boolean put(String key, V value);

    /**
     * 插入数据，在指定时间内生效
     */
    boolean put(String key, V value, int seconds);

    /**
     * 是否包含指定的key
     */
    boolean containKey(String key);

    /**
     * 获取指定Key的值
     */
    V get(String key);

    /**
     * 从缓存中移除key对应的数据
     */
    void remove(String key);

    void shutdownNow();
}