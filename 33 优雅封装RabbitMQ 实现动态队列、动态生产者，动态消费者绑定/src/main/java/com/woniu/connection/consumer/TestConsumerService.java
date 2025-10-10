package com.woniu.connection.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component("testConsumerService")
public class TestConsumerService extends AbsConsumerService {

    @Override
    public void onConsumer(String data, Message message, Channel channel) throws IOException {
        log.info("data is {}", data);
        nack(message, channel);
    }
}
