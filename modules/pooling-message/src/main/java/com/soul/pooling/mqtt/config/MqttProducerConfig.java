package com.soul.pooling.mqtt.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

 /**
 * @Description: Mqtt推送消息配置类
 * @Author: nemo
 * @Date: 2022/6/22
 */
@Configuration
@ConditionalOnProperty(value = "mqtt.producer.enabled", havingValue = "true")
@ConfigurationProperties(prefix = "mqtt.producer")
@Data
public class MqttProducerConfig {


    String defaultTopic;

    Boolean defaultRetained;

    int defaultQos;
    /**
     * 创建推送消息通道
     *
     * @return
     */
    @Bean
    public MessageChannel mqttPublishChannel() {
        return new DirectChannel();
    }

    /**
     * mqtt推送配置
     * @param factory mqtt客户端
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "publishChannel")
    public MessageHandler mqttOutbound(MqttPahoClientFactory factory) {
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler("mqtt-publish-" + System.currentTimeMillis(), factory);
        handler.setAsync(true);

        handler.setDefaultQos(2);
        handler.setDefaultTopic(defaultTopic);
        handler.setDefaultRetained(defaultRetained);
        handler.setDefaultQos(defaultQos);
        return handler;
    }
}
