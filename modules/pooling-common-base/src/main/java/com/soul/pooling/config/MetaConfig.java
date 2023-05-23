package com.soul.pooling.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: 王质松
 * @Date: 2022/8/3 10:29
 */
@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "meta")
public class MetaConfig {

    private String unpackServiceCode;

    private String simulationUrlHead;

    private String scenarioCode;

    private Integer situationDb;

    private Boolean beTest = false;

    private Boolean beNet = false;

//    private Integer onLineDb;

    private Boolean beRealEquipment;

    private String busUrl;

    private List<String> forcesList = new ArrayList<>();
}
