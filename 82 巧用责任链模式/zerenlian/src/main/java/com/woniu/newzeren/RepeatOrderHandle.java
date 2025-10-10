package com.woniu.newzeren;


import com.woniu.zerenlian.OrderAddContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class RepeatOrderHandle extends AbstractOrderHandle {

    @Override
    public OrderAddContext handle(OrderAddContext context) {
        System.out.println("通过seqId，检查客户是否重复下单");
        return context;
    }
}
