package com.zbl.springboot.user;

import com.zbl.springboot.dao.UserMapper;
import com.zbl.springboot.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/11/24 18:02
 */
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
}
