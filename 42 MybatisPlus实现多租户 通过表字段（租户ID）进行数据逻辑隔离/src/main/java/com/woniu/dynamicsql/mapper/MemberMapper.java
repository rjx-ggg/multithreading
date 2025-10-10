package com.woniu.dynamicsql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.dynamicsql.entity.Member;
import org.apache.ibatis.annotations.Mapper;

/**
 * 实战！mybatis拦截器实现动态SQL更新
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

    Long count();
}