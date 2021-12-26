package com.zbl.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbl.springboot.dto.PageResult;
import com.zbl.springboot.dto.PageSearch;
import com.zbl.springboot.po.User;

import java.util.Collection;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/11/25 10:47
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户id 判断是否存在该用户
     *
     * @param userId 用户id
     * @return 如果存在就返回true，否则返回false
     */
    boolean exist(Long userId);

    /**
     * 新建用户
     *
     * @param user 待新建用户的数据
     * @return 新建成功返回 true, 失败返回false
     */
    boolean create(User user);

    /**
     * 批量新建用户
     *
     * @param users 待新建用户的数据
     * @return 新建成功返回 true, 失败返回false
     */
    boolean createBatch(Collection<User> users);

    /**
     * 更新用户数据
     *
     * @param user 待更新的用户数据
     * @return 更新成功返回true, 失败返回false
     */
    boolean update(User user);

    /**
     * 根据用户id查找用户
     *
     * @param userId 用户id
     * @return 查找到的用户，如果没有找到则返回null
     */
    User get(Integer userId);

    /**
     * 查询分页数据
     *
     * @param pageSearch 分页参数
     * @return 一页的结果
     */
    PageResult<User> getPage(PageSearch pageSearch);
}
