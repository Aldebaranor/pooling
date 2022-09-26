package com.soul.pooling.task;


import com.egova.redis.RedisUtils;
import com.soul.pooling.config.Constants;
import com.soul.pooling.config.MetaConfig;
import com.soul.pooling.entity.Platform;
import com.soul.pooling.model.PlatformMoveData;
import com.soul.pooling.model.PlatformStatus;
import com.soul.pooling.model.SituationMoveData;
import com.soul.pooling.model.SituationTemArmy;
import com.soul.pooling.service.PoolingManagement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @Author: Song
 * @Date 2022/8/3 8:55
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class MoveDataJob {
    @Autowired
    private MetaConfig metaConfig;

    @Autowired
    private PoolingManagement management;

    @Scheduled(fixedDelayString = "500")
    public void updateMoveData() {

        if (StringUtils.isBlank(metaConfig.getScenarioCode())) {
            return;
        }
        String armyKey = String.format(Constants.SCENARIO_FORCES, metaConfig.getScenarioCode());
        Map<String, SituationTemArmy> army = RedisUtils.getService(metaConfig.getSituationDb()).extrasForHash().hgetall(armyKey, SituationTemArmy.class);
        String moveKey = String.format(Constants.SCENARIO_MOVE, metaConfig.getScenarioCode());
        Map<String, String> moveMap = RedisUtils.getService(metaConfig.getSituationDb()).extrasForHash().hgetall(moveKey);
        String timeKey = String.format(Constants.SCENARIO_TIME, metaConfig.getScenarioCode());
        String s = RedisUtils.getService(metaConfig.getSituationDb()).getTemplate().opsForValue().get(timeKey);


        if (CollectionUtils.isEmpty(moveMap)) {
            return;
        }

        for (Map.Entry<String, String> entry : moveMap.entrySet()) {
            SituationTemArmy situationTemArmy = army.get((entry.getKey()));
            if (situationTemArmy == null) {
                return;
            }
            String[] split = entry.getValue().split("@");
            if (split.length < 8) {
                log.error("-数据不合法-" + entry.getValue());
            }
            SituationMoveData situationMoveData = new SituationMoveData();
            if (situationTemArmy.getIff() == 3 && !StringUtils.isBlank(situationTemArmy.getCode())) {
                situationMoveData.setId(situationTemArmy.getCode());
            } else {
                situationMoveData.setId(situationTemArmy.getId());
            }
            situationMoveData.setTime(Long.parseLong(s));
            situationMoveData.setMove(initMoveData(split));

            Platform platform = management.getPlatformPool().get(situationMoveData.getId());
            if (platform != null) {
                platform.setPlatformMoveData(situationMoveData.getMove());
            }
            PlatformStatus platformStatus = management.getAll().get(situationMoveData.getId());
            if (platformStatus != null) {
                platformStatus.setMoveData(situationMoveData.getMove());
            }



        }


    }

    public PlatformMoveData initMoveData(String[] split) {
        PlatformMoveData moveData = new PlatformMoveData();
        moveData.setLon(Double.valueOf(split[0]));
        moveData.setLat(Double.valueOf(split[1]));
        moveData.setAlt(Double.valueOf(split[2]));
        moveData.setHeading(Double.valueOf(split[3]));
        moveData.setPitch(Double.valueOf(split[4]));
        moveData.setRoll(Double.valueOf(split[5]));
        moveData.setSpeed(Double.valueOf(split[6]));
        moveData.setLife(Double.valueOf(split[7]));
        return moveData;
    }


}
