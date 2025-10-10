package com.woniu.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CarInfoVO implements Serializable {
    // 车牌号
    private String licenseNumber;

    // 手机号
    private String ownerMobile;

    // 身份证号
    private String ownerIdCard;

    // 银行卡
    private String ownerBankCardNo;

    // 自定义
    private String ownerName;
}
