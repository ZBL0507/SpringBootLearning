package com.zbl.springboot.service;

import com.zbl.springboot.dao.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/11/24 17:58
 */
@Service
public class UserServiceImpl {

    @SuppressWarnings("unused")
    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
