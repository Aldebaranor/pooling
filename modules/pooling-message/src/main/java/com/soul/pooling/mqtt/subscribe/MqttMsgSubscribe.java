package com.soul.pooling.mqtt.subscribe;


import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MqttMsgSubscribe
 * @Description: mqtt订阅处理
 * @Author: jodi
 * @Date: 2021/6/29 15:47
 * @Version: 1.0
 */
@Component
@ConditionalOnProperty(value = "mqtt.subscribe.enabled", havingValue = "true" ,matchIfMissing = false)
@Slf4j
public class MqttMsgSubscribe implements MessageHandler {

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String topic = String.valueOf(message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC));
        String payload = String.valueOf(message.getPayload());


    }






}
