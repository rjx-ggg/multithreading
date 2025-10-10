package com.woniu.zerenlian;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class BankOrderHandleInterceptService implements OrderHandleIntercept {

    @Override
    public OrderAddContext handle(OrderAddContext context) {
        System.out.println("检查银行账户是否合法，调用银行系统检查银行账户余额是否满足下单金额");
        return context;
    }
}