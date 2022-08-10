package com.soul.pooling.task;

import com.alibaba.fastjson.JSON;
import com.egova.redis.RedisUtils;
import com.soul.pooling.entity.Sensor;
import com.soul.pooling.entity.Weapon;
import com.soul.pooling.service.StatusManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public static String SCENARIO_RANGE = "scenario:txy:range";

    @Scheduled(fixedDelayString = "10000")
    public void execute() {
        try {
            int platformNum = 100;
            for (int i = 1; i <= platformNum; i++) {

                RedisUtils.getService().opsForHash().put(SCENARIO_RANGE, JSON.toJSONString(i), JSON.toJSONString(platformRange(String.valueOf(i))));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String platformRange(String platformId) {

        float maxFireRangeSpace = 0;
        float maxFireRangeAir = 0;
        float maxFireRangeSea = 0;
        float maxFireRangeLand = 0;
        float maxFireRangeUnderSea = 0;

        float maxDetectionRangeSpace = 0;
        float maxDetectionRangeAir = 0;
        float maxDetectionRangeSea = 0;
        float maxDetectionRangeLand = 0;
        float maxDetectionRangeUnderSea = 0;

        List<Weapon> weapons = management.getWeaponsByPlatform(platformId);
        for (Weapon weapon : weapons) {
            //获取最大对太空火力范围
            maxFireRangeSpace = (maxFireRangeSpace >
                    weapon.getFireSpace()) ? maxFireRangeSpace : weapon.getFireSpace();
            //获取最大对空火力范围
            maxFireRangeAir = (maxFireRangeAir >
                    weapon.getFireAir()) ? maxFireRangeAir : weapon.getFireAir();
            //获取最大对陆火力范围
            maxFireRangeLand = (maxFireRangeLand >
                    weapon.getFireLand()) ? maxFireRangeLand : weapon.getFireLand();
            //获取最大对海火力范围
            maxFireRangeSea = (maxFireRangeSea >
                    weapon.getFireSea()) ? maxFireRangeSea : weapon.getFireSea();
            //获取最大对潜火力范围
            maxFireRangeUnderSea = (maxFireRangeUnderSea >
                    weapon.getFireUnderSea()) ? maxFireRangeUnderSea : weapon.getFireUnderSea();
        }

        List<Sensor> sensors = management.getSensorsByPlatform(platformId);
        for (Sensor sensor : sensors) {
            //获取最大对太空探测范围
            maxDetectionRangeSpace = (maxDetectionRangeSpace >
                    sensor.getDetectionSpace()) ? maxDetectionRangeSpace : sensor.getDetectionSpace();
            //获取最大对空探测范围
            maxDetectionRangeAir = (maxDetectionRangeAir >
                    sensor.getDetectionAir()) ? maxDetectionRangeAir : sensor.getDetectionAir();
            //获取最大对陆探测范围
            maxDetectionRangeLand = (maxDetectionRangeLand >
                    sensor.getDetectionLand()) ? maxDetectionRangeLand : sensor.getDetectionLand();
            //获取最大对海探测范围
            maxDetectionRangeSea = (maxDetectionRangeSea >
                    sensor.getDetectionSea()) ? maxDetectionRangeSea : sensor.getDetectionSea();
            //获取最大对潜探测范围
            maxDetectionRangeUnderSea = (maxDetectionRangeUnderSea >
                    sensor.getDetectionUnderSea()) ? maxDetectionRangeUnderSea : sensor.getDetectionUnderSea();
        }

        String range = String.format("%s_%s_%s_%s_%s@%s_%s_%s_%s_%s",
                String.valueOf(maxFireRangeSpace), String.valueOf(maxFireRangeAir), String.valueOf(maxFireRangeSea), String.valueOf(maxFireRangeLand), String.valueOf(maxFireRangeUnderSea),
                String.valueOf(maxDetectionRangeSpace), String.valueOf(maxDetectionRangeAir), String.valueOf(maxDetectionRangeSea), String.valueOf(maxDetectionRangeLand), String.valueOf(maxDetectionRangeUnderSea));
        return range;
    }


}
