package com.zbl.springboot.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/11/23 14:43
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionLog {

    @ExceptionHandler(Throwable.class)
    public Object handleException(Throwable e) {
        log.error("", e);
        return null;
    }
}
