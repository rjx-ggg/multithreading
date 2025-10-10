package com.woniu.demo.controller;


import com.woniu.demo.annotation.OperationLog;
import com.woniu.demo.model.request.InsertUserReq;
import com.woniu.demo.model.request.UpdateUserReq;
import com.woniu.demo.service.UserService;
import com.woniu.demo.service.convert.InsertConvert;
import com.woniu.demo.service.convert.UpdateConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * spring aop你真的会用吗？
     * 如何适配操作日志不同请求参数变得通用！
     * @param addReq
     * @return
     */
    @PostMapping("/add")
    @OperationLog(value = "添加用户",convert = InsertConvert.class)// 这里可以写上操作日志的描述
    public ResponseEntity<String> addUser(@RequestBody InsertUserReq addReq) {
        return userService.addUser(addReq);
    }

    @PostMapping("/update")
    @OperationLog(value = "修改用户",convert = UpdateConvert.class)
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserReq updateUserReq) {
        return userService.updateUser(updateUserReq);
    }


}
