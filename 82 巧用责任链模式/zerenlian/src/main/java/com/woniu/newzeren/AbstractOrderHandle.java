package com.woniu.newzeren;

import com.woniu.zerenlian.OrderAddContext;

public abstract class AbstractOrderHandle {
    /**
     * 责任链，下一个链接节点
     */
    private AbstractOrderHandle next;
    /**
     * 执行入口
     *
     * @param context
     * @return
     */
    public OrderAddContext execute(OrderAddContext context) {
        context = handle(context);
        // 判断是否还有下个责任链节点，没有的话，说明已经是最后一个节点
        if (getNext() != null) {
            getNext().execute(context);
        }
        return context;
    }

    /**
     * 对参数进行处理
     *
     * @param context
     * @return
     */
    public abstract OrderAddContext handle(OrderAddContext context);

    public AbstractOrderHandle getNext() {
        return next;
    }

    public void setNext(AbstractOrderHandle next) {
        this.next = next;
    }
}