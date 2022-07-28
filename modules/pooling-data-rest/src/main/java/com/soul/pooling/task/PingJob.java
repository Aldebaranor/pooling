package com.soul.pooling.task;


import com.soul.pooling.config.PoolingConfig;
import com.soul.pooling.service.StatusManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

/**
 * @author 王质松
 * @date 2022/7/20 14:23
 */


@Slf4j
@Component
public class PingJob {

    @Autowired
    public PoolingConfig poolingConfig;

    @Autowired
    private StatusManagement management;

    @Scheduled(fixedDelayString = "1000" )
    public void execute(){
        try{
            List<String> domains = poolingConfig.getUnmannedHostList();
            for(String domain:domains){
                ping(domain);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ping(String domain)  {
        String[] host = domain.split("@");
        try {
            boolean status = InetAddress.getByName(host[0]).isReachable(3000);
            if(status){
                if(!management.isInited(host[1])){
                    management.initForce(host[1]);
                }
            }
            if(!status){
                log.info(host[1],"号无人艇心跳：","----ping[{}]:  {}}",host[0],status?"ok":"timeout");
            }

        } catch (IOException e) {
            log.info(host[1],"号无人艇心跳：","----ping[{}]:  {}}",host[0],"timeout");
        }

    }


}
