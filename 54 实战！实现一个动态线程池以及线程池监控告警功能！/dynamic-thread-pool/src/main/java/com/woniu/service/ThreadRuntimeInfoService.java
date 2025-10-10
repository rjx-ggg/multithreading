package com.woniu.service;

import com.woniu.entity.ThreadRuntimeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (ThreadRuntimeInfo)表服务接口
 *
 * @author Cat
 * @since 2022-03-20 15:46:03
 */
public interface ThreadRuntimeInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ThreadRuntimeInfo queryById(Long id);

    /**
     * 分页查询
     *
     * @param threadRuntimeInfo 筛选条件
     * @param pageRequest       分页对象
     * @return 查询结果
     */
    Page<ThreadRuntimeInfo> queryByPage(ThreadRuntimeInfo threadRuntimeInfo, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param threadRuntimeInfo 实例对象
     * @return 实例对象
     */
    ThreadRuntimeInfo insert(ThreadRuntimeInfo threadRuntimeInfo);

    /**
     * 修改数据
     *
     * @param threadRuntimeInfo 实例对象
     * @return 实例对象
     */
    ThreadRuntimeInfo update(ThreadRuntimeInfo threadRuntimeInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
