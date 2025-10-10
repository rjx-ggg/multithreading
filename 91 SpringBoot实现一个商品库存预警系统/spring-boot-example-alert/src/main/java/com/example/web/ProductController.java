package com.example.web;


import com.example.model.ProductStock;
import com.example.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private InventoryService inventoryService;


    /**
     *
     * SpringBoot实现一个商品库存预警系统
     * @param id
     * @param quantity
     * @return
     */
    @PostMapping("/{id}/reduce/{quantity}")
    public boolean reduceStock(@PathVariable int id, @PathVariable int quantity) {
        return inventoryService.reduceStock(id, quantity); // 减少指定ID的商品库存并检查是否需要发送预警
    }
}