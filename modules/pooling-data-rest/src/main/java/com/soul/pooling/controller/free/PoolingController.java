package com.soul.pooling.controller.free;

import com.egova.exception.ExceptionUtils;
import com.egova.json.utils.JsonUtils;
import com.egova.web.annotation.Api;
import com.flagwind.commons.StringUtils;
import com.soul.pooling.config.PoolingConfig;
import com.soul.pooling.model.ForcesStatus;
import com.soul.pooling.mqtt.producer.MqttMsgProducer;
import com.soul.pooling.netty.NettyUdpClient;
import com.soul.pooling.netty.NettyUdpServer;
import com.soul.pooling.service.StatusManagement;
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
@RequestMapping("/free/pooling")
@RequiredArgsConstructor
public class PoolingController {

    private final RestTemplate restTemplate;

    @Autowired
    private StatusManagement management;

    @Autowired
    private MqttMsgProducer mqttMsgProducer;

    @Autowired
    private PoolingConfig poolingConfig;

    @Autowired
    private NettyUdpServer nettyUdpServer;

    @Autowired
    private NettyUdpClient nettyUdpClient;

    @Api
    @GetMapping(value = "/start/{experiment}")
    public Boolean pumpStart(@PathVariable String experiment) {
        if (StringUtils.isBlank(experiment)) {
            throw ExceptionUtils.api("structName can not be null");
        }
        management.cleanForce();
        return true;
    }

    @Api
    @GetMapping(value = "/forces")
    public Map<String, ForcesStatus> forces() {
        return management.getAll();
    }

    @Api
    @PostMapping(value = "/init/forces")
    public Boolean forcesInit(@RequestBody List<String> forces) {
        for (String id:forces){
            management.initForce(id);
        }
        return true;
    }
    @Api
    @PostMapping(value = "/activate/forces")
    public Boolean forcesActivate(@RequestBody List<String> forces) {
        //通知仿真节点（resources）执行激活
        mqttMsgProducer.producerMsg(poolingConfig.getActivateTopic(),JsonUtils.serialize(forces));
        return true;
    }
    @Api
    @GetMapping(value = "/activated/{forcesId}")
    public Boolean forcesActivated(@PathVariable String forcesId) {
        ForcesStatus forcesData = management.getForcesData(forcesId);
        if(forcesData == null){
            throw ExceptionUtils.api(String.format("该兵力未注册"));
        }
        if(!forcesData.getInitStatus()){
            throw ExceptionUtils.api(String.format("该兵力未初始化"));
        }

        forcesData.setActiveStatus(true);
        //通知仿真
        nettyUdpClient.sendToServer(JsonUtils.serialize(forcesData));
        //TODO:
        //进行资源注册
        management.activeForce(forcesId);
        return true;
    }

    @Api
    @PostMapping(value = "/dis-activate/{forcesId}")
    public Boolean forcesDisActivated(@PathVariable String forcesId) {
        ForcesStatus forcesData = management.getForcesData(forcesId);
        if(forcesData == null){
            throw ExceptionUtils.api(String.format("该兵力未注册"));
        }
        if(!forcesData.getInitStatus()){
            throw ExceptionUtils.api(String.format("该兵力未初始化"));
        }
        if(!forcesData.getActiveStatus()){
            throw ExceptionUtils.api(String.format("该兵力未被激活"));
        }
        forcesData.setActiveStatus(false);
        //通知仿真
        nettyUdpClient.sendToServer(JsonUtils.serialize(forcesData));
        //TODO:
        //进行资源注册
        management.disActiveForce(forcesId);
        return true;
    }

}


