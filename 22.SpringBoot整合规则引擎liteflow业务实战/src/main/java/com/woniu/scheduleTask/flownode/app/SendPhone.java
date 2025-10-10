package com.woniu.scheduleTask.flownode.app;

import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component(value = "sendPhone")
public class SendPhone extends NodeComponent {
    @Override
    public void process() throws Exception {
        log.info("handle send phone !");

    }
}
