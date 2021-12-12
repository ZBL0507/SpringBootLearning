package com.zbl.springboot.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/12/12 16:36
 */
@Data
public class LoginParaDTO implements Serializable {

    private Long userId;

    private String loginCode;
}
