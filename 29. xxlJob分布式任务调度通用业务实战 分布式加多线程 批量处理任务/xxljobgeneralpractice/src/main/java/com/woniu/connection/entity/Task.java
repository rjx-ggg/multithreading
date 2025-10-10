package com.woniu.connection.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("task")
public class Task {

    @TableId(type = IdType.AUTO)
    private int id;

    // 任务名称
    @TableField("task_name")
    private String taskName;

    // 任务状态 1-初始化，2-执行成功，3 执行失败 4.正在执行中
    @TableField("task_status")
    private int taskStatus;

    // 失败次数 <3
    @TableField("fail_count")
    private int failCount;

    // 错误信息
    @TableField("error_msg")
    private String errorMsg;

    // 创建时间
    @TableField("create_time")
    private Date createTime;

    // 更新时间
    @TableField("update_time")
    private Date updateTime;

}
