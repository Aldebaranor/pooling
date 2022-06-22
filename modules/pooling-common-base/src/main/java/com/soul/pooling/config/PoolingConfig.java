package com.soul.pooling.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @ClassName: MqttConfig
 * @Description: MQTT配置类
 * @Author: jodi
 * @Date: 2021/6/29 15:20
 * @Version: 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "pooling")
@Data
public class PoolingConfig {


    private String activateTopic;



}
