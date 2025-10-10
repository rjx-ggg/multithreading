package com.woniu.scheduleTask.method;

import com.woniu.scheduleTask.domain.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: woniu
 * @description:
 **/
@Slf4j
@Component(value = "testSchedulingTask")
public class TestSchedulingTask {

    public void taskMethod(UserInfo obj){
        log.info(String.format("调用了当前定时任务:输出参数：参数1：%s,参数2：%s",obj.getUserName(),obj.getPassword()));
    }
}
