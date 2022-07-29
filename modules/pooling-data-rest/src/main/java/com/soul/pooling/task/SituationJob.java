package com.soul.pooling.task;

import com.egova.json.utils.JsonUtils;
import com.soul.pooling.entity.Platform;
import com.soul.pooling.entity.Sensor;
import com.soul.pooling.entity.Weapon;
import com.soul.pooling.service.StatusManagement;
import com.soul.pooling.zeromq.ZeroMqPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * zmq定时发送态势信息
 * @Author: Song
 * @Date 2022/7/29 14:01
 */
@Slf4j
@Component
public class SituationJob {

    @Autowired
    private StatusManagement management;

    @Autowired
    private ZeroMqPublisher publisher;

    @Scheduled(fixedDelayString = "1000" )
    public void zmqSend(){

//        publisher.setTopic("platSituation");

        Map<String, Platform> platformPool = management.getPlatformPool();
        for(String id: platformPool.keySet()){
            Platform platform = platformPool.get(id);
            String s = JsonUtils.serialize(platform);

            publisher.publish(s);
        }

    }
}
