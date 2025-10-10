package com.woniu.demo.service.convert;


import com.woniu.demo.entity.OperationLogVo;
import com.woniu.demo.model.request.UpdateUserReq;

public class UpdateConvert implements Convert<UpdateUserReq> {
    @Override
    public OperationLogVo convert(UpdateUserReq updateUserReq) {
        OperationLogVo operationLogVo = new OperationLogVo();
        operationLogVo.setParams(updateUserReq.getName());
        return operationLogVo;
    }
}
