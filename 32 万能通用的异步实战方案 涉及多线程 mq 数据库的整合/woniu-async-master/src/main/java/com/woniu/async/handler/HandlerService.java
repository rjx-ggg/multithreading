package com.woniu.async.handler;

import com.woniu.async.handler.context.AsyncContext;
import com.woniu.async.strategy.StrategyService;

/**
 * 异步执行接口
 */
public interface HandlerService extends StrategyService<AsyncContext> {

    /**
     * 执行异步策略
     * 
     * @param context
     * @return
     */
    boolean execute(AsyncContext context);
}
