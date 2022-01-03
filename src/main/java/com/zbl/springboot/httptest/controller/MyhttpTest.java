package com.zbl.springboot.httptest.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zbl
 * @version 1.0
 * @since 2022/1/2 12:27
 */
@Slf4j
@RestController
@RequestMapping("/http-test")
public class MyhttpTest {

    @GetMapping("/get")
    public Map testGet(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.info("请求的uri:{}", requestURI);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("name", "zbl");
        map.put("age", "23");
        return map;
    }

    @GetMapping("/get-with-param")
    public Map testGet(HttpServletRequest request, String name, String age) {
        log.info("request uri is:{}", request.getRequestURI());
        log.info("request param is name:{}, age:{}", name, age);
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("age", age);

        JSONObject jsonObject = new JSONObject();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            jsonObject.put(headerNames.nextElement(), request.getHeader(headerNames.nextElement()));
        }
        map.put("请求时的请求头", jsonObject.toJSONString());

        return map;
    }

    @PostMapping(value = "/post-timeout")
    public Map testPost(HttpServletRequest request, @RequestBody Map<String, String> body) {
        log.info("testPost body is : {}", body.toString());

        String requestURI = request.getRequestURI();
        log.info("testPost requestURI is : {}", requestURI);

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return body;
    }

}
