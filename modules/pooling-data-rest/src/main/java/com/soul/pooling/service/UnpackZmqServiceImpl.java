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
        List<SituationMovedata> situationMovedataList = JsonUtils.deserialize(s, PlatformMoveData.class);;
        for(SituationMovedata situationMovedata : situationMovedataList) {
            String id = situationMovedata.getId();
            Platform platform = management.getPlatformPool().get(id);
            PlatformMoveData moveData = situationMovedata.getMove();
            platform.setPlatformMoveData(moveData);
            management.getPlatformPool().put(id, platform);
        }


//        int lastIndex = s.lastIndexOf("@");
//        String substring = s.substring(0, lastIndex);
//        String[] split = substring.split("@");
//        log.info("移动数据：" + Arrays.toString(split));
//        String id = split[1];
//        PlatformMoveData moveData = new PlatformMoveData();
//        moveData.setLon(Double.valueOf(split[2]));
//        moveData.setLat(Double.valueOf(split[3]));
//        moveData.setAlt(Double.valueOf(split[4]));
//        moveData.setHeading(Double.valueOf(split[5]));
//        moveData.setRoll(Double.valueOf(split[6]));
//        moveData.setPitch(Double.valueOf(split[7]));
//        moveData.setSpeed(Double.valueOf(split[8]));
//        moveData.setLife(Double.valueOf(split[9]));
//        moveData.setUpdateTime(Timestamp.valueOf(split[10]));



    }
}
