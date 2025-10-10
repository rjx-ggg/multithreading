package com.yomahub.tlog.example.feign.domain;

import com.yomahub.tlog.core.thread.TLogInheritableTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsynPoolDomain extends TLogInheritableTask {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void runTask() {
        log.info("这是线程池中的异步方法哦");
        log.info("线程池中的异步方法开始");
        log.info("线程池中的异步方法结束");
    }
}
