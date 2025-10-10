package com.example.jwt.inte;


import java.util.List;

public interface HandleReturn {

    /**
     * 处理返回结果方法
     * @param t 拆分后多次请求结果
     * @return R 处理后的返回结果
     */
    Object handleReturn(List t);
}