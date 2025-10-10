package com.example.smail.mq;


import com.example.smail.common.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public abstract class CommonMessageRetryService {

    private static final Logger log = LoggerFactory.getLogger(CommonMessageRetryService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 初始化消息
     *
     * @param message
     */
    public void initMessage(Message message) {
        log.info("{} 收到消息: {}，业务数据：{}", this.getClass().getName(), message.toString(), new String(message.getBody()));
        try {
            //封装消息
            MessageRetryDTO messageRetryDto = buildMessageRetryInfo(message);
            if (log.isInfoEnabled()) {
                log.info("反序列化消息:{}", messageRetryDto.toString());
            }
            prepareAction(messageRetryDto);
        } catch (Exception e) {
            log.warn("处理消息异常，错误信息：", e);
        }
    }

    /**
     * 准备执行
     *
     * @param retryDto
     */
    protected void prepareAction(MessageRetryDTO retryDto) {
        try {
            execute(retryDto);
            doSuccessCallBack(retryDto);
        } catch (Exception e) {
            log.error("当前任务执行异常，业务数据：" + retryDto.toString(), e);
            //执行失败，计算是否还需要继续重试
            if (retryDto.checkRetryCount()) {
                if (log.isInfoEnabled()) {
                    log.info("重试消息:{}", retryDto.toString());
                }
                retrySend(retryDto);
            } else {
                if (log.isWarnEnabled()) {
                    log.warn("当前任务重试次数已经到达最大次数，业务数据：" + retryDto.toString(), e);
                }
                doFailCallBack(retryDto.setErrorMsg(e.getMessage()));
            }
        }
    }

    /**
     * 任务执行成功，回调服务(根据需要进行重写)
     *
     * @param messageRetryDto
     */
    private void doSuccessCallBack(MessageRetryDTO messageRetryDto) {
        try {
            successCallback(messageRetryDto);
        } catch (Exception e) {
            log.warn("执行成功回调异常，队列描述：{}，错误原因：{}", messageRetryDto.getSourceDesc(), e.getMessage());
        }
    }

    /**
     * 任务执行失败，回调服务(根据需要进行重写)
     *
     * @param messageRetryDto
     */
    private void doFailCallBack(MessageRetryDTO messageRetryDto) {
        try {
            saveMessageRetryInfo(messageRetryDto.setErrorMsg(messageRetryDto.getErrorMsg()));
            failCallback(messageRetryDto);
        } catch (Exception e) {
            log.warn("执行失败回调异常，队列描述：{}，错误原因：{}", messageRetryDto.getSourceDesc(), e.getMessage());
        }
    }

    /**
     * 执行任务
     *
     * @param messageRetryDto
     */
    protected abstract void execute(MessageRetryDTO messageRetryDto);

    /**
     * 成功回调
     *
     * @param messageRetryDto
     */
    protected abstract void successCallback(MessageRetryDTO messageRetryDto);

    /**
     * 失败回调
     *
     * @param messageRetryDto
     */
    protected abstract void failCallback(MessageRetryDTO messageRetryDto);

    /**
     * 构建消息补偿实体
     *
     * @param message
     * @return
     */
    private MessageRetryDTO buildMessageRetryInfo(Message message) {
        //如果头部包含补偿消息实体，直接返回
        Map<String, Object> messageHeaders = message.getMessageProperties().getHeaders();
        if (messageHeaders.containsKey("message_retry_info")) {
            Object retryMsg = messageHeaders.get("message_retry_info");
            if (Objects.nonNull(retryMsg)) {
                return JsonUtil.strToObj(String.valueOf(retryMsg),MessageRetryDTO.class);
            }
        }
        //自动将业务消息加入补偿实体
        MessageRetryDTO messageRetryDto = new MessageRetryDTO();
        messageRetryDto.setBodyMsg(new String(message.getBody(), StandardCharsets.UTF_8));
        messageRetryDto.setExchangeName(message.getMessageProperties().getReceivedExchange());
        messageRetryDto.setRoutingKey(message.getMessageProperties().getReceivedRoutingKey());
        messageRetryDto.setQueueName(message.getMessageProperties().getConsumerQueue());
        messageRetryDto.setCreateTime(new Date());
        return messageRetryDto;
    }

    /**
     * 异常消息重新入库
     *
     * @param retryDto
     */
    private void retrySend(MessageRetryDTO retryDto) {
        //将补偿消息实体放入头部，原始消息内容保持不变
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setHeader("message_retry_info", JsonUtil.objToStr(retryDto));
        Message message = new Message(retryDto.getBodyMsg().getBytes(), messageProperties);
        rabbitTemplate.convertAndSend(retryDto.getExchangeName(), retryDto.getRoutingKey(), message);
    }


    /**
     * 将异常消息存储到数据库中
     *
     * @param retryDto
     */
    private void saveMessageRetryInfo(MessageRetryDTO retryDto) {
        try {
       // 存到数据库
        } catch (Exception e) {
            log.error("将异常消息存储到mongodb失败，消息数据：" + retryDto.toString(), e);
        }
    }
}