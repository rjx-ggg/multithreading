package com.jiashn.phonetoregion.zerenlian;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * <p>第三个</p>
 */
@Component
public class ThirdBusinessChain extends AbstractBusinessChain {

    @Override
    public Boolean executeProcess(Object param) {
        return Objects.nonNull(param);
    }
}