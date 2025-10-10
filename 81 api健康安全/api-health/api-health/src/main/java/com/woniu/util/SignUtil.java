package com.woniu.util;

import org.apache.commons.codec.digest.DigestUtils;

public class SignUtil {

    /**
     * 签名计算
     *
     * @param method
     * @param url
     * @param body
     * @param timestamp
     * @param nonce
     * @param appSecret
     * @return
     */
    public static String getSignature(String method, String url, String body, String timestamp, String nonce, String appSecret) {
        //第一层签名
        String requestStr1 = method + url + body + appSecret;
        String signResult1 = DigestUtils.md5Hex(requestStr1);
        //第二层签名
        String requestStr2 = appSecret + timestamp + nonce + signResult1;
        String signResult2 = DigestUtils.md5Hex(requestStr2);
        return signResult2;
    }
}