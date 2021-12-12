package com.zbl.springboot.util;

import cn.hutool.core.util.StrUtil;
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
 * @since 2021/12/12 15:37
 */
@Slf4j
@Component
public class LoginCodeUtil {

    private final static String user_login_code = "user_login_code_";

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
     * 根据用户id获取登录验证码（验证码5分钟有效）
     *
     * @param userId 用户id
     * @return 登录所使用的验证码
     */
    @SuppressWarnings("unchecked")
    public String getLoginCode(Long userId) {
        String redisKey = user_login_code + userId;

        String code = MyRandomUtil.randomLoginCode();
        valueOperations.set(redisKey, code, 5, TimeUnit.MINUTES);

        return code;
    }

    /**
     * 校验登录验证码
     * 校验规则：用用户输入的验证码和服务端保存的验证码进行比对，如果一致则校验通过，不一致则不通过
     *
     * @param userId    用户id
     * @param loginCode 登录验证码
     * @return 验证成功返回true， 失败返回false
     */
    public boolean checkLoginCode(Long userId, String loginCode) {
        log.info("校验验证码：userId:{}, loginCode:{}", userId, loginCode);
        String redisKey = user_login_code + userId;
        String redisCode = (String) valueOperations.get(redisKey);
        log.info("服务端保存的验证码:{}", loginCode);
        if (StrUtil.isEmpty(redisCode)) {
            return false;
        }

        return redisCode.equals(loginCode);
    }


}
