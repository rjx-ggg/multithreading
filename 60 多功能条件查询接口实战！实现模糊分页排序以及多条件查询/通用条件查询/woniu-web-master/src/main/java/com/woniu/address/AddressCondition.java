package com.woniu.address;

import lombok.Data;

/**
 * @author woniu
 * @version 1.0.0
 * @description 地址基本条件查询对象
 * @since 1.0.0
 */
@Data
//@ApiModel("(地址)基本条件查询对象")
public class AddressCondition {
    /**
     * 省
     */
    //@ApiModelProperty(value = "省")
    private String province;
    /**
     * 城市
     */
    //@ApiModelProperty(value = "城市")
    private String city;
    /**
     * 县/区
     */
    //@ApiModelProperty(value = "县/区")
    private String district;
}
