package com.example.entity;


import lombok.Data;

@Data
public class Order {
    private Long id;
    private String productId;
    private Long ownerId;
}