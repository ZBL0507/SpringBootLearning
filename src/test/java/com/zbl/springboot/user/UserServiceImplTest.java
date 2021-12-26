package com.zbl.springboot.user;

import com.alibaba.fastjson.JSON;
import com.zbl.springboot.dto.PageResult;
import com.zbl.springboot.dto.PageSearch;
import com.zbl.springboot.po.User;
import com.zbl.springboot.service.UserService;
import com.zbl.springboot.util.MyRandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/11/25 10:07
 */
@Slf4j
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
        //@Transactional 加上事务注解，测试完成数据会回滚，不会写入数据库
    void testInsert() {
        User user = new User();
        user.setName(MyRandomUtil.randomName());
        user.setAge(MyRandomUtil.randomAge());
        user.setSex(MyRandomUtil.randomSex());
        boolean res = userService.create(user);
        log.info("创建用户是否成功：{}", res);
    }

    @Test
    void testGet() {
        User user = userService.get(1);
        log.info("user:{}", JSON.toJSONString(user));
    }

    @Test
    void testUpdate() {
        User user = userService.get(3);
        user.setName(MyRandomUtil.randomName());
        boolean res = userService.update(user);
        log.info("更新结果:{}", res);
    }

    @Test
    void testCreateBatch() {
        log.info(Thread.currentThread().getName() + " 线程启动。。。");
        Collection<User> userList = new ArrayList<>();
        for (int i = 0; i < 10_0000; i++) {
            User user = new User();
            user.setName(MyRandomUtil.randomName());
            user.setAge(MyRandomUtil.randomAge());
            user.setSex(MyRandomUtil.randomSex());
            userList.add(user);
        }

        boolean batch = userService.createBatch(userList);
        log.info("批量插入结果：{}", batch);
    }

    @SuppressWarnings("unused")
    @Test
    void testGetPage() {
        PageSearch pageSearch = new PageSearch();
        pageSearch.setPageIndex(2);
        PageResult<User> page = userService.getPage(pageSearch);
    }

    @Test
    public void testMutiThreadInsert() {
        long time = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.submit(this::testCreateBatch);
        executorService.submit(this::testCreateBatch);
        executorService.submit(this::testCreateBatch);
        executorService.submit(this::testCreateBatch);

        long useTime = System.currentTimeMillis() - time;
        log.info("useTime: {}", useTime);

    }

    @Test
    public void testExist() {
        boolean exist = userService.exist(23L);
        log.info("该用户是否存在:{}", exist);
    }
}
