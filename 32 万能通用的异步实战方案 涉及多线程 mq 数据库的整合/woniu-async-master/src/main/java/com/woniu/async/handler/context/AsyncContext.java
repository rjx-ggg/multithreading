package com.woniu.async.handler.context;

import com.woniu.async.annotation.AsyncExec;
import com.woniu.async.dto.AsyncExecDto;
import com.woniu.async.strategy.context.StrategyContext;
import org.aspectj.lang.ProceedingJoinPoint;

import lombok.Data;

/**
 * AsyncContext
 */
@Data
public class AsyncContext extends StrategyContext {

    private static final long serialVersionUID = 1L;

    /**
     * 切面方法
     */
    private ProceedingJoinPoint joinPoint;

    /**
     * 异步执行策略
     */
    private AsyncExec asyncExec;

    /**
     * 异步执行数据
     */
    private AsyncExecDto asyncExecDto;

}
