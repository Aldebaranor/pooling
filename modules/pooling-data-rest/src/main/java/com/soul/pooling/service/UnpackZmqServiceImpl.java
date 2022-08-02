package com.soul.pooling.service;

import com.egova.json.utils.JsonUtils;
import com.flagwind.commons.StringUtils;
import com.soul.pooling.entity.Platform;
import com.soul.pooling.facade.UnpackMessageService;
import com.soul.pooling.model.PlatformMoveData;
import com.soul.pooling.model.SituationMovedata;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
        List<SituationMovedata> situationMovedataList = JsonUtils.deserializeList(s, SituationMovedata.class);
        for(Map.Entry<String , Platform> map:management.getPlatformPool().entrySet()){

            SituationMovedata situationMovedata = situationMovedataList.stream().filter(q -> StringUtils.equals(q.getId(), map.getKey())).findFirst().orElse(null);
            if(situationMovedata == null){
                continue;
            }
            map.getValue().setPlatformMoveData(situationMovedata.getMove());
            
        }

    }
}
