package com.zbl.springboot.rocketmq;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author zbl
 * @version 1.0
 * @since 2023/3/5 17:24
 */
@Slf4j
@Component
public class RocketMqProducer {

    private final static String testTopic = "zheng_chang_topic";
    private final static String testTag = "test_tag";

    private final static String delayTestTopic = "delay_test_topic";
    private final static String delayTestTag = "delay_test_tag";

    private DefaultMQProducer mqProducer;

    private final Snowflake snowflake = new Snowflake();

    @Resource
    private Environment environment;

    @PostConstruct
    public void initMqProducer() {
        mqProducer = new DefaultMQProducer();
        String nameServer = environment.getProperty("rocketmq.name-server");
        mqProducer.setNamesrvAddr(nameServer);

        String groupName = environment.getProperty("rocketmq.producer.group");
        mqProducer.setProducerGroup(groupName);

        try {
            mqProducer.start();
        } catch (MQClientException e) {
            log.error("mqProducer start fail...");
            e.printStackTrace();
        }
    }

    public void send(Object mqBody) {
        this.send(testTopic, testTag, snowflake.nextIdStr(), mqBody, 0);
    }

    public void send(Object mqBody, int delayLevel) {
        this.send(testTopic, testTag, snowflake.nextIdStr(), mqBody, delayLevel);
    }

    /**
     * 发送MQ
     *
     * @param topic      消息topic
     * @param tag        消息tag
     * @param key        消息key
     * @param mqBody     消息体
     * @param delayLevel 消息延迟级别
     */
    public void send(String topic, String tag, String key, Object mqBody, int delayLevel) {
        Message message = new Message();
        message.setTopic(topic);
        message.setTags(tag);
        message.setKeys(key);
        message.setBody(JSON.toJSONString(mqBody).getBytes(StandardCharsets.UTF_8));
        message.setDelayTimeLevel(delayLevel);

        try {
            log.info("mqProducer send mq topic:{}, tag:{}, key:{}, mqBody:{}, delayLevel:{}",
                    topic, tag, key, JSON.toJSONString(mqBody), delayLevel);
            SendResult sendResult = mqProducer.send(message);
            log.info("mqProducer send mq sendResult:{}", JSON.toJSONString(sendResult));
        } catch (Exception e) {
            log.error("mqProducer send mq error...topic:{}, tag:{}, key:{}, mqBody:{}, delayLevel:{}",
                    topic, tag, key, JSON.toJSONString(mqBody), delayLevel);
            e.printStackTrace();
        }
    }


}
