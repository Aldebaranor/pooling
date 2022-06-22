package com.soul.pooling.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

 /**
 * @Description:
 * @Author: nemo
 * @Date: 2022/6/22
 */
@Configuration
@ConfigurationProperties(prefix = "pooling")
@Data
public class PoolingConfig {


    private String activateTopic;



}
