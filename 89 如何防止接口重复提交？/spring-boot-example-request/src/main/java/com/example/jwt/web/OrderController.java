package com.example.jwt.web;

import com.example.jwt.req.OrderConfirmRequest;
import com.example.jwt.result.ResResult;
import com.example.jwt.submit.SubmitLimit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {


    /**
     * 如何防止接口重复提交？
     * 服务端通过一些规则组合生成本次请求唯一ID
     * @param request
     * @return
     */
    @SubmitLimit(customerHeaders = {"appId", "token"},
            customerTipMsg = "正在加紧为您处理，请勿重复下单！")
    @PostMapping(value = "confirm")
    public ResResult confirmOrder(@RequestBody OrderConfirmRequest request) throws InterruptedException {
        log.info("调用订单下单相关逻辑");
        TimeUnit.SECONDS.sleep(2);
        return ResResult.success();
    }
}