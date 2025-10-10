package com.multi.controller;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.multi.entity.TUser;

import com.multi.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;
import java.util.Set;

/**
 * @author woniu
 * @since 2024-02-23
 */
@Slf4j
@RestController
public class TenantInfoController {

    @Autowired
    private UserService userService;

    /**
     * 实战！如何实现多租户数据隔离？
     * @param dsCode
     * @return
     */
    @GetMapping("/{ds_code}/user")
    // Get http://localhost:8080/qwejlkjsdf324werde4535/user
    // Get http://localhost:8080/a4233ewtfewrtweerwe234/user
    public List<TUser> getUsers(@PathVariable("ds_code") String dsCode) {
        try {
            DynamicDataSourceContextHolder.push(dsCode);
            return userService.findAll();
        } finally {
            DynamicDataSourceContextHolder.clear();
        }
    }

    @Autowired
    private DefaultDataSourceCreator dataSourceCreator;

    @Autowired
    private DataSource ds;

    @GetMapping("/add")
    public Set<String> saveInfo(String dsCode, String jdbcUrl, String username, String password) {
        DynamicRoutingDataSource dataSource = (DynamicRoutingDataSource) ds;
        DataSourceProperty dsProperties = new DataSourceProperty();
        dsProperties.setPoolName(dsCode);
        dsProperties.setUrl(jdbcUrl);
        dsProperties.setUsername(username);
        dsProperties.setPassword(password);
        DataSource newDs = dataSourceCreator.createDataSource(dsProperties);
        dataSource.addDataSource(dsProperties.getPoolName(), newDs);
        return dataSource.getDataSources().keySet();
    }
}
