package com.woniu.zerenlian;

public interface OrderHandleIntercept {


    /**
     * 对参数进行处理
     *
     * @param context
     * @return
     */
    OrderAddContext handle(OrderAddContext context);
}