package com.ydlclass.health;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class HealthApplicationTests {


    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void test1() {
        BoundSetOperations<String, String> aa = redisTemplate.boundSetOps("aa");
        aa.add("bb");
        aa.expire(30, TimeUnit.SECONDS);


    }

}
