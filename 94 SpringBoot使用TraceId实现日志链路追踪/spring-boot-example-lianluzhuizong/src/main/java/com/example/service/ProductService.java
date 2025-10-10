package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String HOT_PRODUCTS_KEY = "hot_products";

    public void addProductToRanking(String productId, double score) {
        redisTemplate.opsForZSet().add(HOT_PRODUCTS_KEY, productId, score);
    }

    public void removeProductFromRanking(String productId) {
        redisTemplate.opsForZSet().remove(HOT_PRODUCTS_KEY, productId);
    }

    public Long getProductRank(String productId) {
        return redisTemplate.opsForZSet().reverseRank(HOT_PRODUCTS_KEY, productId);
    }

    public Double getProductScore(String productId) {
        return redisTemplate.opsForZSet().score(HOT_PRODUCTS_KEY, productId);
    }

    public Set<String> getTopProducts(int count) {
        return redisTemplate.opsForZSet().reverseRange(HOT_PRODUCTS_KEY, 0, count - 1);
    }
}