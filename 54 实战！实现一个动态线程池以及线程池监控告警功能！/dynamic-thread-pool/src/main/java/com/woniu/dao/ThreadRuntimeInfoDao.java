package com.woniu.dao;

import com.woniu.entity.ThreadRuntimeInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (ThreadRuntimeInfo)表数据库访问层
 */

public interface ThreadRuntimeInfoDao {

    /**
     * 通过ID查询单条数据
     * @param id 主键
     * @return 实例对象
     */
    ThreadRuntimeInfo queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param threadRuntimeInfo 查询条件
     * @param pageable          分页对象
     * @return 对象列表
     */
    List<ThreadRuntimeInfo> queryAllByLimit(ThreadRuntimeInfo threadRuntimeInfo, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param threadRuntimeInfo 查询条件
     * @return 总行数
     */
    long count(ThreadRuntimeInfo threadRuntimeInfo);

    /**
     * 新增数据
     *
     * @param threadRuntimeInfo 实例对象
     * @return 影响行数
     */
    int insert(ThreadRuntimeInfo threadRuntimeInfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ThreadRuntimeInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ThreadRuntimeInfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ThreadRuntimeInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ThreadRuntimeInfo> entities);

    /**
     * 修改数据
     *
     * @param threadRuntimeInfo 实例对象
     * @return 影响行数
     */
    int update(ThreadRuntimeInfo threadRuntimeInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}
