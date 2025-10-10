package com.woniu.handler;

/**
 * @author 蜗牛
 * @describe:
 * @date 2024/2/19
 */
public interface SecurityHandler {
    /**
     * 全局统一加密
     * @param original  明文
     * @return 密文
     */
    String encrypt(String original);

    /**
     * 全局统一解密
     * @param original 密文
     * @return 明文
     */
    String decrypt(String original);

    /**
     * 加解密默认处理方式
     */
    void init();
}
