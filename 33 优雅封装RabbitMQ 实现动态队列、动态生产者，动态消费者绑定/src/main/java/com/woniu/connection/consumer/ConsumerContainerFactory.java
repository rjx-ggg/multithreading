package com.woniu.connection.consumer;

import com.woniu.connection.retry.CustomRetryListener;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Objects;

/**
 * MQ具体消息监听器工厂
 */
@Data
@Slf4j
@Builder
public class ConsumerContainerFactory implements FactoryBean<SimpleMessageListenerContainer> {

    /**
     * MQ连接工厂
     */
    private ConnectionFactory connectionFactory;

    /**
     * 操作MQ管理器
     */
    private AmqpAdmin amqpAdmin;

    /**
     * 队列
     */
    private Queue queue;

    /**
     * 交换机
     */
    private Exchange exchange;

    /**
     * 消费者
     */
    private ConsumerService consumer;

    /**
     * 重试回调
     */
    private CustomRetryListener retryListener;

    /**
     * 最大重试次数
     */
    private final Integer maxAttempts = 5;

    /**
     * 是否自动确认
     */
    private Boolean autoAck;

    @Override
    public SimpleMessageListenerContainer getObject() throws Exception {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setAmqpAdmin(amqpAdmin);
        container.setConnectionFactory(connectionFactory);
        container.setQueues(queue);
        container.setPrefetchCount(20);
        container.setConcurrentConsumers(20);
        container.setMaxConcurrentConsumers(100);
        container.setDefaultRequeueRejected(Boolean.FALSE);
        container.setAdviceChain(createRetry());
        container.setAcknowledgeMode(autoAck ? AcknowledgeMode.AUTO : AcknowledgeMode.MANUAL);
        if (Objects.nonNull(consumer)) {
            container.setMessageListener(consumer);
        }
        return container;
    }

    /**
     * 配置重试
     *
     * @return
     */
    private Advice createRetry() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.registerListener(new RetryListener() {
            @Override
            public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
                // 第一次重试调用
                return true;
            }

            @Override
            public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {

            }

            @Override
            public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                if (Objects.nonNull(retryListener)) {
                    retryListener.onRetry(context, callback, throwable);
                    if (maxAttempts.equals(context.getRetryCount())) {
                        retryListener.lastRetry(context, callback, throwable);
                    }
                }
            }
        });
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(maxAttempts));
        retryTemplate.setBackOffPolicy(genExponentialBackOffPolicy());
        return RetryInterceptorBuilder.stateless().retryOperations(retryTemplate).recoverer(new RejectAndDontRequeueRecoverer()).build();
    }

    /**
     * 设置过期时间
     *
     * @return
     */
    private BackOffPolicy genExponentialBackOffPolicy() {
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        // 重试间隔基数(秒)
        backOffPolicy.setInitialInterval(5000);
        // 从重试的第一次至最后一次，最大时间间隔(秒)
        backOffPolicy.setMaxInterval(86400000);
        // 重试指数
        backOffPolicy.setMultiplier(1);
        return backOffPolicy;
    }

    @Override
    public Class<?> getObjectType() {
        return SimpleMessageListenerContainer.class;
    }
}
