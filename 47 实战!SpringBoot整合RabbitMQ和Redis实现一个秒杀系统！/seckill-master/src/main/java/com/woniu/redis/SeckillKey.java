package com.woniu.redis;

/**
 * @program: SeckillProject
 * @description: 秒杀状态 key
 **/
public class SeckillKey extends BasePrefix {
    private SeckillKey(String prefix) {
        super(prefix);
    }
    public static SeckillKey isGoodsOver = new SeckillKey("over");
    public static SeckillKey getSeckillPath = new SeckillKey("seckillPath");
}
