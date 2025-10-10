package com.jiashn.phonetoregion.zerenlian;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * <p>第一个</p>
 */
@Component
public class FirstBusinessChain extends AbstractBusinessChain {

    @Override
    public Boolean executeProcess(Object param) {
        return Objects.nonNull(param);
    }
}

