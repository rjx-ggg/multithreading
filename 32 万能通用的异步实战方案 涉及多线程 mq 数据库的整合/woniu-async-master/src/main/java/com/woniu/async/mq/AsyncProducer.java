package com.woniu.async.mq;

import com.woniu.async.dto.AsyncExecDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.woniu.async.constant.AsyncConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * 异步执行提供者
 */
@Slf4j
@Component
public class AsyncProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 队列名称前缀：默认是应用名称
     */
    @Value("${async.topic:${spring.application.name}}")
    private String asyncTopic;

    /**
     * 发送消息
     *
     * @param asyncExecDto
     * @return
     */
    public boolean send(AsyncExecDto asyncExecDto) {
        String queueName = asyncTopic + AsyncConstant.QUEUE_SUFFIX;
        try {
            log.info("rabbitmq消息开始发送，queueName：'{}', message：{}", queueName, asyncExecDto);
            rabbitTemplate.convertAndSend( "async", "async",asyncExecDto);
            log.info("rabbitmq消息发送成功，queueName：'{}', message：{}", queueName, asyncExecDto);
            return true;
        } catch (Exception e) {
            log.error("rabbitmq消息发送失败，queueName：'{}', message：{}", queueName, asyncExecDto, e);
            return false;
        }
    }

}
