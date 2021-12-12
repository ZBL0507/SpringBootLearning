package com.zbl.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.zbl.springboot.common.ApiResult;
import com.zbl.springboot.po.User;
import com.zbl.springboot.service.UserService;
import com.zbl.springboot.util.LoginCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/11/24 18:01
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @SuppressWarnings("all")
    @Autowired
    private UserService userService;

    @SuppressWarnings("all")
    @Autowired
    private LoginCodeUtil loginCodeUtil;

    @GetMapping("/{userId}")
    public ApiResult<User> getUser(@PathVariable("userId") Integer userId) {
        log.info("获取用户详情userId:{}", userId);
        User user = userService.get(userId);
        return ApiResult.success(user);
    }

    @PostMapping("/update")
    public ApiResult<Boolean> update(@RequestBody User user) {
        try {
            log.info("更新用户:{}", JSON.toJSONString(user));
            if (user == null || user.getId() == null) {
                log.info("please key in correct userId");
                throw new IllegalArgumentException("please key in correct userId");
            }
            boolean res = userService.update(user);
            if (res) {
                return ApiResult.success(true);
            } else {
                return ApiResult.fail(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.fail("更新用户信息失败");
        }
    }


    @GetMapping("/login")
    public ApiResult<Boolean> getUser() {
        log.info("lkjsdf");
        return ApiResult.success(true);
    }

    /**
     * 获取登录验证码
     *
     * @return 登录时用的验证码
     */
    @GetMapping("/login/code")
    public ApiResult<String> getLoginCode(Long userId) {
        if (userId == null) {
            return ApiResult.fail("userId not permit null");
        }

        String loginCode = loginCodeUtil.getLoginCode(userId);
        return ApiResult.success(loginCode);
    }

}
