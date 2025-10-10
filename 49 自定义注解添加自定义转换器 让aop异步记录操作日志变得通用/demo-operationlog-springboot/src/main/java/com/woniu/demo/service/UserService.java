package com.woniu.demo.service;

import com.woniu.demo.model.request.InsertUserReq;
import com.woniu.demo.model.request.UpdateUserReq;
import org.springframework.http.ResponseEntity;

public interface UserService {


    /**
     * 添加用户
     *
     * @param addReq 参数
     * @return 添加结果
     */
    ResponseEntity<String> addUser(InsertUserReq addReq);

    ResponseEntity<String> updateUser(UpdateUserReq updateUserReq);
}
