package com.woniu.muban;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ZfbPay implements IPay {
    @Override
    public boolean supports(String code) {
        return "zfb".equals(code);
    }

    @Override
    public void pay() {
        log.info("发起支付宝支付。。。");
    }
}
