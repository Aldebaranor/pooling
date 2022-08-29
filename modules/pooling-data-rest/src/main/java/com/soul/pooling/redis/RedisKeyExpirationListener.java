package com.soul.pooling.redis;

import com.soul.pooling.config.Constants;
import com.soul.pooling.service.PoolingManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * @Description:
 * @Author: nemo
 * @Date: 2022/5/23
 */
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    private PoolingManagement management;


    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 针对redis数据失效事件，进行数据处理
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
//        String expiredKey = message.toString();
//        if (expiredKey.contains(Constants.FORCE_HEAR)) {
//            //有兵力注销，通知仿真引擎将对应的仿真节点退出
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            System.out.println(df.format(System.currentTimeMillis()) + "--------------" + expiredKey);
//            String id = expiredKey.replace(Constants.FORCE_HEAR, "");
//            management.sendDisActivated(id);
//            management.deleteForce(id);
//        }

    }
}
