package com.woniu.suanfa;

import java.security.MessageDigest;
import java.util.Formatter;

/**
 *
 * 大家平时的工作中，可能也在很多地方用到了加密、解密，比如：
 *
 * 用户的密码密文存储
 * 银行卡号之类敏感数据，需要加密传输
 * 重要接口，比如支付，客户端要对请求生成一个签名，服务端要对签名进行验证
 *
 *
 * 不可逆加密 MD5 SHA-256
 * 可逆加密
 *   对称加密 DES AES
 *   非对称加密 RSA
 *
 *
 */
public class MD5SF {
    private static final String MD5_ALGORITHM = "MD5";

    public static String encrypt(String data) throws Exception {
        // 获取MD5算法实例
        MessageDigest messageDigest = MessageDigest.getInstance(MD5_ALGORITHM);
        // 计算散列值
        byte[] digest = messageDigest.digest(data.getBytes());
        Formatter formatter = new Formatter();
        // 补齐前导0，并格式化
        for (byte b : digest) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public static void main(String[] args) throws Exception {
        String data = "Hello World";
        String encryptedData = encrypt(data);
        System.out.println("加密后的数据：" + encryptedData);
    }


}
