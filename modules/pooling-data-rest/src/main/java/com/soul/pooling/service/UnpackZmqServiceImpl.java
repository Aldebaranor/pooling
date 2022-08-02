package com.soul.pooling.service;

import com.egova.json.utils.JsonUtils;
import com.soul.pooling.entity.Platform;
import com.soul.pooling.facade.UnpackMessageService;
import com.soul.pooling.model.PlatformMoveData;
import com.soul.pooling.model.SituationMovedata;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Song
 * @Date 2022/7/28 17:32
 */
@Slf4j
@RequiredArgsConstructor
@Component(value = "test-zmq")
public class UnpackZmqServiceImpl implements UnpackMessageService {

    @Autowired
    private StatusManagement management;

    @Override
    public void unpackZmq(String s) {
        List<SituationMovedata> situationMovedataList = JsonUtils.deserializeList(s, PlatformMoveData.class);;
        for(SituationMovedata situationMovedata : situationMovedataList) {
            String id = situationMovedata.getId();
            Platform platform = management.getPlatformPool().get(id);
            PlatformMoveData moveData = situationMovedata.getMove();
            platform.setPlatformMoveData(moveData);
            management.getPlatformPool().put(id, platform);
        }

    }
}
