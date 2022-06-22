package com.soul.pooling.mqtt.producer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MqttPushlishMsg
 * @Description: 向MQTT服务器推送消息
 * @Author: jodi
 * @Date: 2021/6/29 15:27
 * @Version: 1.0
 */
@Component
@ConditionalOnProperty(value = "mqtt.producer.enabled", havingValue = "true",matchIfMissing = false)
@MessagingGateway(defaultRequestChannel = "publishChannel")
public interface MqttMsgProducer {

    /**
     * 推送消息
     * @param data
     */
    void producerMsg(String data);

    /**
     * 推送消息
     * @param topic
     * @param qos
     * @param payload
     */
    void producerMsg(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);

    /**
     * 推送消息
     * @param topic 主题
     * @param payload 消息内容
     */
    void producerMsg(@Header(MqttHeaders.TOPIC) String topic, String payload);
}
