package com.woniu.scheduleTask.flownode.app;

import cn.hutool.core.util.RandomUtil;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Slf4j
@Component(value = "sendEmail")
public class SendEmail extends NodeComponent {

    @Override
    public void process() throws Exception {
        log.info("handle send email !");
    }
}
