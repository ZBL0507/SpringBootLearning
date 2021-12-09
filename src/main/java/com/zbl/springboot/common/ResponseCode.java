package com.zbl.springboot.common;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/12/9 11:25
 */
public enum ResponseCode {

    session_timeout("507", "session超时");


    //响应码
    @Getter
    @Setter
    private String code;
    //响应消息

    @Getter
    @Setter
    private String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String toJsonStr() {
        HashMap<String, String> map = new HashMap<>();
        map.put("code", this.getCode());
        map.put("message", this.getMessage());
        return JSON.toJSONString(map);
    }
}
