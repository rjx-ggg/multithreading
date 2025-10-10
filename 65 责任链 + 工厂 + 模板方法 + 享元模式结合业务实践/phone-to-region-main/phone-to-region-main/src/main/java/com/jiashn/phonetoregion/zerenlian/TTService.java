package com.jiashn.phonetoregion.zerenlian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TTService {

    @Autowired
    BusinessChainFactory businessChainFactory;

    public void testTT() {
        // 创建并获取业务链
        BusinessChain businessChain = businessChainFactory.businessChain();
        businessChain.process("tt");
    }


}
