package com.woniu.controller;

import com.woniu.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("")
public class DemoController {

    @Autowired
    PayService payService;


    /**
     * 实战！从spring源码学到的优化冗余代码的一种方案，
     * 消除代码中的if else
     * @param code
     * @return
     */
    @GetMapping("/{code}")
    @ResponseBody
    public String doPay(@PathVariable("code") String code) {
        payService.pay(code);
        return "ok";
    }

}
