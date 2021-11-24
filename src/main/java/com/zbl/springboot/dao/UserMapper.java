package com.zbl.springboot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbl.springboot.po.User;
import org.springframework.stereotype.Repository;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/11/24 17:58
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
