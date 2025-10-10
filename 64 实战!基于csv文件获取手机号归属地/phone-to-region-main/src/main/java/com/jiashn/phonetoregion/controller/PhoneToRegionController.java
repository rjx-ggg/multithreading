package com.jiashn.phonetoregion.controller;

import com.jiashn.phonetoregion.utils.JsonResult;
import com.jiashn.phonetoregion.utils.PhoneToRegionCsvUtil;
import com.jiashn.phonetoregion.utils.PhoneToRegionUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * SpringBoot通过手机号获取归属地
 * @author: woniu
 * @description:
 **/
@RestController
@RequestMapping("/phone")
public class PhoneToRegionController {

    @GetMapping("/getPhoneInfo/{phone}")
    public JsonResult<?> getPhoneInfo(@PathVariable("phone") String phone){
        return JsonResult.success(PhoneToRegionUtil.getPhoneInfo(phone));
    }



    @Resource
    private PhoneToRegionCsvUtil phoneToRegionCsvUtil;

    /**
     * 基于CSV文件获取手机号归属地
     * 18351905XXX
     * @param phone
     * @return
     */
    @GetMapping("/getPhoneGeoInfoByCsv/{phone}")
    public JsonResult<?> getPhoneGeoInfoByCsv(@PathVariable("phone") String phone){
        return JsonResult.success(phoneToRegionCsvUtil.getPhoneToRegion(phone));
    }
}
