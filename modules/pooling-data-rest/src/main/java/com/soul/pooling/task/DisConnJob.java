package com.soul.pooling.task;

import com.egova.redis.RedisUtils;
import com.soul.pooling.config.Constants;
import com.soul.pooling.config.MetaConfig;
import com.soul.pooling.entity.Platform;
import com.soul.pooling.service.PoolingManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * zmq定时发送态势信息
 *
 * @Author: Song
 * @Date 2022/7/29 14:01
 */
@Slf4j
@Component
public class DisConnJob {

    @Autowired
    public PoolingManagement poolingManagement;

    @Autowired
    public MetaConfig metaConfig;

    @Autowired
    public PoolingManagement management;

    @Scheduled(fixedDelayString = "5000")
    public void disConnect() {


        Map<String, Platform> platformPool = poolingManagement.getPlatformPool();
        for (Map.Entry<String, Platform> entry : platformPool.entrySet()) {
            String key = Constants.FORCE_HEAR + entry.getKey();
            Boolean aBoolean = RedisUtils.getService(19).getTemplate().hasKey(key);
            if (!aBoolean) {
                //有兵力注销，通知仿真引擎将对应的仿真节点退出
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println(df.format(System.currentTimeMillis()) + "--------------" + key);
                poolingManagement.sendDisActivated(entry.getKey());
                poolingManagement.deleteForce(entry.getKey());
            }
        }
    }
    
}
