package com.woniu.service.impl;

import com.woniu.entity.ThreadRuntimeInfo;
import com.woniu.dao.ThreadRuntimeInfoDao;
import com.woniu.service.ThreadRuntimeInfoService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (ThreadRuntimeInfo)表服务实现类
 */
@Service("threadRuntimeInfoService")
public class ThreadRuntimeInfoServiceImpl implements ThreadRuntimeInfoService {

    @Resource
    private ThreadRuntimeInfoDao threadRuntimeInfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ThreadRuntimeInfo queryById(Long id) {
        return this.threadRuntimeInfoDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param threadRuntimeInfo 筛选条件
     * @param pageRequest       分页对象
     * @return 查询结果
     */
    @Override
    public Page<ThreadRuntimeInfo> queryByPage(ThreadRuntimeInfo threadRuntimeInfo, PageRequest pageRequest) {
        long total = this.threadRuntimeInfoDao.count(threadRuntimeInfo);
        return new PageImpl<>(this.threadRuntimeInfoDao.queryAllByLimit(threadRuntimeInfo, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param threadRuntimeInfo 实例对象
     * @return 实例对象
     */
    @Override
    public ThreadRuntimeInfo insert(ThreadRuntimeInfo threadRuntimeInfo) {
        this.threadRuntimeInfoDao.insert(threadRuntimeInfo);
        return threadRuntimeInfo;
    }

    /**
     * 修改数据
     *
     * @param threadRuntimeInfo 实例对象
     * @return 实例对象
     */
    @Override
    public ThreadRuntimeInfo update(ThreadRuntimeInfo threadRuntimeInfo) {
        this.threadRuntimeInfoDao.update(threadRuntimeInfo);
        return this.queryById(threadRuntimeInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.threadRuntimeInfoDao.deleteById(id) > 0;
    }
}
