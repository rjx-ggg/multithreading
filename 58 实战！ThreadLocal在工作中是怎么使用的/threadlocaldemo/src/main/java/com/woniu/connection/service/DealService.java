package com.woniu.connection.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DealService {

    static final ThreadLocal<String> threadLocal = new ThreadLocal();

    @Autowired
    private AService aService;

    public void dealTransfer() {
        String address = "nanjing";
        threadLocal.set(address);
        A();
    }

    private void A() {
        B();
    }

    private void B() {
        aService.C();
    }
}
