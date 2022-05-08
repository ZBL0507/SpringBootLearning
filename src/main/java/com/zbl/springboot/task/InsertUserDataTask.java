package com.zbl.springboot.task;

import com.zbl.springboot.po.User;
import com.zbl.springboot.service.UserService;
import com.zbl.springboot.util.MyRandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author zbl
 * @version 1.0
 * @since 2022/5/8 16:13
 */
@Component
@Slf4j
public class InsertUserDataTask {

    @Resource
    private UserService userService;

//    @Scheduled(cron = "*/20 * * * * ?")
    public void addUserTask() {
        log.info(Thread.currentThread().getName() + " 线程启动。。。");
        Collection<User> userList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            User user = new User();
            user.setName(MyRandomUtil.randomName());
            user.setAge(MyRandomUtil.randomAge());
            user.setSex(MyRandomUtil.randomSex());
            userList.add(user);
        }

        boolean batch = userService.createBatch(userList);
        log.info(Thread.currentThread().getName() + " 批量插入结果：{}", batch);
    }
}
