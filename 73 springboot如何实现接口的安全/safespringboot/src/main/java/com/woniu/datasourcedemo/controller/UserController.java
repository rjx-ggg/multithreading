package com.woniu.datasourcedemo.controller;

import com.woniu.datasourcedemo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class UserController {


    /**
     * idea如何本地调试远程服务器的代码？
     * @param id
     * @return
     */
    @GetMapping("/v1/user/{id}")
    public User getById1(@PathVariable String id) {
        System.out.println("woniu is good person");
        return new User("蜗牛","18");
    }



}
