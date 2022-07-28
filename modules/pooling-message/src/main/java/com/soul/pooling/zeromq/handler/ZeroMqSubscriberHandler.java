package com.soul.pooling.zeromq.handler;

import com.soul.pooling.facade.UnpackMessageService;
import com.soul.pooling.model.PlatformMoveData;
import com.soul.pooling.zeromq.UnpackMassageFactory;
import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @Author: Song
 * @Date 2022/7/28 16:57
 */
@Slf4j
@Component
@AllArgsConstructor
@NoArgsConstructor
public class ZeroMqSubscriberHandler {

    private UnpackMassageFactory factory;

    protected void channelRead0(PlatformMoveData platformMoveData) {

        UnpackMessageService service = null;
        String s = platformMoveData.toString();
        if(StringUtils.isBlank(s)){
            service = factory.get("default");
        }else {
            service = factory.get(s);
        }
        service.unpackZmq(s);


    }

}
