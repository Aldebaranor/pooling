package com.soul.pooling.service.impl;

import com.egova.json.utils.JsonUtils;
import com.soul.pooling.entity.Engage;
import com.soul.pooling.facade.UnpackMessageService;
import com.soul.pooling.model.ResourceModel;
import com.soul.pooling.service.PoolingManagement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * @Author: Song
 * @Date 2022/7/28 17:32
 */
@Slf4j
@RequiredArgsConstructor
@Component(value = "test-zmq")
public class UnpackZmqServiceImpl implements UnpackMessageService {

    @Autowired
    private PoolingManagement management;

    @Override
    public void unpackZmq(String s) {
        ResourceModel model = JsonUtils.deserialize(s, ResourceModel.class);

        Engage engage = management.getEngageByPlatform(model.getPlatformCode()).stream().filter(q -> q.getDeviceCode().equals(model.getDeviceCode())).collect(Collectors.toList()).get(0);
        int num = engage.getNumber() - model.getNum();
        engage.setNumber(num);

    }
}
