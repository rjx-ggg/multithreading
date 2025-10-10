package com.example.model;


import lombok.Data;

@Data
public class ProductStock {
    private Integer id;           // 主键ID
    private String productName;   // 商品名称
    private Integer stockQuantity;// 库存量
    private Integer threshold;    // 预警阈值
}