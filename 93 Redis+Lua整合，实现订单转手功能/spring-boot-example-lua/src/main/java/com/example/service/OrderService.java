package com.example.service;


import com.example.entity.Order;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final StringRedisTemplate redisTemplate;
    private final DefaultRedisScript<Long> transferOrderScript;

    @Autowired
    public OrderService(OrderRepository orderRepository, StringRedisTemplate redisTemplate, DefaultRedisScript<Long> transferOrderScript) {
        this.orderRepository = orderRepository;
        this.redisTemplate = redisTemplate;
        this.transferOrderScript = transferOrderScript;
    }


    /**
     * 转移订单的方法
     * @param orderId      订单ID
     * @param currentOwner 当前拥有者ID
     * @param newOwner     新拥有者ID
     * @return 成功返回1，失败返回0
     */
    public Long transferOrder(Long orderId, Long currentOwner, Long newOwner) {
        // Lua脚本参数列表
        List<String> keys = Collections.singletonList("order:" + orderId.toString());
        List<String> values = Arrays.asList(currentOwner.toString(), newOwner.toString());
        // 执行Lua脚本
        Long result = redisTemplate.execute(transferOrderScript, keys, values.toArray());
        if (result == 1 ) {
            // 更新数据库中的订单所有者
            Order order = orderRepository.findById(orderId);
            order.setOwnerId(newOwner);
            orderRepository.save(order);
        }
        return result;
    }

}