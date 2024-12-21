package com.progzhou.community.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类
 */
@Configuration
public class RedisConfig {

    //过期时间，单位s
    @Value("${spring.redis.expireTime:60}")
    private Integer expireTime;

    @Bean(name = "defaultRedisTemplate")
    public RedisTemplate<String, String> redisTemplate(LettuceConnectionFactory factory) {
        RedisStandaloneConfiguration standaloneConfiguration = factory.getStandaloneConfiguration();
        LettuceClientConfiguration lettuceClientConfiguration = factory.getClientConfiguration();
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(standaloneConfiguration, lettuceClientConfiguration);

        //默认使用db0
        lettuceConnectionFactory.setDatabase(0);
        lettuceConnectionFactory.afterPropertiesSet();

        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
