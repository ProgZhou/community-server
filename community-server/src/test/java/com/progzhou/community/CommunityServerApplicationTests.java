package com.progzhou.community;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class CommunityServerApplicationTests {

    @Resource(name = "defaultRedisTemplate")
    RedisTemplate<String, String> redisTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    public void test() {
        Long test = redisTemplate.opsForValue().increment("test");
        System.out.println(test);
        String value = redisTemplate.opsForValue().get("test");
        System.out.println(value);
    }

}
