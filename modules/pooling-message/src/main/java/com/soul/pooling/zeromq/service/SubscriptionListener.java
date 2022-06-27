package com.soul.pooling.zeromq.service;

/**
 * @Description: 监听$
 * @Author: nemo
 * @Date: 2022/6/25 2:49 PM
 */

@FunctionalInterface
public interface SubscriptionListener {

    void onReceive(String s);

}