package com.woniu.demo.model.request;


import lombok.Data;

@Data
public class InsertUserReq {


    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户手机号
     */
    private String userPhone;


}
