package com.woniu.scheduleTask.flownode;

import com.alibaba.fastjson.JSON;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * @Description 规则引擎执行器
 * @Date 2024-01-29
 */

@Slf4j
@Component
public class FlowExecutorService {


    @Autowired
    private FlowExecutor flowExecutor;


    /**
     * SpringBoot 整合规则引擎 liteflow 业务实战
     *
     * 订单完成后，进行积分的发放，mq消息发送，同时并行发送短信和邮件。
     * @param flowDto
     */
    @Async
    public void handleApp(AppFlowDto flowDto){
        // 使用的规则文件，传递参数，上下文对象
        LiteflowResponse response = flowExecutor.execute2Resp("test_flow", flowDto, AppFlowContext.class);

        // 获取流程执行后的结果
        if (!response.isSuccess()) {
            Exception e = response.getCause();
            log.warn(" error is {}", e.getCause(), e);
        }

        AppFlowContext context = response.getContextBean(AppFlowContext.class);
        log.info("handleApp 执行完成后 context {}", JSON.toJSONString(context));
    }



}
