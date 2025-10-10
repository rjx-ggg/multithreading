package com.woniu.dynamicsql.controller;

import com.woniu.dynamicsql.entity.CarInfoVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


//  如何优雅的实现数据脱敏？
@RestController
public class SerialController {
    @GetMapping("/car/info")
    @ResponseBody
    public CarInfoVO queryCarInfo() {
        CarInfoVO carInfoVO = new CarInfoVO();
        carInfoVO.setLicenseNumber("沪B08U28");
        carInfoVO.setOwnerMobile("162323545");
        carInfoVO.setOwnerIdCard("410328200001010007");
        carInfoVO.setOwnerBankCardNo("6214856213978533");
        carInfoVO.setOwnerName("蜗牛小哥哥");
        return carInfoVO;
    }





}