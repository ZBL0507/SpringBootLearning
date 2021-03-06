package com.zbl.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.nimbusds.jose.JOSEException;
import com.zbl.springboot.common.ApiResult;
import com.zbl.springboot.dto.LoginParaDTO;
import com.zbl.springboot.dto.LoginUserDTO;
import com.zbl.springboot.po.User;
import com.zbl.springboot.service.UserService;
import com.zbl.springboot.util.LoginCodeUtil;
import com.zbl.springboot.util.MyRandomUtil;
import com.zbl.springboot.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/11/24 18:01
 */
@Api(description = "用户相关操作")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    public static final String userEncryptKey = "welkhjoihkjsdhf";

    @SuppressWarnings("all")
    @Autowired
    private UserService userService;

    @SuppressWarnings("all")
    @Autowired
    private LoginCodeUtil loginCodeUtil;

    @ApiOperation("获取用户详情")
    @GetMapping("/{userId}")
    public ApiResult<User> getUser(@PathVariable("userId") Integer userId) {
        log.info("获取用户详情userId:{}", userId);
        User user = userService.get(userId);
        return ApiResult.success(user);
    }

    @ApiOperation("更新用户")
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


    /**
     * 使用用户id和登录验证码进行登录
     *
     * @param loginParaDTO 登录请求参数
     * @return 成功登录则返回true，否则返回false
     */
    @ApiOperation("用户登录接口")
    @PostMapping("/login")
    public ApiResult<Boolean> login(@RequestBody LoginParaDTO loginParaDTO, HttpServletResponse response) throws UnsupportedEncodingException, JOSEException {
        //先检查该用户是否存在
        boolean exist = userService.exist(loginParaDTO.getUserId());
        if (!exist) {
            log.info("该用户不存在:{}", JSON.toJSONString(loginParaDTO));
            return ApiResult.fail("不存在该用户！");
        }
        boolean isSuccess = loginCodeUtil.checkLoginCode(loginParaDTO.getUserId(), loginParaDTO.getLoginCode());
        if (isSuccess) {
            LoginUserDTO loginUserDTO = new LoginUserDTO();
            loginUserDTO.setId(loginParaDTO.getUserId());
            loginUserDTO.setExpire(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
            String jsonString = JSON.toJSONString(loginUserDTO);
            String encode = URLEncoder.encode(jsonString, "utf-8");
            Cookie cookie = new Cookie("login_cookie_name", encode);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(cookie);
            response.addHeader("token", TokenUtil.generateToken(loginUserDTO, userEncryptKey));
            return ApiResult.success(true);
        }
        return ApiResult.fail(false);
    }

    /**
     * 获取登录验证码
     *
     * @return 登录时用的验证码
     */
    @ApiOperation("获取用户登录验证码接口")
    @GetMapping("/login/code")
    public ApiResult<String> getLoginCode(Long userId) {
        if (userId == null) {
            log.info("获取登录验证码时userid为空");
            return ApiResult.fail("userId not permit null");
        }

        String loginCode = loginCodeUtil.getLoginCode(userId);
        log.info("获取登录验证码userid:{}, loginCode:{}", userId, loginCode);
        return ApiResult.success(loginCode);
    }


    /**
     * 退出登录
     *
     * @return 退出状态
     */
    @ApiOperation("用户退出登录接口")
    @PostMapping("/logout")
    public ApiResult<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        return ApiResult.success("退出登录成功");
    }


    @GetMapping("/batchInsert")
    public String batchInsert() {
        new Thread(this::batchAdd).start();
        new Thread(this::batchAdd).start();
        new Thread(this::batchAdd).start();
        new Thread(this::batchAdd).start();
        return "线程启动，后台插入";
    }

    public void batchAdd() {
        log.info(Thread.currentThread().getName() + " 线程启动。。。");
        Collection<User> userList = new ArrayList<>();
        for (int i = 0; i < 80_0000; i++) {
            User user = new User();
            user.setName(MyRandomUtil.randomName());
            user.setAge(MyRandomUtil.randomAge());
            user.setSex(MyRandomUtil.randomSex());
            userList.add(user);
        }

        boolean batch = userService.createBatch(userList);
        log.info(Thread.currentThread().getName() + " 批量插入结果：{}", batch);
    }

}
