package com.jiashn.phonetoregion.zerenlian;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <p>业务链链工厂</p>
 */
@Component
public class BusinessChainFactory {

    @Autowired
    @Qualifier("firstBusinessChain")
    private BusinessChain firstBusinessChain;

    @Autowired
    @Qualifier("secondBusinessChain")
    private BusinessChain sencondBusinessChain;

    @Autowired
    @Qualifier("thirdBusinessChain")
    private BusinessChain thirdBusinessChain;

    @PostConstruct
    public void init() {
        firstBusinessChain.setNext(sencondBusinessChain);
        sencondBusinessChain.setNext(thirdBusinessChain);
        thirdBusinessChain.setNext(null);
    }

    public BusinessChain businessChain() {
        return firstBusinessChain;
    }

}
