package com.woniu.demo.config;


import com.woniu.demo.annotation.OperationLog;
import com.woniu.demo.entity.OperationLogVo;
import com.woniu.demo.service.impl.OperationLogService;
import com.woniu.demo.spring.SpringUtils;

import java.util.TimerTask;

public class AsyncFactory {

    /**
     * 记录操作日志
     * @param operationLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOperation(OperationLogVo operationLog) {
        return new TimerTask() {
            @Override
            public void run() {
                SpringUtils.getBean(OperationLogService.class).saveOperationLog(operationLog);
            }
        };
    }


}
