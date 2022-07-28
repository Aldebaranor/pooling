package com.soul.pooling.service;

import com.soul.pooling.facade.UnpackMessageService;

/**
 * @Author: Song
 * @Date 2022/7/28 16:51
 */
public class UnpackMessageDefaultServiceImpl implements UnpackMessageService {

    @Override
    public void unpackZmq(String s) {
        System.out.println("-------------ZMQ消费者收到消息"+s);
    }
}
