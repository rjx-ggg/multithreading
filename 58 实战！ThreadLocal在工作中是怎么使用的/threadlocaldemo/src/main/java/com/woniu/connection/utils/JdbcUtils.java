package com.woniu.connection.utils;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class JdbcUtils {

    static ThreadLocal<Connection> tl = new ThreadLocal<>();

    @Autowired
    private DruidDataSource dataSource;

    // 获取连接
    /*
    * 原本: 直接从连接池中获取连接
    * 现在:
    *       1. 直接获取当前线程绑定的连接对象
    *       2. 如果连接对象是空的
    *           2.1 再去连接池中获取连接
    *           2.2 将此连接对象跟当前线程进行绑定
    * */
    public  Connection getConnection() throws SQLException {
        Connection conn = tl.get();
        if(conn == null){
            conn = dataSource.getConnection();
            tl.set(conn);
        }
        return conn;
    }
    //释放资源
    public  void release(AutoCloseable... ios){
        for (AutoCloseable io : ios) {
            if(io != null){
                try {
                    io.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public  void commitAndClose(Connection conn) {
        try {
            if(conn != null){
                //提交事务
                conn.commit();
                //解绑当前线程绑定的连接对象
                tl.remove();
                //释放连接
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void rollbackAndClose(Connection conn) {
        try {
            if(conn != null){
                //回滚事务
                conn.rollback();
                //解绑当前线程绑定的连接对象
                tl.remove();
                //释放连接
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
