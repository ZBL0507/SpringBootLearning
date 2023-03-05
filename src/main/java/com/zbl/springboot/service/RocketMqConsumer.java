package com.zbl.springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 普通消息的消费者
 *
 * @author zbl
 * @version 1.0
 * @since 2023/3/5 11:52
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "zheng_chang_topic",
        consumerGroup = "consumerGroup_test001",
        selectorExpression = "test_tag"
)
public class RocketMqConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("普通消息的消费者.收到消息...:{}", message);
    }
}
