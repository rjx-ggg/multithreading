package com.woniu.muban;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 微信支付
 */
@Slf4j
@Component
public class WxPay implements IPay {
    @Override
    public boolean supports(String code) {
        return "wx".equals(code);
    }

    @Override
    public void pay() {
         log.info("发起微信支付。。。");
    }
}
