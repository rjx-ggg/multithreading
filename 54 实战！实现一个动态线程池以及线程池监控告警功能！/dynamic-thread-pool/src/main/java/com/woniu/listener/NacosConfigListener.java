package com.woniu.listener;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.woniu.dynamic.DynamicThreadPoolExecutorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import java.util.concurrent.Executor;

@Component
public class NacosConfigListener implements ApplicationRunner {

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Autowired
    private DynamicThreadPoolExecutorManager dynamicThreadPoolExecutorManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //开始监听nacos的配置更新
        String dataId = nacosConfigManager.getNacosConfigProperties().getName();
        String group = nacosConfigManager.getNacosConfigProperties().getGroup();
        nacosConfigManager.getConfigService().addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            //更新后的配置信息在此方法中接收，configInfo变量就是更新后的配置信息
            @Override
            public void receiveConfigInfo(String configInfo) {
                //刷新我们的线程池配置类
                Properties properties = new Properties();
                try {
                    properties.load(new StringReader(configInfo));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dynamicThreadPoolExecutorManager.refreshThreadPoolExecutor(properties);
            }
        });
    }
}
