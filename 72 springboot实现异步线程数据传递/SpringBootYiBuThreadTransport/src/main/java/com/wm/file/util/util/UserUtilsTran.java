package com.wm.file.util.util;


import com.alibaba.ttl.TransmittableThreadLocal;

public class UserUtilsTran {

    private static final TransmittableThreadLocal<String> threadLocal = new TransmittableThreadLocal<>();

    public static String get() {
        return threadLocal.get();
    }

    public static void set(String userId) {
        threadLocal.set(userId);
    }

    public static void clear() {
        threadLocal.remove();
    }

}
