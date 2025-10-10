package com.example.web;

import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Redis+Lua整合，实现订单转手功能
     */
    @PostMapping("/orders/{orderId}/transfer")
    public Long transferOrder(@PathVariable Long orderId,
                              @RequestParam Long currentOwner,
                              @RequestParam Long newOwner) {
        return orderService.transferOrder(orderId, currentOwner, newOwner);
    }

}