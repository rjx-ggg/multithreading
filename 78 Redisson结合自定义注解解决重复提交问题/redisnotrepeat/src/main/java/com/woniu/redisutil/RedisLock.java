package com.woniu.redisutil;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Component
public class RedisLock {
    private static final Logger log = LoggerFactory.getLogger(RedisLock.class);

    // todo  待优化，最好使用自定义的线程池，自定义工作队列和最大线程数。
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(4);
    @Resource
    private Redisson redisson;

    /**
     * Redission获取锁
     *
     * @param lockKey      锁名
     * @param delaySeconds 过期时间
     * @param unit         单位
     * @return 是否获取成功
     */
    public boolean Rlock(String lockKey, long delaySeconds, final TimeUnit unit) {
        RLock rLock = redisson.getLock(lockKey);
        boolean success = false;
        try {
            success = rLock.tryLock(0, delaySeconds, unit);
        } catch (InterruptedException e) {
            log.error("[RedisLock][Rlock]>>>> 加锁异常: ", e);
        }
        return success;
    }

    /**
     * Redission释放锁
     *
     * @param lockKey 锁名
     */
    public void Runlock(String lockKey) {
        RLock rLock = redisson.getLock(lockKey);
        log.debug("[RedisLock][Rlock]>>>> {}, status: {} === unlock thread id is: {}", rLock.isHeldByCurrentThread(), rLock.isLocked(), Thread.currentThread().getId());
        rLock.unlock();
    }

    /**
     * Redission延迟释放锁
     *
     * @param lockKey   锁名
     * @param delayTime 延迟时间
     * @param unit      单位
     */
    public void delayUnlock(final String lockKey, long delayTime, TimeUnit unit) {
        if (!StringUtils.hasText(lockKey)) {
            return;
        }
        if (delayTime <= 0) {
            Runlock(lockKey);
        } else {
            EXECUTOR_SERVICE.schedule(() -> Runlock(lockKey), delayTime, unit);
        }
    }

}