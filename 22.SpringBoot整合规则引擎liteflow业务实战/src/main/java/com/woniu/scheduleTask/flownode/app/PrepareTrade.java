package com.woniu.scheduleTask.flownode.app;

import com.alibaba.fastjson.JSONObject;


import com.woniu.scheduleTask.flownode.AppFlowContext;
import com.woniu.scheduleTask.flownode.AppFlowDto;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;



/**
 * @Description 数据准备和校验处理
 * @Date 2024-01-31 2:28 PM
 */

@Slf4j
@Component(value = "prepareTrade")
public class PrepareTrade extends NodeComponent {

    @Override
    public void process() throws Exception {
        log.info("交易完成后业务处理数据准备和校验");
        //拿到请求参数
        AppFlowDto req = this.getSlot().getRequestData();
        // 参数校验
        checkParam(req);
        // 设置上下文对象 设置一些参数
        AppFlowContext context = this.getContextBean(AppFlowContext.class);
        context.setText("text");
    }

    /**
     * 参数校验
     * @param req
     */
    private void checkParam(AppFlowDto req) {
        if (req == null || StringUtils.isBlank(req.getBusId())) {
            throw new RuntimeException("入参不能为空");
        }
    }


}
