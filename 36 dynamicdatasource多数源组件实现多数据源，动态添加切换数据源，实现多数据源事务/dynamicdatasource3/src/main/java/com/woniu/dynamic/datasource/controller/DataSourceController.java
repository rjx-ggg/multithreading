package com.woniu.dynamic.datasource.controller;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.Set;

/***
 * @Author 蜗牛
 */
@RestController
@RequestMapping("/datasources")
public class DataSourceController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/getdatasources")
    public Set<String> now() {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        return ds.getDataSources().keySet();
    }


    @PostMapping("/add")
    public Set<String> add() {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        ds.addDataSource("dynamic", dataSource);
        return ds.getDataSources().keySet();
    }


}
