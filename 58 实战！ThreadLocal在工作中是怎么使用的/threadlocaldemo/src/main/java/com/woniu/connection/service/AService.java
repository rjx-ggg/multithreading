package com.woniu.connection.service;

import org.springframework.stereotype.Service;

@Service
public class AService {

    public void C() {
        String s = DealService.threadLocal.get();
        //做处理
        System.out.println(s);
        DealService.threadLocal.remove();
    }

}
