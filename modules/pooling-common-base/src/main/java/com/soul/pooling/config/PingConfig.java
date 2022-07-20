package com.soul.pooling.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2022/7/20 14:02
 */
@Configuration
@ConfigurationProperties(prefix = "ping")
@Data
public class PingConfig {

    private List<String> hostList = new ArrayList<>();

}
