package com.woniu.demo.model.request;

import lombok.Data;

@Data
public class UpdateUserReq {

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户手机号
     */
    private String userPhone;

}
