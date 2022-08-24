package com.soul.pooling.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * zmq定时发送态势信息
 * @Author: Song
 * @Date 2022/7/29 14:01
 */
@Slf4j
@Component
public class SituationJob {

//    @Autowired
//    private PoolingManagement management;
//
//    @Autowired(required = false)
//    private ZeroMqPublisher publisher;
//
//    @Scheduled(fixedDelayString = "1000" )
//    public void zmqSend(){
//
//        publisher.setTopic("platSituation");
//        publisher.publish("wzsZMQTest");
//
//        Map<String, Platform> platformPool = management.getPlatformPool();
//        for(String id: platformPool.keySet()){
//            Platform platform = platformPool.get(id);
//            String s = JsonUtils.serialize(platform);
//
//            publisher.publish(s);
//        }
//
//    }
}
