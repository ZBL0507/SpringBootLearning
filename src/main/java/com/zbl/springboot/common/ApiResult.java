package com.zbl.springboot.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/12/5 13:29
 */
@Data
public class ApiResult<T> implements Serializable {
    //data of return
    private T data;

    //message of tip
    private String message;

    //code of return like http status
    private String code;

    private ApiResult(String code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>("0000", data, "成功");
    }

    public static <T> ApiResult<T> success(String code, T data) {
        return new ApiResult<>(code, data, "成功");
    }

    public static <T> ApiResult<T> success(String code, T data, String message) {
        return new ApiResult<>(code, data, message);
    }

    public static <T> ApiResult<T> fail(T data) {
        return new ApiResult<>("5000", data, "失败");
    }

    public static <T> ApiResult<T> fail(String message) {
        return new ApiResult<>("5000", null, message);
    }

    public static <T> ApiResult<T> fail(String code, T data) {
        return new ApiResult<>(code, data, "失败");
    }

    public static <T> ApiResult<T> fail(String code, T data, String message) {
        return new ApiResult<>(code, data, message);
    }
}
