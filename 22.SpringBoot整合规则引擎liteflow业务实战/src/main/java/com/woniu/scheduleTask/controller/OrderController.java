package com.woniu.scheduleTask.controller;

import com.woniu.scheduleTask.flownode.AppFlowDto;
import com.woniu.scheduleTask.flownode.FlowExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderController {

    @Autowired
    private FlowExecutorService flowService;


    /**
     * 流程信息
     */
    @GetMapping(value = "flow")
    public String flow() {
        AppFlowDto cxt = new AppFlowDto();
        cxt.setBusId("woniu");
        flowService.handleApp(cxt);
        return "success";
    }
}
