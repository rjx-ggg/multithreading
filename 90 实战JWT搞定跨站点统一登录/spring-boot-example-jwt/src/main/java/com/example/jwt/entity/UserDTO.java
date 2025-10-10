package com.example.jwt.entity;

import java.io.Serializable;


public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户登录账户
     */
    private String userNo;


    private String userPwd;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
