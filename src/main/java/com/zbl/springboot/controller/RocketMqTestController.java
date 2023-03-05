package com.zbl.springboot.controller;

import com.zbl.springboot.service.RocketMqTestServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author zbl
 * @version 1.0
 * @since 2023/3/5 11:17
 */
@Slf4j
@RestController
public class RocketMqTestController {

    @Resource
    private RocketMqTestServiceImpl rocketMqTestService;

    @GetMapping("/testSendMq")
    public Object testSendMq() {
        rocketMqTestService.testSendDeMq(new HashMap<>(), true);
        return "send done...";
    }

    @GetMapping("/testSendDelayMq")
    public Object testSendDelayMq() {
        rocketMqTestService.testSendDelayMq(new HashMap<>());
        return "testSendDelayMq done...";
    }
}
