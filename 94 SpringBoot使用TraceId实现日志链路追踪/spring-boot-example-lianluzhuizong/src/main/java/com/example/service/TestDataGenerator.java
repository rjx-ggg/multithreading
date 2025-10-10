package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDataGenerator {

    @Autowired
    private ProductService productService;

    public void generateTestData() {
        // Generate and add test products to the ranking
        for (int i = 1; i <= 10_000_000; i++) {
            String productId = "product_" + i;
            double score = Math.random() * 10000;
            productService.addProductToRanking(productId, score);
        }
    }
}