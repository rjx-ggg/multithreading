package com.woniu.async.biz;

import com.woniu.async.dto.AsyncExecDto;
import com.woniu.async.domain.entity.AsyncReq;

/**
 * 异步执行接口
 */
public interface AsyncBizService {

    /**
     * 执行方法
     * @param asyncReq
     * @return
     */
    boolean invoke(AsyncReq asyncReq);

    /**
     * 执行方法
     *
     * @param asyncExecDto
     * @return
     */
    boolean invoke(AsyncExecDto asyncExecDto);

}
