package com.zbl.springboot.user;

import com.alibaba.fastjson.JSON;
import com.zbl.springboot.dao.UserMapper;
import com.zbl.springboot.po.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/11/24 18:02
 */
@Slf4j
@SpringBootTest
@SuppressWarnings("unused")
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsert() {
        User user = new User();
        user.setSex("女");
        int count = userMapper.insert(user);
    }

    @Test
    void testUpdate() {
        User user = userMapper.selectById(1);
        user.setAge(25);
        user.setGmtModify(new Date());
        userMapper.updateById(user);
    }

    @Test
    public void testSelectById() {
        User user = userMapper.selectById(12);
        log.info("user:{}", user.toString());
        log.info("jsonFormat:{}", JSON.toJSONString(user));
    }
}
