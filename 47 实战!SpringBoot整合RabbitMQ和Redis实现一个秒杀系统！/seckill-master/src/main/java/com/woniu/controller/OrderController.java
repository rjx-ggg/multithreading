package com.woniu.controller;

import com.woniu.pojo.GoodsVo;
import com.woniu.pojo.OrderDetail;
import com.woniu.pojo.OrderInfo;
import com.woniu.pojo.User;
import com.woniu.result.CodeMsg;
import com.woniu.result.Result;
import com.woniu.service.GoodsService;
import com.woniu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: SeckillProject
 * @description: 订单表现层
 **/
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetail> info(User user, @RequestParam("orderId") long orderId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo orderInfo = orderService.getByOrderId(orderId);
        if(orderInfo == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }

        long goodsId = orderInfo.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoById(goodsId);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(orderInfo);
        orderDetail.setGoods(goods);
        return Result.success(orderDetail);
    }
}
