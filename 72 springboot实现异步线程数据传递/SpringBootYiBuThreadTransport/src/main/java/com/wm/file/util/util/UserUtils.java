package com.wm.file.util.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 公众号：程序员蜗牛g
 * @description 用户上下文信息
 */
@Slf4j
public class UserUtils {

    private static final ThreadLocal<String> userLocal = new ThreadLocal<>();

    public static String getUserId() {
        return userLocal.get();
    }

    public static void setUserId(String userId) {
        userLocal.set(userId);
    }

    public static void clear() {
        userLocal.remove();
    }




}