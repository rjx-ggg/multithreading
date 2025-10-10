package com.woniu.address;

import lombok.Data;

/**
 * @author  * @date 2023/06/02
 * @version 1.0.0
 * @date 2023/06/02
 * @description 地址直接性查询对象
 * @since 1.0.0
 */
@Data
//@ApiModel("(地址)直接性查询对象")
public class AddressQueries {
    /**
     * 用户ID
     */
    //@ApiModelProperty(value = "用户ID")
    private Long userId;
    /**
     * 用户手机号
     */
    //@ApiModelProperty(value = "用户手机号")
    private String phone;
}
