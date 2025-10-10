package com.multi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * <p>
 * 用户表
 * </p>
 */
@Data
@TableName("t_user")
public class TUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    private String Id;

    /**
     * 用户名
     */
    @TableField("name")
    private String name;



}
