package com.jiashn.phonetoregion.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author: woniu
 * @description:
 **/
@Data
@Accessors(chain = true)
public class RegionVo implements Serializable {
    /**
     * 手机号前缀（前7位）
     */
    private String phonePrefix;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 供应商
     */
    private String carrier;

    private static final long serialVersionUID = 1L;
}
