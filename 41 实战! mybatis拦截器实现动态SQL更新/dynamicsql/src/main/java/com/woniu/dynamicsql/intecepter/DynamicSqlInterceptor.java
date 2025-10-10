package com.woniu.dynamicsql.intecepter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.woniu.dynamicsql.anno.DynamicSql;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;

@Component
@Slf4j
@Intercepts({
        @Signature(type = StatementHandler.class,
                method = "prepare", args = {Connection.class, Integer.class})
})
public class DynamicSqlInterceptor implements Interceptor {

    @Value("${dynamicSql.placeholder}")
    private String placeholder;

    @Value("${dynamicSql.date}")
    private String dynamicDate;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 1. 获取 StatementHandler 对象也就是执行语句
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        // 2. MetaObject 是 MyBatis 提供的一个反射帮助类，可以优雅访问对象的属性，这里是对 statementHandler 对象进行反射处理，
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
                new DefaultReflectorFactory());
        // 3. 通过 metaObject 反射获取 statementHandler 对象的成员变量 mappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        // mappedStatement 对象的 id 方法返回执行的 mapper 方法的全路径名，如com.woniu.dynamicsql.mapper.count()
        String id = mappedStatement.getId();
        // 4. 通过 id 获取到 Dao 层类的全限定名称，然后反射获取 Class 对象
        Class<?> classType = Class.forName(id.substring(0, id.lastIndexOf(".")));
        // 5. 获取包含原始 sql 语句的 BoundSql 对象
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        log.info("替换前---sql：{}", sql);
        // 拦截方法
        String mSql = null;
        // 6. 遍历 Dao 层类的方法
        for (Method method : classType.getMethods()) {
            // 7. 判断方法上是否有 DynamicSql 注解，有的话，就认为需要进行 sql 替换
            if (method.isAnnotationPresent(DynamicSql.class)) {
                mSql = sql.replaceAll(placeholder, String.format("'%s'", dynamicDate));
                break;
            }
        }
        if (StringUtils.isNotBlank(mSql)) {
            log.info("替换后---mSql：{}", mSql);
            // 8. 对 BoundSql 对象通过反射修改 SQL 语句。
            Field field = boundSql.getClass().getDeclaredField("sql");
            field.setAccessible(true);
            field.set(boundSql, mSql);
        }
        // 9. 执行修改后的 SQL 语句。
        return invocation.proceed();
    }

}

