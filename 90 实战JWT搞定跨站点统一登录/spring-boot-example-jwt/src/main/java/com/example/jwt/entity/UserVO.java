package com.example.jwt.entity;

import java.io.Serializable;


public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 用户登录账户
     */
    private String userNo;

    /**
     * 用户中文名
     */
    private String userName;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
