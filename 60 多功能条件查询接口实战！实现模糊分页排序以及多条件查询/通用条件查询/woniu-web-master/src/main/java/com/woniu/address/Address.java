package com.woniu.address;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("address")
@Data
public class Address implements Serializable {


    @TableId(type = IdType.AUTO)
    private String id;

    @TableField("phone")
    private String phone;

    @TableField("address")
    private String address;

    @TableField("city")
    private String city;
}
