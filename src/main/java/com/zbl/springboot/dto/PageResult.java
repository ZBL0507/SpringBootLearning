package com.zbl.springboot.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/11/25 11:10
 */
@Data
public class PageResult<T> {
    /**
     * 总记录数
     */
    private Long total;

    /**
     * 页数据
     */
    private List<T> list;
}
