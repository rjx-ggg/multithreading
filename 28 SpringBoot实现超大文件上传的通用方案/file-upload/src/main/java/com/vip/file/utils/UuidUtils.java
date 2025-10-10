package com.vip.file.utils;

import java.util.UUID;

/**
 * UUID工具
 */
public class UuidUtils {

    /**
     * 得到32位的uuid
     *
     * @return {@link String}
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
