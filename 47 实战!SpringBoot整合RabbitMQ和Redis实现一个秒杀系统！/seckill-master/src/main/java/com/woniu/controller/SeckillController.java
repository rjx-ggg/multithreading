package com.woniu.controller;

import com.woniu.access.AccessLimit;
import com.woniu.pojo.GoodsVo;
import com.woniu.pojo.SeckillOrder;
import com.woniu.pojo.User;
import com.woniu.rabbitmq.MQSender;
import com.woniu.rabbitmq.SeckillMessage;
import com.woniu.redis.GoodsKey;
import com.woniu.redis.RedisService;
import com.woniu.result.CodeMsg;
import com.woniu.result.Result;
import com.woniu.service.GoodsService;
import com.woniu.service.OrderService;
import com.woniu.service.SeckillService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: SeckillProject
 * @description: 秒杀表现层
 **/
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender sender;

    private Map<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();

    /**
     * 实战！给简易秒杀系统添加监控sql运行功能
     */
    @RequestMapping(value = "/{path}/seckill_mq", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> seckillMq(User user, @RequestParam("goodsId") long goodsId, @PathVariable("path") String path) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //验证path
        boolean checkPath = seckillService.checkPath(user, goodsId, path);
        if (!checkPath) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }

        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if (over) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //预减库存
        long stock = redisService.decr(GoodsKey.getSeckillGoodsStock, "" + goodsId);//10
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        //判断是否已经秒杀到了
        SeckillOrder order = orderService.getOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        //放入消息队列
        SeckillMessage sm = new SeckillMessage();
        sm.setUser(user);
        sm.setGoodsId(goodsId);
        sender.sendSeckillMessage(sm);
        return Result.success(0);//排队中
    }

    /**
     * 系统启动及初始化，用于系统启动后将热点数据存入redis中
     */
    public void afterPropertiesSet() {
        List<GoodsVo> goodsList = goodsService.listGoodVo();
        if (goodsList == null) {
            return;
        }
        //Redis预热秒杀商品数据
        for (GoodsVo goods : goodsList) {
            redisService.set(GoodsKey.getSeckillGoodsStock,
                    "" + goods.getId(), goods.getStockCount(), 1800);
            localOverMap.put(goods.getId(), false);
        }
    }

    /**
     * 客户端轮询查询是否下单成功
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> seckillResult(@RequestParam("goodsId") long goodsId, User user) {
        if (user == null) {
            return Result.error(CodeMsg.USER_NO_LOGIN);
        }
        long result = seckillService.getSeckillResult(user.getId(), goodsId);
        return Result.success(result);
    }

    /**
     * 获取秒杀地址
     * @param user
     * @param goodsId
     * @return
     */
    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @RequestMapping(value = "/path", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getSeckillPath(User user, @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.USER_NO_LOGIN);
        }
        String path = seckillService.createPath(user, goodsId);
        return Result.success(path);
    }
}
