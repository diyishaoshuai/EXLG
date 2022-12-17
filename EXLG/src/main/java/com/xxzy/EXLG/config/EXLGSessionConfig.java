package com.xxzy.EXLG.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author gjq0117
 * @email  gjq0117@163.com
 * @date 2022/4/27 下午 05:35
 * 配置Redis序列化为JSON
 */
@Configuration
public class EXLGSessionConfig {
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer(){
        return new GenericJackson2JsonRedisSerializer();
    }
}
