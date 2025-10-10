package com.woniu.handler.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.woniu.GlobalProperties;
import com.woniu.handler.SecurityHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.KeyPair;

/**
 * @author woniu
 * @date 2024 2 19
 * @describe 基于hutool下的非对称加密SM2
 **/
@Slf4j
@Component
public class EncryAdecryImpl implements SecurityHandler {

    @Autowired
    private GlobalProperties globalProperties;
    private static volatile SM2 sm2;

    @Override
    public String encrypt(String original) {
        log.info("【starter】具体加密的数据{}",original);
        return sm2.encryptBase64(original, KeyType.PublicKey);
    }

    @Override
    public String decrypt(String original) {
        String decryptData = StrUtil.utf8Str(sm2.decryptStr(original, KeyType.PrivateKey));
        log.info("【starter】具体解密的数据：{}",decryptData);
        return decryptData;
    }

    @PostConstruct
    @Override
    public void init() {
        log.info("======>获取映射的加密算法类型：{}",globalProperties.getAlgorithmType());
        //传的是加密算法
        KeyPair pair = SecureUtil.generateKeyPair(globalProperties.getAlgorithmType());
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();
        sm2= SmUtil.sm2(privateKey, publicKey);
    }
}