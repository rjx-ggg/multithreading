package com.tuling.dynamic.datasource.service;

import com.tuling.dynamic.datasource.annotation.WR;
import com.tuling.dynamic.datasource.entity.Frend;

import java.util.List;


/***
 * @Author 程序员蜗牛
 */
public interface FrendService  {
    List<Frend> list();

    void save(Frend frend);

}
