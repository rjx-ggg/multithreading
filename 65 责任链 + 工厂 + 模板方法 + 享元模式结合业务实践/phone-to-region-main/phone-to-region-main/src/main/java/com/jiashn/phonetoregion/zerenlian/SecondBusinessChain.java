package com.jiashn.phonetoregion.zerenlian;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * <p>第二个</p>
 */
@Component
public class SecondBusinessChain extends AbstractBusinessChain {

    @Override
    public Boolean executeProcess(Object param) {
        return Objects.nonNull(param);
    }
}

