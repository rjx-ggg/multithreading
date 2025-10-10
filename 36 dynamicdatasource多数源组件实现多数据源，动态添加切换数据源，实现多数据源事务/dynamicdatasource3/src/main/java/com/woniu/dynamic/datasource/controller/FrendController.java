package com.woniu.dynamic.datasource.controller;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.woniu.dynamic.datasource.entity.Frend;
import com.woniu.dynamic.datasource.service.FrendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;

/***
 * dynamic-datasource多数源组件
 * 实现多数据源，动态添加切换数据源，实现多数据源事务
 * @Author 蜗牛
 */
@RestController
@RequestMapping("frend")
@Slf4j
public class FrendController {

    @Autowired
    private FrendService frendService;

    @GetMapping(value = "select")
    public List<Frend> select(){
        return frendService.list();
    }

    @GetMapping(value = "insert")
    public void in(){
        Frend frend = new Frend();
        frend.setName("程序员蜗牛");
        frendService.save(frend);
    }

    @Autowired
    private DataSource dataSource;

    @DeleteMapping("ds/del")
    public String remove(String name) {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        ds.removeDataSource(name);
//        ds.addDataSource();
        return "删除成功";
    }



}
