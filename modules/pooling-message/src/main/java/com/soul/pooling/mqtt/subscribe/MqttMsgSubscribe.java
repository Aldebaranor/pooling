package com.soul.pooling.mqtt.subscribe;


import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;


 /**
 * @Description: mqtt订阅处理
 * @Author: nemo
 * @Date: 2022/6/22
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
