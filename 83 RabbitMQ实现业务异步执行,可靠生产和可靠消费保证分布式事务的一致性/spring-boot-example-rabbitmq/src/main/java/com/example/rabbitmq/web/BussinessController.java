package com.example.rabbitmq.web;

import com.example.rabbitmq.entity.BussinessEntity;
import com.example.rabbitmq.service.ProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * RabbitConfig
 * </p>
 *
 * @author 程序员蜗牛
 */
@RestController
public class BussinessController {

    @Autowired
    private ProduceService produceService;


    @PostMapping("sendMailToDB")
    public String sendMailToDB(BussinessEntity bussinessEntity) {
        boolean result = produceService.sendByAuto(bussinessEntity);
        return result ? "success" : "fail";
    }
}
