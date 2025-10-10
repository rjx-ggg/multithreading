package com.woniu.shardingsphere.controller;


import com.woniu.shardingsphere.entity.UserEntity;
import com.woniu.shardingsphere.mapper.UserMapperDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("systemUser")
@Slf4j
public class Controller {

    @Autowired
    UserMapperDao userMapperDao;

    @PostMapping("/insert")
    public void insert() {
        UserEntity entity = new UserEntity();
        entity.setId(3L);
        entity.setEmail("123@123.com");
        entity.setNickName("woniu");
        entity.setPassWord("123");
        entity.setRegTime("2021-10-10 00:00:00");
        entity.setUserName("woniu");
        entity.setSalary("2500");
        userMapperDao.insert(entity);
    }

}
