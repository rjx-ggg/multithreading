package com.example.shardingsphere.junit;

import com.example.shardingsphere.entity.UserEntity;
import com.example.shardingsphere.mapper.UserMapperXml;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * <p>
 * UserTest
 * </p>
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserMapperXml userMapperXml;

    @Test
    public void insert() throws Exception {
        UserEntity entity = new UserEntity();
        entity.setId(3l);
        entity.setEmail("123@123.com");
        entity.setNickName("蜗牛");
        entity.setPassWord("123");
        entity.setRegTime("2025-02-10 00:00:00");
        entity.setUserName("程序员蜗牛");
        entity.setSalary("2500");
        userMapperXml.insert(entity);
    }

    @Test
    public void query() throws Exception {
        List<UserEntity> dataList = userMapperXml.findAll();
        System.out.println(dataList.toString());
    }
}