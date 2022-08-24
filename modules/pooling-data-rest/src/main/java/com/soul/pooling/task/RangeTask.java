package com.soul.pooling.task;

import com.egova.redis.RedisUtils;
import com.soul.pooling.entity.Engage;
import com.soul.pooling.entity.Find;
import com.soul.pooling.entity.Platform;
import com.soul.pooling.service.*;
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
    private PlatformService platformService;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private WeaponService weaponService;

    @Autowired
    private FindService findService;

    @Autowired
    private EngageService engageService;

    public static String SCENARIO_RANGE = "scenario:txy:range";

    @Scheduled(fixedDelayString = "10000")
    public void execute() {
        List<Platform> platformList = platformService.getAll();
        for (Platform platform : platformList) {
            String platformId = platform.getId();
            RedisUtils.getService().opsForHash().put(SCENARIO_RANGE, platformId, platformRange(platformId));
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

        List<Engage> weapons = engageService.getByPlatformCode(platformId);
        for (Engage weapon : weapons) {
            //获取最大对太空火力范围
            maxFireRangeSpace = (maxFireRangeSpace >
                    weapon.getMaxFireRangeSpace()) ? maxFireRangeSpace : weapon.getMaxFireRangeSpace();
            //获取最大对空火力范围
            maxFireRangeAir = (maxFireRangeAir >
                    weapon.getMaxFireRangeAir()) ? maxFireRangeAir : weapon.getMaxFireRangeAir();
            //获取最大对海火力范围
            maxFireRangeSea = (maxFireRangeSea >
                    weapon.getMaxFireRangeSea()) ? maxFireRangeSea : weapon.getMaxFireRangeSea();
            //获取最大对陆火力范围
            maxFireRangeLand = (maxFireRangeLand >
                    weapon.getMaxFireRangeLand()) ? maxFireRangeLand : weapon.getMaxFireRangeLand();
            //获取最大对潜火力范围
            maxFireRangeUnderSea = (maxFireRangeUnderSea >
                    weapon.getMaxFireRangeUnderSea()) ? maxFireRangeUnderSea : weapon.getMaxFireRangeUnderSea();
        }

        List<Find> sensors = findService.getByPlatformCode(platformId);
        for (Find sensor : sensors) {
            //获取最大对太空探测范围
            maxDetectionRangeSpace = (maxDetectionRangeSpace >
                    sensor.getMaxDetectRangeSpace()) ? maxDetectionRangeSpace : sensor.getMaxDetectRangeSpace();
            //获取最大对空探测范围
            maxDetectionRangeAir = (maxDetectionRangeAir >
                    sensor.getMaxDetectRangeAir()) ? maxDetectionRangeAir : sensor.getMaxDetectRangeAir();
            //获取最大对海探测范围
            maxDetectionRangeSea = (maxDetectionRangeSea >
                    sensor.getMaxDetectRangeSea()) ? maxDetectionRangeSea : sensor.getMaxDetectRangeSea();
            //获取最大对陆探测范围
            maxDetectionRangeLand = (maxDetectionRangeLand >
                    sensor.getMaxDetectRangeLand()) ? maxDetectionRangeLand : sensor.getMaxDetectRangeLand();
            //获取最大对潜探测范围
            maxDetectionRangeUnderSea = (maxDetectionRangeUnderSea >
                    sensor.getMaxDetectRangeUnderSea()) ? maxDetectionRangeUnderSea : sensor.getMaxDetectRangeUnderSea();
        }

        String range = String.format("%s_%s_%s_%s_%s@%s_%s_%s_%s_%s",
                String.valueOf(maxFireRangeSpace), String.valueOf(maxFireRangeAir), String.valueOf(maxFireRangeSea), String.valueOf(maxFireRangeLand), String.valueOf(maxFireRangeUnderSea),
                String.valueOf(maxDetectionRangeSpace), String.valueOf(maxDetectionRangeAir), String.valueOf(maxDetectionRangeSea), String.valueOf(maxDetectionRangeLand), String.valueOf(maxDetectionRangeUnderSea));
        return range;
    }


}
