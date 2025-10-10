package com.springboot.sample.thread;


public interface RejectPolicy<T> {

    void reject(BlockQueue<T> blockQueue, T e);

}
