package com.woniu.shardingsphere.mapper;

import com.woniu.shardingsphere.entity.UserEntity;

import java.util.List;


public interface UserMapperDao {


    /**
     * 查询所有的信息
     * @return
     */
    List<UserEntity> findAll();

    /**
     * 新增数据
     * @param user
     */
    void insert(UserEntity user);
}

