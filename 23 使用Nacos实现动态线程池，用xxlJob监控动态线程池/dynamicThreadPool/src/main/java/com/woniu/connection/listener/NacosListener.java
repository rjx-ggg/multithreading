package com.woniu.connection.listener;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.woniu.connection.core.ResizableCapacityLinkedBlockIngQueue;
import com.woniu.connection.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
/**
 * 使用Nacos实现动态线程池，用xxlJob监控动态线程池
 */
@Component
public class NacosListener implements ApplicationRunner {
    @Resource
    private ConfigService configService;
    public static final String DATA_ID = "dynamic-thread-pool-dev.properties";

    @Autowired
    @Qualifier("commonThreadPool")
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private SendMailService sendMailService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //添加nacos配置文件监听
        listenerNacosConfig();
    }

    /**
     * 监听数据源变化
     * @throws Exception 异常
     */
    private void listenerNacosConfig() throws Exception {
        configService.addListener(DATA_ID, "DEFAULT_GROUP", new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("-------改变之前---------");
                ResizableCapacityLinkedBlockIngQueue<Runnable> queue = (ResizableCapacityLinkedBlockIngQueue) threadPoolExecutor.getQueue();
                System.out.println("CorePoolSize=" + threadPoolExecutor.getCorePoolSize() + "   " +
                        "queueSize=" + queue.getCapacity() + "   " +
                        "MaximumPoolSize=" + threadPoolExecutor.getMaximumPoolSize());
                System.out.println("------改变之后--------");
                String[] split = configInfo.split("\n");
                int[] nums = new int[3];
                int index = 0;
                for (String s : split) {
                    nums[index++] = Integer.valueOf(s.split("=")[1]);
                }
                threadPoolExecutor.setCorePoolSize(nums[0]);
                threadPoolExecutor.setMaximumPoolSize(nums[1]);
                queue.setCapacity(nums[2]);
                System.out.println("CorePoolSize=" + threadPoolExecutor.getCorePoolSize() + "   " +
                        "queueSize=" + queue.getCapacity() + "   " +
                        "MaximumPoolSize=" + threadPoolExecutor.getMaximumPoolSize());
                System.out.println("---------------");
//                sendMailService.sendMailToWarn(threadPoolExecutor);
                System.out.println("线程池修改成功，发送邮件成功");
            }

            @Override
            public Executor getExecutor() {
                return null;
            }
        });
    }

    /**
     * 向nacos发布内容
     *
     * @param content 内容
     * @throws Exception 异常
     */
    private void publishConfig(String content) throws Exception {
        //发布内容
        configService.publishConfig(DATA_ID, "DEFAULT_GROUP", content);
    }
}