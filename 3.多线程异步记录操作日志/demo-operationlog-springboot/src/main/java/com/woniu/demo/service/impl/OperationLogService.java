package com.woniu.demo.service.impl;

import com.woniu.demo.annotation.OperationLog;
import com.woniu.demo.entity.OperationLogVo;
import com.woniu.demo.mapper.LogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperationLogService {

    private final LogMapper logMapper;

    public void saveOperationLog(OperationLogVo operationLog) {
        logMapper.insert(operationLog);
    }
}
