package com.example.shardingsphere.mapper;

import com.example.shardingsphere.entity.UserEntity;

import java.util.List;

public interface UserMapperXml {


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

