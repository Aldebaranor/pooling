package com.soul.pooling.controller.free;

import com.egova.exception.ExceptionUtils;
import com.egova.json.utils.JsonUtils;
import com.egova.web.annotation.Api;
import com.flagwind.commons.StringUtils;
import com.soul.pooling.config.Constants;
import com.soul.pooling.config.PoolingConfig;
import com.soul.pooling.model.ActivatedModel;
import com.soul.pooling.model.PlatformStatus;
import com.soul.pooling.mqtt.producer.MqttMsgProducer;
import com.soul.pooling.netty.NettyUdpClient;
import com.soul.pooling.service.StatusManagement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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


    @Autowired
    private StatusManagement management;

    @Autowired
    private MqttMsgProducer mqttMsgProducer;

    @Autowired
    private PoolingConfig poolingConfig;

    @Autowired(required = false)
    private NettyUdpClient nettyUdpClient;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 开始试验，清楚缓存
     * @param experiment
     * @return
     */
    @Api
    @GetMapping(value = "/start/{experiment}")
    public Boolean pumpStart(@PathVariable String experiment) {
        if (StringUtils.isBlank(experiment)) {
            throw ExceptionUtils.api("structName can not be null");
        }
        management.cleanForce();
        return true;
    }

    /**
     * 获取所有上电的节点信息
     * @return
     */
    @Api
    @GetMapping(value = "/platform")
    public Map<String, PlatformStatus> platform() {
        return management.getAll();
    }

    /**
     * 接收resource的初始化
     * @param forces
     * @return
     */
    @Api
    @PostMapping(value = "/init/platform")
    public Boolean forcesInit(@RequestBody List<String> forces) {
        for (String id:forces){
            management.initForce(id);
        }
        return true;
    }

    /**
     * 接收试验管理的注册指令，通知节点进行入云注册
     * @param forces
     * @return
     */
    @Api
    @PostMapping(value = "/activate/platform")
    public Boolean forcesActivate(@RequestBody List<String> forces) {
        //通知仿真节点（resources）执行激活
        mqttMsgProducer.producerMsg(poolingConfig.getActivateTopic(),JsonUtils.serialize(forces));
        return true;
    }

    /**
     *接收resource的入云注册
     * @param platformId
     * @return
     */
    @Api
    @GetMapping(value = "/activated/{platformId}")
    public Boolean forcesActivated(@PathVariable String platformId) {
        PlatformStatus forcesData = management.getForcesData(platformId);
        if(forcesData == null){
            throw ExceptionUtils.api(String.format("该兵力未注册"));
        }
        if(!forcesData.getInitStatus()){
            throw ExceptionUtils.api(String.format("该兵力未初始化"));
        }

        forcesData.setActiveStatus(true);

        //通知上线
        sendActivated(platformId);

        //TODO:
        //进行资源注册
        management.activeForce(platformId);
        return true;
    }

    /**
     * 接收resource的注销
     * @param platformId
     * @return
     */

    @Api
    @GetMapping(value = "/dis-activated/{platformId}")
    public Boolean forcesDisActivated(@PathVariable String platformId) {
        PlatformStatus forcesData = management.getForcesData(platformId);
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

        //通知下线
        sendDisActivated(platformId);

        //TODO:
        //进行资源注册
        management.disActiveForce(platformId);
        return true;
    }


    private Boolean sendActivated(String id){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ActivatedModel model = new ActivatedModel();
        model.setId(id);
        model.setType("1");
        HttpEntity<Object> request = new HttpEntity<>(model, headers);
        try{
            ResponseEntity<String> response = restTemplate.postForEntity(poolingConfig.getSimulationUrlHead() + Constants.OPERATE_FORCE_URL, request, String.class);
            if(response.getStatusCode() != HttpStatus.OK){
                throw ExceptionUtils.api("仿真引擎未开启");
            }
        }catch (Exception ex){
            throw ExceptionUtils.api("仿真引擎未开启");
        }
        return true;

    }

    private Boolean sendDisActivated(String id){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ActivatedModel model = new ActivatedModel();
        model.setId(id);
        model.setType("0");
        HttpEntity<Object> request = new HttpEntity<>(model, headers);
        try{
            ResponseEntity<String> response = restTemplate.postForEntity(poolingConfig.getSimulationUrlHead() + Constants.OPERATE_FORCE_URL, request, String.class);
            if(response.getStatusCode() != HttpStatus.OK){
                throw ExceptionUtils.api("仿真引擎未开启");
            }
        }catch (Exception ex){
            throw ExceptionUtils.api("仿真引擎未开启");
        }
        return true;

    }

}


