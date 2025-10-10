package com.example.service;


import com.example.mapper.ProductStockMapper;
import com.example.model.ProductStock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class InventoryService {

    @Autowired
    private ProductStockMapper productStockMapper;

    @Autowired
    private JavaMailSender mailSender;

    public ProductStock getProductById(int id) {
        return productStockMapper.getProductById(id); // 根据ID获取商品库存信息
    }

    @Transactional
    public boolean reduceStock(int productId, int quantity) {
        productStockMapper.reduceStock(productId, quantity); // 减少指定ID的商品库存
        int alertCount = productStockMapper.getAlertCountForProduct(productId); // 获取预警次数
        // 如果有预警，则发送邮件通知
        if (alertCount > 0) {
            sendAlertEmail(productStockMapper.getProductById(productId));
        }
        return alertCount > 0;
    }


    public void sendAlertEmail(ProductStock product) {
         log.info("发送邮件");
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo("woniu@qq.com"); // 收件人邮箱地址
//        message.setSubject("Inventory Alert: Low Stock for " + product.getProductName()); // 邮件主题
//        message.setText("The stock quantity for " + product.getProductName() + " has dropped below the threshold. Current stock: " + product.getStockQuantity()); // 邮件正文
//        mailSender.send(message); // 发送邮件
    }
}