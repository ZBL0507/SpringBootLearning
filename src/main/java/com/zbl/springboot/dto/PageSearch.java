package com.zbl.springboot.dto;

import lombok.Data;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/11/25 11:13
 */
@Data
public class PageSearch {
    /**
     * 页码
     */
    private Integer pageIndex = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;
}
