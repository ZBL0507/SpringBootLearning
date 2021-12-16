package com.zbl.springboot.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.nimbusds.jose.JOSEException;
import com.zbl.springboot.common.ResponseCode;
import com.zbl.springboot.controller.UserController;
import com.zbl.springboot.dto.LoginUserDTO;
import com.zbl.springboot.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * 登录拦截器
 *
 * @author zbl
 * @version 1.0
 * @since 2021/12/9 10:59
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private static final ArrayList<String> noCheckPathList = new ArrayList<>();

    private static final String loginCookieName = "login_cookie_name";

    static {
        noCheckPathList.add("/user/login");
        noCheckPathList.add("/user/login/code");
        noCheckPathList.add("/error");
        noCheckPathList.add("/index");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("LoginInterceptor_requestURI:{}", requestURI);
        if (noCheckPathList.contains(requestURI)) {
            return true;
        }

        LoginUserDTO loginUserDTO = deduceLoginUserFromToken(request, response);
        if (null == loginUserDTO) {
            loginUserDTO = deduceLoginUserFromCookies(request, response);
        }

        if (null == loginUserDTO
                || loginUserDTO.getId() < 0
                || loginUserDTO.getExpire() < System.currentTimeMillis()) {
            returnErrorResponse(response, ResponseCode.session_timeout.toJsonStr());
            return false;
        }

        return true;
    }

    private LoginUserDTO deduceLoginUserFromToken(HttpServletRequest request, HttpServletResponse response) throws JOSEException {
        //用于记录用户的登录凭证
        String token = request.getParameter("token");
        if (StrUtil.isEmpty(token)) {
            return null;
        }

        return TokenUtil.parseToken(token, UserController.userEncryptKey);
    }

    private LoginUserDTO deduceLoginUserFromCookies(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //用于记录用户的登录凭证
        String userValue = "";

        //从cookie中判断是否含有登录凭证
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(loginCookieName)) {
                    userValue = cookie.getValue();
                }
            }
        }

        if (StrUtil.isEmpty(userValue)) {
            return null;
        }

        String decode = URLDecoder.decode(userValue, "utf-8");
        return JSON.parseObject(decode, LoginUserDTO.class);
    }

    private void returnErrorResponse(HttpServletResponse response, String result) throws IOException {
        ServletOutputStream outputStream = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            outputStream = response.getOutputStream();
            outputStream.write(result.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                outputStream.close();
            }
        }
    }
}
