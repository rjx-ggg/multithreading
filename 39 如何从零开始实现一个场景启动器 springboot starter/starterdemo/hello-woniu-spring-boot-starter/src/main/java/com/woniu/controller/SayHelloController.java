package com.woniu.controller;

import com.woniu.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/woniu")
public class SayHelloController {

    @Autowired
    private Person person;

    /**
     * 如何自定义一个场景启动器 springboot starter？
     * @return
     */
    @GetMapping("/sayHello")
    public String sayHello() {
        return "woniu say hello to somebody, name is " + person.getName() + ", age is " + person.getAge();
    }


}
