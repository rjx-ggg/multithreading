package com.woniu.connection.config;

import com.woniu.connection.vo.RedisAsyResultData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;


@Configuration
public class RedisConfig {

   @Bean
   public RedisTemplate<String, RedisAsyResultData> redisTemplate(RedisConnectionFactory redisConnectionFactory){
       RedisTemplate<String, RedisAsyResultData> template = new RedisTemplate<>();
       template.setConnectionFactory(redisConnectionFactory);
       template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
       template.setKeySerializer(new GenericJackson2JsonRedisSerializer());
       return template;
   }



}
