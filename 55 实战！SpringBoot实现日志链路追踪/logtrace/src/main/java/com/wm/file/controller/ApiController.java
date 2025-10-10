package com.wm.file.controller;


import com.wm.file.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/")
@Slf4j
public class ApiController {

    @Autowired
    private OrderService orderService;

    @ResponseBody
    @GetMapping(value = "/pay/{orderSn}")
    public String alipay(@PathVariable("orderSn") String orderSn) {
        log.info("我开始了！");
        String result = orderService.alipay(orderSn);
        log.info("我结束了！{}",result);
        return result;
    }

    @ResponseBody
    @GetMapping(value = "/insert")
    public String insert() throws InterruptedException {
        log.info("我开始了！");
         orderService.insert();
        log.info("结束了！");
        return "ok";
    }

}
