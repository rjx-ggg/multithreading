package com.woniu.zerenlian;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;


@Component
public class OrderHandleChainService {


    @Autowired
    private List<OrderHandleIntercept> handleList;

    /**
     * 执行处理
     *
     * @param context
     * @return
     */
    public OrderAddContext execute(OrderAddContext context) {
        for (OrderHandleIntercept handleIntercept : handleList) {
            context = handleIntercept.handle(context);
        }
        return context;
    }
}