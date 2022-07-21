package com.soul.pooling.controller.free;

import com.egova.exception.ExceptionUtils;
import com.egova.json.utils.JsonUtils;
import com.egova.web.annotation.Api;
import com.flagwind.commons.StringUtils;
import com.soul.pooling.config.PoolingConfig;
import com.soul.pooling.mqtt.producer.MqttMsgProducer;
import com.soul.pooling.netty.NettyUdpClient;
import com.soul.pooling.netty.NettyUdpServer;
import com.soul.pooling.service.StatusManagement;
import com.soul.pooling.zeromq.ZeroMqPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


/**
* @Description:
* @Author: nemo
* @Date: 2022/6/22
*/
@Slf4j
@RestController
@RequestMapping("/free/test")
@RequiredArgsConstructor
public class TestController {


    @Autowired(required = false)
    private ZeroMqPublisher zeroMqPublisher;


    @Api
    @GetMapping(value = "/1")
    public void test1() {
        for(int i=0;i<10000;i++){
            zeroMqPublisher.publish(i+"---------这里是测试：AaBb123@#$");
        }

    }



}


