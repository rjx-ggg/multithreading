package com.woniu.newzeren;

import com.woniu.zerenlian.OrderAddContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class OrderHandleManager {

    @Autowired
    private List<AbstractOrderHandle> orderHandleList;


    @PostConstruct
    public void init() {
        int size = orderHandleList.size();
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                orderHandleList.get(i).setNext(null);
            } else {
                orderHandleList.get(i).setNext(orderHandleList.get(i + 1));
            }
        }

    }

    /**
     * 执行处理
     *
     * @param context
     * @return
     */
    public OrderAddContext execute(OrderAddContext context) {
        context = orderHandleList.get(0).execute(context);
        return context;
    }
}