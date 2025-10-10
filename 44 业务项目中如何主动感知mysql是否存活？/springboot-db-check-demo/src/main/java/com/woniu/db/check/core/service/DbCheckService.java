package com.woniu.db.check.core.service;


import com.woniu.db.check.context.MySQLCommunicationsHolder;
import com.woniu.db.check.core.DbConnManger;
import com.woniu.db.check.core.valid.ValidConnectionChecker;
import com.woniu.db.check.event.CommunicationsHealthEvent;
import com.woniu.db.check.event.CommunicationsUnHealthEvent;
import com.woniu.db.check.properties.DbCheckProperies;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 业务项目中如何主动感知mysql是否存活？
 */
@Slf4j
@RequiredArgsConstructor
public class DbCheckService implements InitializingBean, ApplicationContextAware {

    private final ValidConnectionChecker validConnectionChecker;

    private final DbCheckProperies dbCheckProperies;

    private final DbConnManger dbConnManger;

    private ApplicationContext applicationContext;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
        private AtomicInteger atomicInteger = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "dbcheck-pool-" + atomicInteger.getAndIncrement());
            thread.setDaemon(true);
            return thread;
        }
    });

    @Override
    public void afterPropertiesSet() throws Exception {
        executorService.scheduleWithFixedDelay(new DbCheckTask(), 0, dbCheckProperies.getTimeBetweenEvictionRunsMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private class DbCheckTask implements Runnable {
        @Override
        public void run() {
            SQLException sqlException = null;
            Connection conn = dbConnManger.getConn();
            try {
                boolean validConnection = validConnectionChecker.isValidConnection(conn, dbCheckProperies.getValidationQuery(), dbCheckProperies.getValidationQueryTimeout());
                if (validConnection) {
                    boolean b = MySQLCommunicationsHolder.isMySQLCommunicationsException.compareAndSet(true, false);
                    if (b) {
                        CommunicationsHealthEvent event = CommunicationsHealthEvent.builder().conn(conn).build();
                        applicationContext.publishEvent(event);
                    }
                } else {
                    sqlException = new SQLException("connection is invalid", "10040");
                }
            } catch (SQLException e) {
                log.error("{}", e);
                sqlException = e;
                dbConnManger.closeConnection();
                conn = null;
            }

            if (sqlException != null) {
                MySQLCommunicationsHolder.isMySQLCommunicationsException.compareAndSet(false, true);
                CommunicationsUnHealthEvent event = CommunicationsUnHealthEvent.builder().sqlException(sqlException).build();
                applicationContext.publishEvent(event);
            }


        }
    }
}
