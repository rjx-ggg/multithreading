package com.woniu.connection.service;

import com.woniu.connection.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoginService {



    public User login(String userName, String passWord) {
        //假设登录成功
        if (!StringUtils.isEmpty(userName)&& !StringUtils.isEmpty(passWord)) {
            User user1 = new User();
            user1.setUserName("蜗牛");
            return user1;
        }
        return null;
    }
}
