package com.example.jackson.controller;

import com.example.jackson.entity.UserEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * HelloController
 * </p>
 *  整合 jackson 轻松搞定接口数据脱敏
 * @author 程序员蜗牛
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public UserEntity hello() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1L);
        userEntity.setName("蜗牛");
        userEntity.setMobile("18000000001");
        userEntity.setIdCard("420117200001011000008888");
        userEntity.setAge(20);
        userEntity.setSex("男");
        return userEntity;
    }
}
