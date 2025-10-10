package com.woniu.datasourcedemo.service;


import java.util.stream.LongStream;

/**
 * 代码调试神器
 *
 * @author 蜗牛
 * @date 2023/01/13
 */
public class MainTest {


    public static void main(String[] args) {

        long[] dataResult = LongStream.of(10, 11, 52, 49, 5, 94, 105, 88)
                .filter(data -> data > 10)
                .map(data -> data * 2)
                .distinct()
                .sorted()
                .toArray();
    }
}
