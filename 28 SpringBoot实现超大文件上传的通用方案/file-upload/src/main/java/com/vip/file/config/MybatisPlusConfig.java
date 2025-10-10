package com.vip.file.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus配置自动填充
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 自动填充功能
     *
     * @return {@link GlobalConfig}
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new MyBatisPlusMetaHandler());
        return globalConfig;
    }
}
