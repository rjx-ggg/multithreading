package com.jiashn.phonetoregion.utils;

import com.jiashn.phonetoregion.domain.RegionVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.*;

/**
 * @author: woniu
 * @description:
 **/
@Component
public class PhoneToRegionCsvUtil {

    private final static String PHONE_REGION_KEY = "country_phone_region_info";

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 根据手机号获取手机归属地
     * @param phone 手机号
     * @return 归属地信息
     */
    public RegionVo getPhoneToRegion(String phone){
        String prefix = StringUtils.substring(phone, 0, 7);
        Object region = redisTemplate.opsForHash().get(PHONE_REGION_KEY, prefix);
        return Objects.isNull(region) ? new RegionVo() : (RegionVo) region;
    }
}
