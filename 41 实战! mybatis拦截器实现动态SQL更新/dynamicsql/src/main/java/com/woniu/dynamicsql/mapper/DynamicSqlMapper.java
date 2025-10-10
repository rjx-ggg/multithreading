package com.woniu.dynamicsql.mapper;

import com.woniu.dynamicsql.anno.DynamicSql;
import org.apache.ibatis.annotations.Mapper;

/**
 * 实战！mybatis拦截器实现动态SQL更新
 */
@Mapper
public interface DynamicSqlMapper {
    @DynamicSql
    Long count();
}