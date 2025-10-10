package com.woniu.entity;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

/**
* @desc：the module of table t_user
found table comment  用户表
* @date 2024/02/29
*/
@TableName("t_user")
public class TUser implements Serializable{
    private static final long serialVersionUID = 1L;
        //主键ID
        @TableId
        @TableField("id")
        private Integer id;

        //名字
        @TableField("name")
        private String name;

        //创建时间
        @TableField("create_time")
        private Date createTime;


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name == null ? null : name.trim();
        }
        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("pojo.TUser ").append("[");
                sb.append(", id = ").append(id);
                sb.append(", name = ").append(name);
                sb.append(", createTime = ").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}