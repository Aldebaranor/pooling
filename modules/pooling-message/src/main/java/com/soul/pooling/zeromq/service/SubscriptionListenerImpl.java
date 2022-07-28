package com.soul.pooling.zeromq.service;

import com.soul.pooling.facade.UnpackMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 监听实现$
 * @Author: nemo
 * @Date: 2022/6/27 9:28 AM
 */
@Slf4j
@Service
public class SubscriptionListenerImpl implements SubscriptionListener{

    @Autowired
    UnpackMessageService messageService;

    @Override
    public void onReceive(String s) {
        log.info(s);
        messageService.unpackZmq(s);
    }
}
