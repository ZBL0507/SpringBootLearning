package com.zbl.springboot.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zbl
 * @version 1.0
 * @since 2021/11/24 17:52
 */
@Data
@TableName("user")
public class User implements Serializable {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别: 男，女，未知
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date gmtCreated;

    /**
     * 最后修改时间
     */
    private Date gmtModify;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 最后修改人
     */
    private String modifier;


}
