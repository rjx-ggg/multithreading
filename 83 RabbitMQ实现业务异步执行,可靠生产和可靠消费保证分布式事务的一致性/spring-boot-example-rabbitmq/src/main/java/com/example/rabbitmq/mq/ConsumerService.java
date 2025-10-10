package com.example.rabbitmq.mq;

import com.example.rabbitmq.common.Constant;
import com.example.rabbitmq.common.MessageHelper;
import com.example.rabbitmq.entity.BussinessEntity;
import com.example.rabbitmq.entity.MsgLog;
import com.example.rabbitmq.service.MsgLogService;
import com.example.rabbitmq.service.BussinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * <p>
 * SpringBoot整合RabbitMQ实现业务异步执行
 * 可靠生产和可靠消费保证分布式事务的一致性
 * </p>
 *
 * @author 程序员蜗牛
 */
@Component
public class ConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);

    @Autowired
    private BussinessService bussinessService;

    @Autowired
    private MsgLogService msgLogService;


    /**
     * 监听消息队列，自动确认模式，无需调用ack或者nack方法，当程序执行时才删除消息
     * 配置参数：spring.rabbitmq.listener.simple.acknowledge-mode=auto
     *
     * @param message
     */
    @RabbitListener(queues = {"bussiness.queue"})
    public void consumeFromAuto(Message message) {
        LOGGER.info("收到消息：{}", message.toString());
        // 获取消息ID
        BussinessEntity entity = MessageHelper.msgToObj(message, BussinessEntity.class);
        // 消息幂等性处理，如果已经处理成功，无需重复消费
        MsgLog queryObj = msgLogService.selectByMsgId(entity.getMsgId());
        if (Objects.nonNull(queryObj) && Constant.SUCCESS.equals(queryObj.getStatus())) {
            return;
        }
        // 执行业务逻辑
        boolean success = bussinessService.doService();
        if (success) {
            msgLogService.updateStatus(entity.getMsgId(), Constant.SUCCESS, "业务执行成功");
        } else {
            msgLogService.updateStatus(entity.getMsgId(), Constant.FAIL, "业务执行失败");
        }
    }
}
