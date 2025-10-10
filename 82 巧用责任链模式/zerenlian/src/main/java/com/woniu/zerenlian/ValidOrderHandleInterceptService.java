package com.woniu.zerenlian;


import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class ValidOrderHandleInterceptService implements OrderHandleIntercept {

    @Override
    public OrderAddContext handle(OrderAddContext context) {
        System.out.println("检查请求参数，是否合法，并且获取客户的银行账户");
        return context;
    }
}