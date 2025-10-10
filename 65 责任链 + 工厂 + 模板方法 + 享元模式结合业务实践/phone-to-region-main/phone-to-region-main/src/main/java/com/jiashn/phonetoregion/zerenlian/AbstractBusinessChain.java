package com.jiashn.phonetoregion.zerenlian;


import lombok.Data;

import java.util.Objects;

/**
 * <p>抽象实现链</p>
 */
@Data
public abstract class AbstractBusinessChain implements BusinessChain {

    protected BusinessChain next;

    private Boolean hasNext() {
        return Objects.nonNull(next);
    }

    @Override
    public void process(Object param) {
        Boolean r = executeProcess(param);
        if (Boolean.FALSE.equals(r) && Boolean.TRUE.equals(hasNext())) {
            next.process(param);
        }
    }

    protected abstract Boolean executeProcess(Object param);

    @Override
    public void setNext(BusinessChain next) {
        this.next = next;
    }
}
