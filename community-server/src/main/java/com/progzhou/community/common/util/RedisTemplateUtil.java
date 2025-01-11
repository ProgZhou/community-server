package com.progzhou.community.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisTemplateUtil {

    @Resource(name = "defaultRedisTemplate")
    RedisTemplate<String, String> redisTemplate;

    @Value("${spring.redis.expireTime}")
    private Integer expireTime;

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setWithNoExpire(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
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

    public long incr(String key) {
       return redisTemplate.opsForValue().increment(key);
    }

    public long incr(String key, long value) {
        return redisTemplate.opsForValue().increment(key, value);
    }

    public long decr(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    public long decr(String key, long value) {
        return redisTemplate.opsForValue().decrement(key, value);
    }

    public void hset(String hashKey, String field, String value) {
        redisTemplate.opsForHash().put(hashKey, field, value);
    }

    public String hget(String key, String field) {
        return (String) redisTemplate.opsForHash().get(key, field);
    }



}
