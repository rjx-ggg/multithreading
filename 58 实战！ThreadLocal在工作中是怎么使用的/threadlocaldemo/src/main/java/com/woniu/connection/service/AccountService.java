package com.woniu.connection.service;


import com.woniu.connection.dao.AccountDao;
import com.woniu.connection.utils.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
/*
 *
 *   事务的使用注意点:
 *       1. service层和dao层的连接对象保持一致
 *       2. 每个线程的connection对象必须前后一致, 线程隔离
 *
 *   常规的解决方案
 *       1. 传参 : 将service层的connection对象直接传递到dao层
 *       2. 加锁
 *
 *   常规解决方案的弊端:
 *       1. 提高代码耦合度
 *       2. 降低程序性能
 * */

@Service
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private JdbcUtils jdbcUtils;

    //转账
    public boolean transfer(String outUser, String inUser, int money) {

        Connection conn = null;
        try {
            //1. 开启事务
            conn = jdbcUtils.getConnection();
            conn.setAutoCommit(false);

            // 转出
            accountDao.out(outUser, money);
            //算术异常: 模拟转出成功,转入失败
            int i = 1 / 0;
            // 转入
            accountDao.in(inUser, money);

            //2. 成功提交
            jdbcUtils.commitAndClose(conn);

        } catch (Exception e) {
            e.printStackTrace();
            //2. 或者失败回滚
            jdbcUtils.rollbackAndClose(conn);
            return false;
        }

        return true;
    }

}
