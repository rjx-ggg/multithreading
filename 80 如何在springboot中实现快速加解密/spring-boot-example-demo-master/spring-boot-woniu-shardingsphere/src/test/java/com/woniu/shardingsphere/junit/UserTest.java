package com.woniu.shardingsphere.junit;

import com.woniu.shardingsphere.entity.UserEntity;
import com.woniu.shardingsphere.mapper.UserMapperDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserMapperDao userMapperDao;

    /**
     * 如何在 Spring Boot 中快速实现数据的加解密功能？
     *
     */
    @Test
    public void insert() throws Exception {
        UserEntity entity = new UserEntity();
        entity.setId(3L);
        entity.setEmail("123@123.com");
        entity.setNickName("woniu");
        entity.setPassWord("123");
        entity.setRegTime("2024-10-10 00:00:00");
        entity.setUserName("woniu");
        entity.setSalary("2500");
        userMapperDao.insert(entity);
    }

    @Test
    public void query() throws Exception {
        List<UserEntity> dataList = userMapperDao.findAll();
        System.out.println(dataList.toString());
    }
}