package com.woniu.db.check.configure;

import cn.hutool.extra.spring.SpringUtil;
import com.woniu.db.check.callback.DBCommunicationsCallBack;
import com.woniu.db.check.callback.support.DefaultCommunicationsCallBack;
import com.woniu.db.check.core.DbConnManger;
import com.woniu.db.check.core.service.DbCheckService;
import com.woniu.db.check.core.valid.ValidConnectionChecker;
import com.woniu.db.check.core.valid.support.ValidConnectionCheckerAdapter;
import com.woniu.db.check.event.listener.CommunicationsHealthEventListener;
import com.woniu.db.check.event.listener.CommunicationsUnHealthEventListener;
import com.woniu.db.check.properties.DbCheckProperies;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

import static com.woniu.db.check.properties.DbCheckProperies.PREFIX;


/**
 * @description: db检测自动装配
 **/
@Configuration
@Import(SpringUtil.class)
@EnableConfigurationProperties(DbCheckProperies.class)
@ConditionalOnClass(DataSource.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@ConditionalOnProperty(prefix = PREFIX,name = "enabled",havingValue = "true",matchIfMissing = true)
public class DbCheckConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DBCommunicationsCallBack dbCommunicationsCallBack(){
        return new DefaultCommunicationsCallBack();
    }



    @Bean
    @ConditionalOnMissingBean
    public CommunicationsUnHealthEventListener communicationsUnHealthEventListener(){
        return new CommunicationsUnHealthEventListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public CommunicationsHealthEventListener communicationsHealthEventListener(){
        return new CommunicationsHealthEventListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public ValidConnectionChecker validConnectionChecker(DbCheckProperies dbCheckProperies){
        return new ValidConnectionCheckerAdapter(dbCheckProperies);
    }

    @Bean
    @ConditionalOnMissingBean
    public DbConnManger dbConnManger(DataSourceProperties dataSourceProperties){
        return new DbConnManger(dataSourceProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public DbCheckService dbCheckService(DbCheckProperies dbCheckProperies, ValidConnectionChecker validConnectionChecker, DbConnManger dbConnManger){
        return new DbCheckService(validConnectionChecker,dbCheckProperies,dbConnManger);
    }
}
