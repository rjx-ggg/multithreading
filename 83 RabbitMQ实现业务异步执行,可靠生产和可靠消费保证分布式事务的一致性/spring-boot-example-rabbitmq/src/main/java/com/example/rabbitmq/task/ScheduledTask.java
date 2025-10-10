package com.example.rabbitmq.task;

import com.example.rabbitmq.common.Constant;
import com.example.rabbitmq.common.MessageHelper;
import com.example.rabbitmq.entity.MsgLog;
import com.example.rabbitmq.service.MsgLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTask.class);

    /**
     * 最大投递次数
     */
    private static final int MAX_TRY_COUNT = 3;

    @Autowired
    private MsgLogService msgLogService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 每30s拉取消费失败的消息, 重新投递
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void retry() {
        LOGGER.info("开始执行重新投递消费失败的消息！");
        // 查询需要重新投递的消息
        List<MsgLog> msgLogs = msgLogService.selectFailMsg();
        for (MsgLog msgLog : msgLogs) {
            if (msgLog.getTryCount() >= MAX_TRY_COUNT) {
                msgLogService.updateStatus(msgLog.getMsgId(), Constant.RETRY_FAIL, msgLog.getResult());
                LOGGER.info("超过最大重试次数, msgId: {}", msgLog.getMsgId());
                break;
            }

            // 重新投递消息
            CorrelationData correlationData = new CorrelationData(msgLog.getMsgId());
            rabbitTemplate.convertAndSend("", msgLog.getQueueName(), MessageHelper.objToMsg(msgLog.getMsg()), correlationData);
            // 更新下次重试时间
            msgLogService.updateNextTryTime(msgLog.getMsgId(), msgLog.getTryCount());
        }
    }
}

