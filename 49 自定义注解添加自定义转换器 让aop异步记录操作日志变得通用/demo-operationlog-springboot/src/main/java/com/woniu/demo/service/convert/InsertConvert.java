package com.woniu.demo.service.convert;


import com.woniu.demo.entity.OperationLogVo;
import com.woniu.demo.model.request.InsertUserReq;

public class InsertConvert implements Convert<InsertUserReq> {
    @Override
    public OperationLogVo convert(InsertUserReq insertUserReq) {
        OperationLogVo operationLogVo = new OperationLogVo();
        operationLogVo.setParams(insertUserReq.getUserName());
        return operationLogVo;
    }
}
