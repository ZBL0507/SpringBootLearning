package com.zbl.springboot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/11/22 10:37
 */

@Slf4j
//@SpringBootTest
class LogTest {

    @Test
    void testLog() {
        log.info("这是一条测试日志");
        new Thread(() -> log.error("线程日志测试")).start();
    }
}
