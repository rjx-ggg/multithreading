package com.woniu.connection.util;

import com.woniu.connection.vo.RedisAsyResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

@Component
public class AsyncUtil implements ApplicationContextAware {

    static Logger LOG = LoggerFactory.getLogger(AsyncUtil.class);
    public static ExecutorService executor = Executors.newFixedThreadPool(40);
    public static ScheduledExecutorService ex = Executors.newScheduledThreadPool(1);
    static List<String> keys = new ArrayList<>();
    static boolean scheduleIsStart = false;
    static RedisTemplate<String, RedisAsyResultData> redisTemplate;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        redisTemplate = applicationContext.getBean("redisTemplate", RedisTemplate.class);
    }


    static void updateKeyLiveTime() {
        if (!scheduleIsStart) {
            // 更新redis中缓存的过期时间
            ex.scheduleAtFixedRate(() -> {
                try {
                    LOG.info("----- update AsyncResult keys length:{} -----",
                            keys.size());
                    if (!CollectionUtils.isEmpty(keys)) {
                        List<RedisAsyResultData> multiGet =
                                redisTemplate.opsForValue().multiGet(keys);
                        for (RedisAsyResultData result : multiGet) {
                            if (result != null) {
                                String key = result.getRedisKey();
                                redisTemplate.expire(key, 5, TimeUnit.MINUTES);
                            }
                        }
                    }
                } catch (Exception e) {
                    scheduleIsStart = false;
                    LOG.error(e.getMessage(), e);
                }
            }, 1, 3, TimeUnit.MINUTES);
            scheduleIsStart = true;
        }
    }

    /**
     * 设置进度
     *
     * @param key
     * @param done
     * @return
     */
    public static void setDone(String key, Integer done) {
        RedisAsyResultData result = getResult(key);
        Optional.ofNullable(result).ifPresent(re -> {
            re.setDone(done);
            saveResult(key, result);
        });
    }

    /**
     * 设置总数
     *
     * @param key
     * @param total
     * @return
     */
    public static void setTotal(String key, Integer total) {
        RedisAsyResultData result = getResult(key);
        Optional.ofNullable(result).ifPresent(re -> {
            re.setTotal(total);
            saveResult(key, result);
        });
    }

    public static RedisAsyResultData submitTask(String key, Supplier supplier) {
        AtomicReference<RedisAsyResultData> rs = new AtomicReference<>(new RedisAsyResultData());
        rs.get().setSuccess(false);
        rs.get().setRedisKey(key);
        rs.get().setDone(0);
        rs.get().setTotal(100);
        setToRedis(rs.get(), key);
        if (!keys.contains(key)) {
            keys.add(key);
        }
        String finalKey = key;
        executor.submit(() -> {
            String msg = null;
            try {
                Object o = supplier.get();
                RedisAsyResultData result = getResult(key);
                if (null != result) {
                    rs.set(result);
                }
                rs.get().setData(o);
                rs.get().setFlag(true);
            } catch (Exception e) {
                rs.get().setFlag(false);
                msg = e.getMessage();
                LOG.error(e.getMessage(), e);
            }
            rs.get().setSuccess(true);

            rs.get().setDone(rs.get().getTotal());
            if (null != msg) {
                rs.get().setError(msg);
            }
            keys.remove(finalKey);
            setToRedis(rs.get(), finalKey);
        });
        updateKeyLiveTime();
        return rs.get();
    }

    private static void setToRedis(RedisAsyResultData result, String redisKey) {
        redisTemplate.opsForValue().set(redisKey, result, 5, TimeUnit.MINUTES);
    }

    public static RedisAsyResultData getResult(String key) {
        RedisAsyResultData excelResult =
                redisTemplate.opsForValue().get(key);
        if (null != excelResult) {
            return excelResult;
        }
        return null;
    }

    public static void saveResult(String key, RedisAsyResultData result) {
        setToRedis(result, key);
    }

}


