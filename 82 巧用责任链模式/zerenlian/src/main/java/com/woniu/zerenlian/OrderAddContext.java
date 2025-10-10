package com.woniu.zerenlian;

import lombok.Data;

/**
 * 巧妙利用 SpringBoot 责任连模式，让编程事半功倍！
 */
@Data
public class OrderAddContext {

    /**
     * 请求唯一序列ID
     */
    private String seqId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 产品skuId
     */
    private Long skuId;

    /**
     * 下单数量
     */
    private Integer amount;

    /**
     * 用户收货地址ID
     */
    private String userAddressId;

}