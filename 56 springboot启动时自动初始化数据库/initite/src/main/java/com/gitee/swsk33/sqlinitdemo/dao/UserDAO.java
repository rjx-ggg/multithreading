package com.gitee.swsk33.sqlinitdemo.dao;

import com.gitee.swsk33.sqlinitdemo.dataobject.User;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO extends BaseMapper<User> {
}