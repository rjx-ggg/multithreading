package com.woniu;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author woniu
 * @date 2024/2/19
 * @description 于绑定外部配置（如 properties 或 YAML 文件）到 Java 对象的一种机制
 * 允许将配置文件中的键值对映射到一个类的属性上
 **/
@Data
@ConfigurationProperties("encryption.type")
public class GlobalProperties {

    /**
     * 加解密算法
     */
    private String algorithmType;

    /**
     * 加解密key值
     */
    private String key;

}