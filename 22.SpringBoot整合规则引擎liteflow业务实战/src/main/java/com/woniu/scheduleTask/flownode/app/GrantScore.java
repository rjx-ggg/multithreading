package com.woniu.scheduleTask.flownode.app;



import com.woniu.scheduleTask.flownode.AppFlowContext;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @Description 发送积分
 * @Date 2024-01-31 2:28 PM
 */
@Slf4j
@Component(value = "grantScore")
public class GrantScore extends NodeComponent {

    @Override
    public void process() throws Exception {
        // 拿到上下文对象 做一些操作
        AppFlowContext context = this.getContextBean(AppFlowContext.class);
        log.info("get context valus : {}",context);
        log.info("handle grant score !");
    }

    // 是否处理该节点
    @Override
    public boolean isAccess() {
        return Boolean.TRUE;
    }


}
