package com.woniu.service;

import com.woniu.muban.IPay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PayService {

    @Autowired
    List<IPay> payList;

    public void pay(String code) {
        for (IPay iPay : payList) {
            if (iPay.supports(code)) {
                iPay.pay();
            }
        }
    }


}
