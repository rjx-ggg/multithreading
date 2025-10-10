package com.multi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.multi.entity.DataSourceEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 */
@Mapper
public interface TenantInfoMapper extends BaseMapper<DataSourceEntity> {

    List<DataSourceEntity> findAll();
}
