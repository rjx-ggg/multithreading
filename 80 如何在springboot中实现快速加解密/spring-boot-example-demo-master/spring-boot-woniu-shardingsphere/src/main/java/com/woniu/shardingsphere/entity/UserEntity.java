package com.woniu.shardingsphere.entity;


import lombok.Data;

@Data
public class UserEntity {

    private Long id;

    private String email;

    private String nickName;

    private String passWord;

    private String regTime;

    private String userName;

    private String salary;

}
