package com.soul.pooling.controller.free;

import com.egova.exception.ExceptionUtils;
import com.egova.json.utils.JsonUtils;
import com.egova.web.annotation.Api;
import com.flagwind.commons.StringUtils;
import com.soul.pooling.condition.ResourceCondition;
import com.soul.pooling.config.PoolingConfig;
import com.soul.pooling.entity.*;
import com.soul.pooling.model.Command;
import com.soul.pooling.model.PlatformMoveData;
import com.soul.pooling.model.PlatformStatus;
import com.soul.pooling.mqtt.producer.MqttMsgProducer;
import com.soul.pooling.netty.NettyUdpClient;
import com.soul.pooling.service.StatusManagement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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


    /**
     * 开始试验，清楚缓存
     *
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
     *
     * @return
     */
    @Api
    @GetMapping(value = "/platform")
    public Map<String, PlatformStatus> platform() {
        return management.getAll();
    }

    /**
     * 接收resource的初始化
     *
     * @param forces
     * @return
     */
    @Api
    @PostMapping(value = "/init/platform")
    public Boolean forcesInit(@RequestBody List<String> forces) {
        for (String id : forces) {
            management.initForce(id);
        }
        return true;
    }

    /**
     * 接收试验管理的注册指令，通知节点进行入云注册
     *
     * @param forces
     * @return
     */
    @Api
    @PostMapping(value = "/activate/platform")
    public Boolean forcesActivate(@RequestBody List<String> forces) throws InterruptedException {
        //通知仿真节点（resources）执行激活

        for (String s : forces) {
            List<String> list = new ArrayList<>();
            list.add(s);
            mqttMsgProducer.producerMsg(poolingConfig.getActivateTopic(), JsonUtils.serialize(list));
            Thread.sleep(1);
        }

        return true;
    }

    /**
     * 接收resource的入云注册
     *
     * @param platformId
     * @return
     */
    @Api
    @GetMapping(value = "/activated/{platformId}")
    public Boolean forcesActivated(@PathVariable String platformId) {
        PlatformStatus forcesData = management.getForcesData(platformId);
        if (forcesData == null) {
            throw ExceptionUtils.api(String.format("该兵力未注册"));
        }
        if (!forcesData.getInitStatus()) {
            throw ExceptionUtils.api(String.format("该兵力未初始化"));
        }

        forcesData.setActiveStatus(true);

        //通知上线
        management.sendActivated(platformId);

        management.activeForce(platformId);
        return true;
    }

    /**
     * 接收resource的注销
     *
     * @param platformId
     * @return
     */

    @Api
    @GetMapping(value = "/dis-activated/{platformId}")
    public Boolean forcesDisActivated(@PathVariable String platformId) {
        PlatformStatus forcesData = management.getForcesData(platformId);
        if (forcesData == null) {
            throw ExceptionUtils.api(String.format("该兵力未注册"));
        }
        if (!forcesData.getInitStatus()) {
            throw ExceptionUtils.api(String.format("该兵力未初始化"));
        }
        if (!forcesData.getActiveStatus()) {
            throw ExceptionUtils.api(String.format("该兵力未被激活"));
        }

        //通知下线
        management.sendDisActivated(platformId);

        management.disActiveForce(platformId);

        forcesData.setActiveStatus(false);
        return true;
    }

    @Api
    @PostMapping(value = "/dis-activated/platformIds")
    public Boolean forcesDisActivatedBatch(@RequestBody List<String> platformIds) {
        for (String platformId : platformIds) {
            PlatformStatus forcesData = management.getForcesData(platformId);

            //通知下线
            management.sendDisActivated(platformId);

            management.disActiveForce(platformId);
            forcesData.setActiveStatus(false);
        }
        return true;
    }

    /**
     * 获取资源池的平台信息
     *
     * @return
     */
    @Api
    @GetMapping(value = "/pool/platform")
    public Map<String, Platform> platformPool() {
        return management.getPlatformPool();
    }

    @Api
    @GetMapping(value = "/platform/moveData/{platformId}")
    public PlatformMoveData platformMoveData(@PathVariable String platformId) {
        return management.getPlatformPool().get(platformId).getPlatformMoveData();
    }




    @Api
    @GetMapping(value = "/all/find")
    public Map<String, Find> getAllFinds() {
        return management.getFindPool();
    }

    @Api
    @PostMapping(value = "/list/find")
    public List<Find> getFindList(@RequestBody ResourceCondition condition) {

        Map<String, Find> findPool = management.getFindPool();
        return null;
        //findPool.entrySet().stream().filter()
    }

    @Api
    @GetMapping(value = "/pool/fix")
    public Map<String, Fix> getAllFixes() {
        return management.getFixPool();
    }

    @Api
    @GetMapping(value = "/pool/track")
    public Map<String, Track> getAllTracks() {
        return management.getTrackPool();
    }

    @Api
    @GetMapping(value = "/pool/target")
    public Map<String, Target> getAllTargets() {
        return management.getTargetPool();
    }

    @Api
    @GetMapping(value = "/pool/engage")
    public Map<String, Engage> getAllEngages() {
        return management.getEngagePool();
    }

    @Api
    @GetMapping(value = "/pool/asses")
    public Map<String, Asses> getAllAsses() {
        return management.getAssesPool();
    }


}


