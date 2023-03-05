package com.zbl.springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author zbl
 * @version 1.0
 * @since 2023/3/5 11:52
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "delay_test_topic",
        consumerGroup = "consumerGroup_test002",
        selectorExpression = "delay_test_tag"
)
public class RocketMqDelayConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("延迟消息的消费者.收到消息...:{}", message);
    }
}
