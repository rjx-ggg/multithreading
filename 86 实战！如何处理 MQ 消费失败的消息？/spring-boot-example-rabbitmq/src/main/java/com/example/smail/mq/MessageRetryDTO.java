package com.example.smail.mq;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 实战！如何处理 MQ 消费失败的消息？
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MessageRetryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 原始消息body
     */
    private String bodyMsg;

    /**
     * 消息来源ID
     */
    private String sourceId;

    /**
     * 消息来源描述
     */
    private String sourceDesc;

    /**
     * 交换机
     */
    private String exchangeName;

    /**
     * 路由键
     */
    private String routingKey;

    /**
     * 队列
     */
    private String queueName;

    /**
     * 状态,1:初始化，2：成功，3：失败
     */
    private Integer status = 1;

    /**
     * 最大重试次数
     */
    private Integer maxTryCount = 3;

    /**
     * 当前重试次数
     */
    private Integer currentRetryCount = 0;

    /**
     * 重试时间间隔（毫秒）
     */
    private Long retryIntervalTime = 0L;

    /**
     * 任务失败信息
     */
    private String errorMsg;

    /**
     * 创建时间
     */
    private Date createTime;

    @Override
    public String toString() {
        return "MessageRetryDTO{" +
                "bodyMsg='" + bodyMsg + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", sourceDesc='" + sourceDesc + '\'' +
                ", exchangeName='" + exchangeName + '\'' +
                ", routingKey='" + routingKey + '\'' +
                ", queueName='" + queueName + '\'' +
                ", status=" + status +
                ", maxTryCount=" + maxTryCount +
                ", currentRetryCount=" + currentRetryCount +
                ", retryIntervalTime=" + retryIntervalTime +
                ", errorMsg='" + errorMsg + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    /**
     * 检查重试次数是否超过最大值
     * @return
     */
    public boolean checkRetryCount() {
        retryCountCalculate();
        //检查重试次数是否超过最大值
        if (this.currentRetryCount < this.maxTryCount) {
            return true;
        }
        return false;
    }

    /**
     * 重新计算重试次数
     */
    private void retryCountCalculate() {
        this.currentRetryCount = this.currentRetryCount + 1;
    }

}