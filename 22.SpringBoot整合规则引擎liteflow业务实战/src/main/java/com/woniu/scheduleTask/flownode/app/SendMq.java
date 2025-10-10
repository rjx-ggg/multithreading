package com.woniu.scheduleTask.flownode.app;

import cn.hutool.core.util.RandomUtil;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 发送mq 业务
 */
@Slf4j
@Component(value = "sendMq")
public class SendMq extends NodeComponent {
    @Override
    public void process() throws Exception {
        log.info("handle send mq !");

    }
}
