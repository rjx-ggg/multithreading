package com.tuling.datasource.dynamic.mybatis.service;


import com.tuling.datasource.dynamic.mybatis.entity.Frend;

import java.util.List;

/***
 * @Author 蜗牛
 */
public interface FrendService  {

    List<Frend> list();

    // 保存-- 写库
    void saveW(Frend frend);

    // 保存-- 读库
    void saveR(Frend frend);

    void save(Frend frend);

    // 读-- 写库
    void saveAll(Frend frend) throws Exception;

    void saveAllR(Frend frend);
}
