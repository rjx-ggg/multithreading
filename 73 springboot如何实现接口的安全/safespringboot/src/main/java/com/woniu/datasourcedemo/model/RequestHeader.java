package com.woniu.datasourcedemo.model;

import lombok.Builder;
import lombok.Data;

//@Data
//@Builder
public class RequestHeader {
    private String sign;
    private Long timestamp;
    private String nonce;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}
