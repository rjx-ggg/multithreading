package com.woniu.dynamic.datasource.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.woniu.dynamic.datasource.mapper.FrendMapper;
import com.woniu.dynamic.datasource.entity.Frend;
import com.woniu.dynamic.datasource.service.FrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @Author 蜗牛
 */
@Service
public class FrendImplService implements FrendService {

    @Autowired
    FrendMapper frendMapper;

    @Override
    @DS("slave")  // 从库， 如果按照下划线命名方式配置多个  ， 可以指定前缀即可（组名）
    public List<Frend> list() {
        return frendMapper.list();
    }

    @Override
    @DS("master")
    public void save(Frend frend) {
        frendMapper.save(frend);
    }


    @DS("master")
    @DSTransactional
    public void saveAll() {
        // 执行多数据源的操作
    }


}
