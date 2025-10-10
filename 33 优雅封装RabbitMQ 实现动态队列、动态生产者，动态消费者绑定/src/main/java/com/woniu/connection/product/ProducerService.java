package com.woniu.connection.product;

/**
 * 生产者接口
 */
public interface ProducerService {

 /**
  * 发送消息
  * @param message
  */
 void send(Object message);

}
