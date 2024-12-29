package com.progzhou.community.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisTemplateUtil {

    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Value("${spring.redis.expireTime}")
    private Integer expireTime;

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }

    public void set(String key, String value, long expireTime) {
        redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }

    public void set(String key, String value, long expireTime, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, expireTime, unit);
    }

    public void incr(String key) {
        redisTemplate.opsForValue().increment(key);
    }

    public void incr(String key, long value) {
        redisTemplate.opsForValue().increment(key, value);
    }

    public void decr(String key) {
        redisTemplate.opsForValue().decrement(key);
    }

    public void decr(String key, long value) {
        redisTemplate.opsForValue().decrement(key, value);
    }

    public void hset(String hashKey, String field, String value) {
        redisTemplate.opsForHash().put(hashKey, field, value);
    }

    public String hget(String key, String field) {
        return (String) redisTemplate.opsForHash().get(key, field);
    }



}
