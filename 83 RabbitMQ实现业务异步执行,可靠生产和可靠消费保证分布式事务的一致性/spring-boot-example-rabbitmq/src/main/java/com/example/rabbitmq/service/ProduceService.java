package com.example.rabbitmq.service;

import com.example.rabbitmq.common.MessageHelper;
import com.example.rabbitmq.entity.BussinessEntity;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 * RabbitConfig
 * </p>
 * @author 程序员蜗牛
 */
@Service
public class ProduceService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MsgLogService msgLogService;

    /**
     * 发送消息
     */
    public boolean sendByAuto(BussinessEntity bussinessEntity) {
        String msgId = UUID.randomUUID().toString().replaceAll("-", "");
        bussinessEntity.setMsgId(msgId);
        // 1.存储要消费的数据
        msgLogService.save("bussiness.exchange",
                "bussiness.route",
                "bussiness.queue", msgId, bussinessEntity);

        // 2.发送消息到mq服务器中（附带消息ID）
        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend("bussiness.exchange", "bussiness.route",
                MessageHelper.objToMsg(bussinessEntity), correlationData);
        return true;
    }
}
