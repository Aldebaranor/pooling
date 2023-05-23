package com.soul.pooling.task;

import com.egova.exception.ExceptionUtils;
import com.soul.pooling.config.MetaConfig;
import com.soul.pooling.model.PlatformStatus;
import com.soul.pooling.service.PoolingManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Song
 * @Date 2022/9/30 14:59
 */
@Component
public class initPooling implements ApplicationRunner {

    @Autowired
    public MetaConfig metaConfig;
    @Autowired
    public PoolingManagement management;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> list = metaConfig.getForcesList();
        if (metaConfig.getBeTest()) {
            for (int i = 2; i <= 107; i++) {
                if (list.contains(String.valueOf(i))) {

                    continue;
                }
                management.initForce(String.valueOf(i));
                initPooling(String.valueOf(i));
            }

        }
    }

    public void initPooling(String platformId) {
        PlatformStatus forcesData = management.getForcesData(platformId);
        if (forcesData == null) {
            throw ExceptionUtils.api(String.format("该兵力未注册") + platformId);
        }
        if (!forcesData.getInitStatus()) {
            throw ExceptionUtils.api(String.format("该兵力未初始化") + platformId);
        }

        forcesData.setActiveStatus(true);

        management.activeForce(platformId);

    }
}
