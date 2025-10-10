package com.wm.file.decorator;

import com.wm.file.util.util.UserUtils;
import org.springframework.core.task.TaskDecorator;

/**
 * @author 公众号：程序员蜗牛g
 * @description 上下文装饰器
 */

public class CustomTaskDecorator implements TaskDecorator {

    @Override

    public Runnable decorate(Runnable runnable) {

        String robotId = UserUtils.getUserId();

        System.out.println(robotId);

        return () -> {
            try {
                // 将主线程的请求信息，设置到子线程中
                UserUtils.setUserId(robotId);
                // 执行子线程，这一步不要忘了
                runnable.run();
            } finally {
                // 线程结束，清空这些信息，否则可能造成内存泄漏
                UserUtils.clear();
            }
        };

    }

}