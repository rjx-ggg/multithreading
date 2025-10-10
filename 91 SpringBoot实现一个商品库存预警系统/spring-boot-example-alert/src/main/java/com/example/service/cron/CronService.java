package com.example.service.cron;

import com.example.mapper.ProductStockMapper;
import com.example.model.ProductStock;
import com.example.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public class CronService {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductStockMapper productStockMapper;

    @Scheduled(fixedRate = 3600000) // 每小时执行一次
    public void checkAndSendAlerts() {
        List<ProductStock> lowStockProducts = productStockMapper.getLowStockProducts(); // 获取所有低于阈值的商品库存信息
        for (ProductStock product : lowStockProducts) {
            inventoryService.sendAlertEmail(product); // 发送邮件通知
        }
    }

}
