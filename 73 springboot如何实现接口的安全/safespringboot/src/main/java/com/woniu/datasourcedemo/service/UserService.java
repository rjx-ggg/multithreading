package com.woniu.datasourcedemo.service;

import com.woniu.datasourcedemo.dao.UserDao;
import com.woniu.datasourcedemo.model.User;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//@Service
public class UserService {

//
//    @Resource
//    UserDao userDao;


//    @DS("ds1")
    public User getByUserId1(String userId) {
//        return userDao.getById(Integer.parseInt(userId));\
        return null;
    }

//    @DS("ds2")
//    public User getByUserId2(String userId) {
//        return userDao.getById(Integer.parseInt(userId));
//    }


//    @DS("ds1")
//    public void insertUserDs1(String name,String age) {
//         userDao.insertUserDs1(name,age);
//    }
//
//    @DS("ds2")
//    public void insertUserDs2(String name,String age) {
//         userDao.insertUserDs2(name,age);
//    }
//
//    @DSTransactional
//    public void insertUserAll(String name, String age){
//        UserService userService = (UserService) AopContext.currentProxy();
//        userService.insertUserDs1(name,age);
//        userService.insertUserDs2(name,age);
//        int i =1/0;
//    }



}
