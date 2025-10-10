package com.woniu.trim.controller;

import com.alibaba.fastjson.JSONObject;
import com.woniu.trim.model.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 测试接口
 * 实战！过滤器结合装饰模式，改变请求参数，去除请求参数前后空格。
 * @author 程序员蜗牛
 */
@Slf4j
@RestController
public class DemoController {


    @GetMapping(value = "/shouDongTrim")
    public void shouDongTrim(String userName) {
        //手动去空格
        userName = userName == null ? null : userName.trim();
        //或者通过工具类手动去空格
        String trim = StringUtils.trim(userName);
    }


    /**
     * 1、get方法测试首尾去空格
     * http://localhost:8080/getTrim?username=张三 &phone= 18812345678
     */
    @GetMapping(value = "/getTrim")
    public String getTrim( String username,  String phone) {
        return username + "&" + phone;
    }

    /**
     * 2、post方法测试首尾去空格
     */
    @PostMapping(value = "/postTrim")
    public String postTrim( String username, String phone) {
        return username + "&" + phone;
    }

    /**
     * 3、post方法 Content-Type为application/json 测试首尾去空格
     */
    @PostMapping(value = "/postJsonTrim")
    public String helloUser(@RequestBody UserDO userDO) {
        return JSONObject.toJSONString(userDO);
    }
}
