package com.zbl.springboot.service;

import com.alibaba.fastjson.JSON;
import com.zbl.springboot.rocketmq.RocketMqProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author zbl
 * @version 1.0
 * @since 2023/3/5 11:22
 */
@Slf4j
@Service
public class RocketMqTestServiceImpl {

    private final static String testTopic = "zheng_chang_topic";
    private final static String testTag = "test_tag";

    private final static String delayTestTopic = "delay_test_topic";
    private final static String delayTestTag = "delay_test_tag";

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private RocketMqProducer rocketMqProducer;

    public void testSendDeMq(Map<String, Object> messageBody, boolean needDelay) {
        messageBody.put("producerTime", System.currentTimeMillis());
        messageBody.put("age", 15);
        messageBody.put("name", "ZhangSan");

        String messageBodyJson = JSON.toJSONString(messageBody);

        log.info("RocketMqTestServiceImpl testSendMq messageBodyJson:{}", messageBodyJson);
        rocketMQTemplate.convertAndSend(testTopic + ":" + testTag, messageBodyJson);
        //Map<String, Object> map = new HashMap<>();
        //map.put("KEYS", "3465456416516");
        //MessageHeaders header = new MessageHeaders(map);
        //Message<String> message = MessageBuilder.createMessage(messageBodyJson, header);
        //rocketMQTemplate.convertAndSend(testTopic + ":" + testTag, message);
        log.info("RocketMqTestServiceImpl testSendMq done...");
    }

    public void testSendDelayMq(Map<String, Object> messageBody) {
        messageBody.put("producerTime", System.currentTimeMillis());
        messageBody.put("age", 25);
        messageBody.put("name", "ZhangSan");

        String messageBodyJson = JSON.toJSONString(messageBody);

        log.info("RocketMqTestServiceImpl testSendDelayMq messageBodyJson:{}", messageBodyJson);
        rocketMQTemplate.sendAndReceive(delayTestTopic + ":" + delayTestTag,
                messageBodyJson,
                String.class,
                30000L,
                3);
        log.info("RocketMqTestServiceImpl testSendDelayMq done...");
    }


    public void testSendDeMqWithSpringWay(Map<String, Object> messageBody) {
        messageBody.put("producerTime", System.currentTimeMillis());
        messageBody.put("age", 15);
        messageBody.put("name", "ZhangSan");
        rocketMqProducer.send(messageBody);
    }

    public void testSendDeMqWithSpringWay(Map<String, Object> messageBody, int delayLevel) {
        messageBody.put("producerTime", System.currentTimeMillis());
        messageBody.put("age", 15);
        messageBody.put("name", "ZhangSan");
        rocketMqProducer.send(messageBody, delayLevel);
    }

}
