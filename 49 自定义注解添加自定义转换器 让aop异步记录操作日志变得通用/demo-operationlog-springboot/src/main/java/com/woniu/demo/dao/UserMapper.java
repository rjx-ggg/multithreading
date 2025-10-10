package com.woniu.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.demo.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}