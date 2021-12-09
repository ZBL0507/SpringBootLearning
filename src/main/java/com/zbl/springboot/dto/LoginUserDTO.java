package com.zbl.springboot.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/12/9 12:03
 */
@Data
public class LoginUserDTO implements Serializable {

    private Long id;

    private String name;

    private long expire;
}
