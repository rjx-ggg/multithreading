package com.woniu.dynamic.datasource.service;

import com.woniu.dynamic.datasource.entity.Frend;

import java.util.List;

/***
 * @Author 蜗牛
 */
public interface FrendService  {
    List<Frend> list();

    void save(Frend frend);

}
