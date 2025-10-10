package com.woniu.rabbitmq;

import com.woniu.pojo.GoodsVo;
import com.woniu.pojo.SeckillOrder;
import com.woniu.pojo.User;
import com.woniu.service.GoodsService;
import com.woniu.service.OrderService;
import com.woniu.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.woniu.redis.RedisService;

/**
 * @program: SeckillProject
 * @description: 消息队列：接收
 **/
@Service
public class MQReceiver {
    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SeckillService seckillService;

    /**
     * 秒杀业务消息接收
     * @param message
     */
    @RabbitListener(queues=MQConfig.SECKILL_QUEUE)
    public void receive(String message) {
        log.info("receive message:"+message);
        SeckillMessage sm  = redisService.stringToBean(message, SeckillMessage.class);
        User user = sm.getUser();
        long goodsId = sm.getGoodsId();

        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        SeckillOrder order = orderService.getOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        seckillService.seckill(user, goods);
    }

    /**
     * 简单字符串接收测试
     * @param message
     */
    @RabbitListener(queues=MQConfig.QUEUE)
    public void receiveStr(String message) {
        log.info("receive message:"+message);
    }

    /**
     * Topic模式 交换机Exchange  queue1
     */
    @RabbitListener(queues=MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info(" topic  queue1 message:"+message);
    }

    /**
     * Topic模式 交换机Exchange  queue1
     */
    @RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info(" topic  queue2 message:"+message);
    }
}
