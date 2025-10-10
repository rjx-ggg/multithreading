package com.woniu.db.check.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.db.check.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {

}
