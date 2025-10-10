package com.woniu.demo.service.convert;

import com.woniu.demo.entity.OperationLogVo;

/**
 * @className: Convert
 * @author: woniu
 * @date: 2024/3/8
 **/
public interface Convert<T> {

    OperationLogVo convert(T t);
}
