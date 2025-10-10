package com.woniu.connection.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class RedisAsyResultData<T> implements Serializable {

    private boolean success;
    private String error;
    private String redisKey;
    private Integer done;
    private Integer total;

    private boolean flag;

    private T data;

}
