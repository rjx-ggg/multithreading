package com.multi.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.multi.dao.TenantInfoMapper;
import com.multi.entity.DataSourceEntity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;

/**
 * @Description 动态数据源初始化
 */
@Slf4j
@Configuration
public class DynamicDataSourceInit {

    @Autowired
    private DefaultDataSourceCreator dataSourceCreator;

    @Autowired
    private TenantInfoMapper tenantInfoMapper;

    @Autowired
    private DataSource dataSource;

    @Bean
    public void initDataSource() {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        List<DataSourceEntity> datasources = tenantInfoMapper.selectList(null);
        for (DataSourceEntity entity : datasources) {
            DataSourceProperty dsProperties = new DataSourceProperty();
            dsProperties.setPoolName(entity.getDsCode());
            dsProperties.setUrl(entity.getJdbcUrl());
            dsProperties.setUsername(entity.getUsername());
            dsProperties.setPassword(entity.getPassword());
            DataSource dataSource = dataSourceCreator.createDataSource(dsProperties);
            ds.addDataSource(dsProperties.getPoolName(), dataSource);
        }
        log.info("{}", ds.getDataSources().keySet());
    }
}
