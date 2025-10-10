package com.woniu.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@ConfigurationProperties(prefix = "redisson.serverconfig")
@Data
public class RedissonSpringDataConfig {

    private static final Logger log = LoggerFactory.getLogger(RedissonSpringDataConfig.class);

    private String address;
    private int database;
    private String password;

    @Bean
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws JsonProcessingException {
        log.debug("[RedissonSpringDataConfig][redisson]>>>> address: {}, database: {}, password: {}", address, database, password);
        Config config = new Config();
        SingleServerConfig sconfig = config.useSingleServer()
                .setAddress(address)
                .setDatabase(database);
        // 如果redis设置了密码，这里不设置密码就会报“org.redisson.client.RedisAuthRequiredException: NOAUTH Authentication required”错误。
        if (StringUtils.hasText(password)) {
            sconfig.setPassword(password);
        }
        return Redisson.create(config);
    }

}