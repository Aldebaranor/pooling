package com.soul.pooling.task;

import com.alibaba.fastjson.JSON;
import com.soul.pooling.entity.Sensor;
import com.soul.pooling.entity.Weapon;
import com.soul.pooling.service.StatusManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Song
 * @Date 2022/8/9 14:24
 */
@Slf4j
@Component
public class RangeTask {

    @Autowired
    private StatusManagement management;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public static String SCENARIO_RANGE = "scenario:range:route";

    @Scheduled(fixedDelayString = "10000")
    public void execute() {
        try {
            int platformNum = 100;
            for (int i = 1; i <= platformNum; i++) {

                redisTemplate.opsForHash().put(SCENARIO_RANGE, JSON.toJSONString(i), JSON.toJSONString(platformRange(String.valueOf(i))));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String platformRange(String platformId) {

        float maxFireRangeAir = 0;
        float maxFireRangeLand = 0;
        float maxFireRangeSea = 0;
        float maxFireRangeUnderSea = 0;
        float maxDetectionRangeAir = 0;
        float maxDetectionRangeLand = 0;
        float maxDetectionRangeSea = 0;
        float maxDetectionRangeUnderSea = 0;

        List<Sensor> sensors = management.getSensorsByPlatform(platformId);
        for (Sensor sensor : sensors) {
            maxDetectionRangeAir = (maxDetectionRangeAir >
                    sensor.getDetectionAir()) ? maxDetectionRangeAir : sensor.getDetectionAir();
            maxDetectionRangeLand = (maxDetectionRangeLand >
                    sensor.getDetectionLand()) ? maxDetectionRangeLand : sensor.getDetectionLand();
            maxDetectionRangeSea = (maxDetectionRangeSea >
                    sensor.getDetectionSea()) ? maxDetectionRangeSea : sensor.getDetectionSea();
            maxDetectionRangeUnderSea = (maxDetectionRangeUnderSea >
                    sensor.getDetectionUnderSea()) ? maxDetectionRangeUnderSea : sensor.getDetectionUnderSea();
        }
        List<Weapon> weapons = management.getWeaponsByPlatform(platformId);
        for (Weapon weapon : weapons) {
            maxFireRangeAir = (maxFireRangeAir >
                    weapon.getFireAir()) ? maxFireRangeAir : weapon.getFireAir();
            maxFireRangeLand = (maxFireRangeLand >
                    weapon.getFireLand()) ? maxFireRangeLand : weapon.getFireLand();
            maxFireRangeSea = (maxFireRangeSea >
                    weapon.getFireSea()) ? maxFireRangeSea : weapon.getFireSea();
            maxFireRangeUnderSea = (maxFireRangeUnderSea >
                    weapon.getFireUnderSea()) ? maxFireRangeUnderSea : weapon.getFireUnderSea();
        }
        String range = String.format("%s_%s_%s_%s@%s_%s_%s_%s",
                String.valueOf(maxFireRangeAir), String.valueOf(maxFireRangeLand), String.valueOf(maxFireRangeSea), String.valueOf(maxFireRangeUnderSea),
                String.valueOf(maxDetectionRangeAir), String.valueOf(maxDetectionRangeLand), String.valueOf(maxDetectionRangeSea), String.valueOf(maxDetectionRangeUnderSea));
        return range;
    }


}
