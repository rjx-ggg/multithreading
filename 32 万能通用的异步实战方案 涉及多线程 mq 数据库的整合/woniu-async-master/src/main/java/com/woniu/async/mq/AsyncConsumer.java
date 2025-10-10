package com.woniu.async.mq;

import com.woniu.async.biz.AsyncBizService;
import com.woniu.async.dto.AsyncExecDto;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.woniu.async.constant.AsyncConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * 异步执行消费者
 */
@Slf4j
@Component
public class AsyncConsumer {

    @Autowired
    private AsyncBizService asyncBizService;

    /**
     * 队列名称前缀：默认是应用名称
     */
    @Value("${async.topic:${spring.application.name}}")
    private String asyncTopic;

    /**
     * 消费消息
     *
     * @param asyncExecDto
     * @return
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "${async.topic:${spring.application.name}}" + AsyncConstant.QUEUE_SUFFIX),
                    exchange = @Exchange(value = "async", type = "direct"),
                    key = "async"
            )
    }
    )
    public void onConsume(AsyncExecDto asyncExecDto) {
        String queueName = asyncTopic + AsyncConstant.QUEUE_SUFFIX;
        try {
            log.info("rabbitmq消息开始消费，queueName：'{}'，message：{}", queueName, asyncExecDto);
            // 执行方法
            asyncBizService.invoke(asyncExecDto);
            log.info("rabbitmq消息消费成功，queueName：'{}'，message：{}", queueName, asyncExecDto);
        } catch (Exception e) {
            log.error("rabbitmq消息消费失败，queueName：'{}'，message：{}", queueName, asyncExecDto, e);
            throw e;
        }
    }

}
