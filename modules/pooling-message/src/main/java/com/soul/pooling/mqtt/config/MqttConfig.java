package com.soul.pooling.mqtt.config;

import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

/**
 * @ClassName: MqttConfig
 * @Description: MQTT配置类
 * @Author: jodi
 * @Date: 2021/6/29 15:20
 * @Version: 1.0
 */
@Configuration
@ConditionalOnExpression("${mqtt.producer.enabled:true} || ${mqtt.subscribe.enabled:true}")
@ConfigurationProperties(prefix = "mqtt.client")
@Data
public class MqttConfig {


    private String host;

    private String userName;

    private String password;

    private int keepAliveInterval;

    private int connectionTimeout;

    /**
     * 创建Mqtt客户端
     * @return
     */
    @Bean
    public MqttPahoClientFactory factory(){
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(userName);
        options.setPassword(password.toCharArray());
        options.setServerURIs(new String[]{host});
        options.setKeepAliveInterval(keepAliveInterval);
        options.setConnectionTimeout(connectionTimeout);
        factory.setConnectionOptions(options);
        return factory;
    }
}
