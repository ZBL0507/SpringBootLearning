package com.zbl.springboot.util;

import com.zbl.springboot.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/12/8 11:53
 */
@Slf4j
@Component
public class UserCacheUtil {
    private static final String user_cache_prefix = "user_cache_prefix_";

    @SuppressWarnings("all")
    @Autowired
    private RedisTemplate redisTemplate;

    @SuppressWarnings("all")
    private ValueOperations valueOperations;

    @PostConstruct
    public void init() {
        valueOperations = redisTemplate.opsForValue();
    }

    /**
     * 根据用户id从分布式缓存redis中获取该用户数据
     *
     * @param userId 用户id
     * @return 这个id对应的用户数据，如果缓存中没有则返回null
     */
    public User getUser(Long userId) {
        try {
            return (User) valueOperations.get(user_cache_prefix + userId);
        } catch (Exception e) {
            log.error("getUser from cache error... ", e);
            return null;
        }
    }

    /**
     * 保存用户数据到分布式缓存redis中
     *
     * @param user     用户数据
     * @param time     过期时间
     * @param timeUnit 时间单位
     */
    @SuppressWarnings("unchecked")
    public void saveUser(User user, Long time, TimeUnit timeUnit) {
        if (null == user) {
            throw new IllegalArgumentException("user must not be null!");
        }
        if (null == user.getId()) {
            throw new IllegalArgumentException("user id must not be null!");
        }

        valueOperations.set(user_cache_prefix + user.getId(), user, time, timeUnit);
    }

    public void saveUser(User user) {
        try {
            saveUser(user, 1L, TimeUnit.HOURS);
        } catch (Exception e) {
            log.error("save user to cache error... ", e);
        }
    }

    /**
     * 根据用户id删除这个用户的缓存数据
     *
     * @param userId 用户id
     */
    @SuppressWarnings("unchecked")
    public void delUser(Long userId) {
        redisTemplate.delete(user_cache_prefix + userId);
    }

}
