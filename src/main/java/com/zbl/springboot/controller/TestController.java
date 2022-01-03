package com.zbl.springboot.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/11/23 15:19
 */
@Api(description = "controller测试")
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String sayHello(String name) {
        return "hello " + name;
    }
}
