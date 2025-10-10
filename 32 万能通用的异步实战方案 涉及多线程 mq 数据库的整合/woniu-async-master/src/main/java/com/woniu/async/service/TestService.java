package com.woniu.async.service;

import com.woniu.async.enums.AsyncTypeEnum;
import com.woniu.async.annotation.AsyncExec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class TestService {


    /**
     * 执行策略
     * @param id
     * @return
     */
    @AsyncExec(type = AsyncTypeEnum.SAVE_ASYNC, remark = "数据字典")
    public void exec(Long id) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            log.error("打断了");
        }
    }


}
