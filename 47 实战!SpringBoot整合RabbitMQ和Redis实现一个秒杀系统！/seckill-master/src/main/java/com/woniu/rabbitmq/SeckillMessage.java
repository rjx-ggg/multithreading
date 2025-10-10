package com.woniu.rabbitmq;

import com.woniu.pojo.User;
import lombok.Getter;
import lombok.Setter;

/**
 * @program: SeckillProject
 * @description: 秒杀消息封装
 **/
@Getter
@Setter
public class SeckillMessage {
    private User user;
    private long goodsId;
}
