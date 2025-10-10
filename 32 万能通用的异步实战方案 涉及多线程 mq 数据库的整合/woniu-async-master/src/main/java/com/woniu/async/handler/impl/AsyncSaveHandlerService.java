package com.woniu.async.handler.impl;

import java.util.Collections;
import java.util.List;

import com.woniu.async.enums.AsyncTypeEnum;
import com.woniu.async.enums.ExecStatusEnum;
import org.springframework.stereotype.Component;

import com.woniu.async.domain.entity.AsyncReq;
import com.woniu.async.handler.context.AsyncContext;

import lombok.extern.slf4j.Slf4j;

/**
 * 先异步消息处理失败再保存数据库
 */
@Slf4j
@Component
public class AsyncSaveHandlerService extends AbstractHandlerService {

    @Override
    public List<String> listType() {
        return Collections.singletonList(AsyncTypeEnum.ASYNC_SAVE.name());
    }

    @Override
    public boolean execute(AsyncContext context) {
        // 放入消息队列
        boolean success = asyncProducer.send(context.getAsyncExecDto());
        if (success) {
            return true;
        }
        // 保存数据库
        AsyncReq asyncReq = this.saveAsyncReq(context.getAsyncExecDto(), ExecStatusEnum.ERROR.getStatus());
        return null != asyncReq;
    }
}
