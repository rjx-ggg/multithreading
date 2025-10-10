package com.jiashn.phonetoregion.zerenlian;

/**
 *责任链 + 工厂 + 模板方法 + 享元模式结合业务实践
 */
public interface BusinessChain {

    void process(Object param);

    void setNext(BusinessChain next);

}
