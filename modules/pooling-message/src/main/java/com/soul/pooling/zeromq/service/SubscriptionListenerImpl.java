package com.soul.pooling.zeromq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description: 监听实现$
 * @Author: nemo
 * @Date: 2022/6/27 9:28 AM
 */
@Slf4j
@Service
public class SubscriptionListenerImpl implements SubscriptionListener{
    @Override
    public void onReceive(String s) {
        log.info(s);
    }
}
