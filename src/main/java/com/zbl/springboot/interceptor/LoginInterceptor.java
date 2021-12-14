package com.zbl.springboot.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.zbl.springboot.common.ResponseCode;
import com.zbl.springboot.dto.LoginUserDTO;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        //用于记录用户的登录凭证
        String userValue = "";

        //获取请求路径
        String requestURI = request.getRequestURI();
        //如果是需要登录才能访问的路径
        if (!noCheckPathList.contains(requestURI)) {
            //从cookie中判断是否含有登录凭证
            Cookie[] cookies = request.getCookies();
            if (null != cookies) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(loginCookieName)) {
                        userValue = cookie.getValue();
                    }
                }
            }

            //如果cookie中没有，则从请求参数中获取token
            if (StrUtil.isEmpty(userValue)) {
                userValue = request.getParameter("token");
            }

            //
            if (StrUtil.isEmpty(userValue)) {
                returnErrorResponse(response, ResponseCode.session_timeout.toJsonStr());
                return false;
            }

            String decode = URLDecoder.decode(userValue, "utf-8");
            LoginUserDTO loginUserDTO = JSON.parseObject(decode, LoginUserDTO.class);
            if (null == loginUserDTO
                    || loginUserDTO.getId() < 0
                    || loginUserDTO.getExpire() < System.currentTimeMillis()) {
                returnErrorResponse(response, ResponseCode.session_timeout.toJsonStr());
                return false;
            }
        }

        return true;
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
