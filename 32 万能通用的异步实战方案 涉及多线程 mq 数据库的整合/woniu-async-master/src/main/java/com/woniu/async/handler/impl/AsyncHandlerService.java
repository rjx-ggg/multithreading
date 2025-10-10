package com.woniu.async.handler.impl;

import java.util.Collections;
import java.util.List;

import com.woniu.async.enums.AsyncTypeEnum;
import com.woniu.async.handler.context.AsyncContext;
import org.springframework.stereotype.Component;

/**
 * 仅异步消息处理
 */
@Component
public class AsyncHandlerService extends AbstractHandlerService {

    @Override
    public List<String> listType() {
        return Collections.singletonList(AsyncTypeEnum.ASYNC.name());
    }

    @Override
    public boolean execute(AsyncContext context) {
        // 放入消息队列
        return asyncProducer.send(context.getAsyncExecDto());
    }
}
