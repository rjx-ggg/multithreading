package com.woniu.service;

import com.woniu.pojo.GoodsVo;
import com.woniu.pojo.OrderInfo;
import com.woniu.pojo.User;
import org.springframework.transaction.annotation.Transactional;

public interface SeckillService {
    /**
     * 秒杀业务
     * @param user
     * @param goodsVo
     * @return
     */
    @Transactional
    OrderInfo seckill(User user, GoodsVo goodsVo);

    /**
     * 轮询查询是否下单成功
     * @param id
     * @param goodsId
     * @return
     */
    long getSeckillResult(int id, long goodsId);

    /**
     * 秒杀地址生成
     * @param user
     * @param goodsId
     * @return
     */
    String createPath(User user, long goodsId);

    /**
     * 秒杀地址验证
     * @param user
     * @param goodsId
     * @param path
     * @return
     */
    boolean checkPath(User user, long goodsId,String path);
}
