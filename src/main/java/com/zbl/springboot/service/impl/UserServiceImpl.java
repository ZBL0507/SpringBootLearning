package com.zbl.springboot.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbl.springboot.dao.UserMapper;
import com.zbl.springboot.dto.PageResult;
import com.zbl.springboot.dto.PageSearch;
import com.zbl.springboot.po.User;
import com.zbl.springboot.service.UserService;
import com.zbl.springboot.util.UserCacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/11/24 17:58
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @SuppressWarnings("all")
    @Autowired
    private UserService userService;

    @SuppressWarnings("all")
    @Autowired
    private UserCacheUtil userCacheUtil;

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean exist(Long userId) {
        User user = userMapper.selectById(userId);
        return user != null;
    }

    @Override
    public boolean create(User user) {
        user.setGmtCreated(new Date());
        user.setGmtModify(new Date());
        int count = userMapper.insert(user);
        return count > 0;
    }


    @Override
    public boolean createBatch(Collection<User> users) {
        final Date date = new Date();
        users.forEach(e -> {
            e.setGmtCreated(date);
            e.setGmtModify(date);
        });

        long startTime = System.currentTimeMillis();
        boolean res = userService.saveBatch(users);
        log.info("批量插入耗时：{}", System.currentTimeMillis() - startTime);
        return res;
    }

    @Override
    public boolean update(User user) {
        if (user.getId() == null || user.getId() <= 0) {
            throw new RuntimeException("please key in correct user id");
        }
        //清除缓存
        userCacheUtil.delUser(user.getId());

        user.setGmtModify(new Date());
        int count = userMapper.updateById(user);
        return count > 0;
    }

    @Override
    public User get(Integer userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("please key in correct user id");
        }

        User user = userCacheUtil.getUser(userId.longValue());
        if (null != user) {
            log.info("从缓存中获得user数据:{}", userId);
            return user;
        }
        user = userMapper.selectById(userId);
        userCacheUtil.saveUser(user);
        return user;
    }

    @Override
    public PageResult<User> getPage(PageSearch pageSearch) {
        IPage<User> page = new Page<>(pageSearch.getPageIndex(), pageSearch.getPageSize());
        page = userMapper.selectPage(page, null);
        PageResult<User> pageResult = new PageResult<>();
        pageResult.setTotal(page.getTotal());
        pageResult.setList(page.getRecords());
        return pageResult;
    }
}
