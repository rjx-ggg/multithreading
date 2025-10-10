package com.woniu.newzeren;

import com.woniu.zerenlian.OrderAddContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class ValidOrderHandle extends AbstractOrderHandle {

    @Override
    public OrderAddContext handle(OrderAddContext context) {
        System.out.println("检查请求参数，是否合法，并且获取客户的银行账户");
        return context;
    }
}