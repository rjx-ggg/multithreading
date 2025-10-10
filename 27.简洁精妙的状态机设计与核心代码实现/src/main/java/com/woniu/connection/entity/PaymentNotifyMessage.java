package com.woniu.connection.entity;


import lombok.Data;

@Data
public class PaymentNotifyMessage {

    private String paymentId;

    private String event;
}
