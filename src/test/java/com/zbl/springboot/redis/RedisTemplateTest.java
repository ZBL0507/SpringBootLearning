package com.zbl.springboot.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/11/18 10:16
 */
@SpringBootTest
class RedisTemplateTest {

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    void testRedisConnect() {
        /*redisTemplate.opsForValue().set("zzz", "zzz的缓存value");*/
        /*redisTemplate.opsForValue().set("bbb", "bbb的缓存value");*/
        /*redisTemplate.opsForValue().set("ccc", "ccc的缓存value");*/

        Object zzz = redisTemplate.opsForValue().get("zzz");
        System.out.println(zzz);
        Object bbb = redisTemplate.opsForValue().get("bbb");
        System.out.println(bbb);
        Object ccc = redisTemplate.opsForValue().get("ccc");
        System.out.println(ccc);
    }
}
