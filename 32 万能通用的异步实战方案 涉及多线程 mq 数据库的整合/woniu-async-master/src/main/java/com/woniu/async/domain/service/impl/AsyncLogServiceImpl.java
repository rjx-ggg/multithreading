package com.woniu.async.domain.service.impl;

import java.util.Date;

import com.woniu.async.domain.service.AsyncLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woniu.async.domain.dao.AsyncLogDao;
import com.woniu.async.domain.entity.AsyncLog;

/**
 * 异步执行日志接口实现
 */
@Service
public class AsyncLogServiceImpl implements AsyncLogService {

    @Autowired(required = false)
    private AsyncLogDao asyncLogDao;

    @Override
    public void save(AsyncLog asyncLog) {
        asyncLog.setCreateTime(new Date());
        asyncLogDao.save(asyncLog);
    }

    @Override
    public void delete(Long asyncId) {
        asyncLogDao.delete(asyncId);
    }

    @Override
    public String getErrorData(Long asyncId) {
        return asyncLogDao.getErrorData(asyncId);
    }
}
