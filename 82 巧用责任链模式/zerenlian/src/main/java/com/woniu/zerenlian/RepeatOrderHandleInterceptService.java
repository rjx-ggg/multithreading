package com.woniu.zerenlian;


import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class RepeatOrderHandleInterceptService implements OrderHandleIntercept {


    @Override
    public OrderAddContext handle(OrderAddContext context) {
        System.out.println("检查客户是否重复下单");
        return context;
    }
}