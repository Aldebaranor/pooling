package com.soul.pooling.controller.free;

import com.egova.web.annotation.Api;
import com.soul.pooling.entity.Engage;
import com.soul.pooling.entity.Find;
import com.soul.pooling.entity.Platform;
import com.soul.pooling.model.CommandAttack;
import com.soul.pooling.model.KillingChain;
import com.soul.pooling.model.Point;
import com.soul.pooling.service.EngageService;
import com.soul.pooling.service.FindService;
import com.soul.pooling.service.StatusManagement;
import com.soul.pooling.utils.DistanceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Song
 * @Date 2022/8/23 14:43
 */
@Slf4j
@RestController
@RequestMapping("/free/pooling/command")
@RequiredArgsConstructor
public class CommandController {

    @Autowired
    private StatusManagement management;

    @Autowired
    private FindService findService;

    @Autowired
    private EngageService engageService;


    /**
     * 智能调度
     * @param command
     * @return <目标,打击连></>
     */
    @Api
    @PostMapping(value = "/mission")
    public Map<String,KillingChain> getKillChain(@RequestBody CommandAttack command){
        return null;
    }

    /**
     * 资源筛选,根据目标过滤资源池
     * @param command
     * @return
     */
    @Api
    @PostMapping(value = "/resource")
    public KillingChain getTargetResource(@RequestBody CommandAttack command){

        return null;
    }

    @Api
    @GetMapping(value = "/weapon/{test}")
    public List<String> engage(@PathVariable String test) {
        List<String> list = new ArrayList<>();
        list.add(test);
        return list;
    }

//    @Api
//    @PostMapping(value = "/sensor/test")
//    public List<String> mission(@RequestBody Mission mission) {
//        List<String> list = new ArrayList<>();
//        Map<String, Platform> platformPool = management.getPlatformPool();
//
//        double distance = Double.MAX_VALUE;
//        String firstSensor = "";
//
//        for (int i = 0; i < mission.targets.size(); i++) {
//            double targetLon = mission.targets.get(i).getLon();
//            double targetLat = mission.targets.get(i).getLat();
//            double targetAlt = mission.targets.get(i).getAlt();
//
//            for (Map.Entry<String, Platform> entry : platformPool.entrySet()) {
//                Platform platform = entry.getValue();
//                //经纬度算距离
//                double platformLon = platform.getPlatformMoveData().getLon();
//                double platformLat = platform.getPlatformMoveData().getLat();
//                double s = DistanceUtils.getDistance(platformLon, platformLat, targetLon, targetLat);
//
//                //是否在探测距离内
//                //TODO:判断高度
//                List<Float> detectDistance = platformFindRange(platform.getId());
//
//                if ((detectDistance.get(mission.missionArea) > s) && (distance > s)) {
//                    firstSensor = entry.getValue().getId();
//                }
//            }
//            list.add(firstSensor);
//        }
//        return list;
//    }

//    public List<Float> platformFindRange(String platformId) {
//        List<Float> list = new ArrayList<>();
//        float maxDetectionRangeSpace = 0;
//        float maxDetectionRangeAir = 0;
//        float maxDetectionRangeSea = 0;
//        float maxDetectionRangeLand = 0;
//        float maxDetectionRangeUnderSea = 0;
//
//        List<Find> sensors = findService.getByPlatformCode(platformId);
//        for (Find sensor : sensors) {
//            //获取最大对太空探测范围
//            maxDetectionRangeSpace = (maxDetectionRangeSpace >
//                    sensor.getDetectionSpace()) ? maxDetectionRangeSpace : sensor.getDetectionSpace();
//            //获取最大对空探测范围
//            maxDetectionRangeAir = (maxDetectionRangeAir >
//                    sensor.getDetectionAir()) ? maxDetectionRangeAir : sensor.getDetectionAir();
//            //获取最大对海探测范围
//            maxDetectionRangeSea = (maxDetectionRangeSea >
//                    sensor.getDetectionSea()) ? maxDetectionRangeSea : sensor.getDetectionSea();
//            //获取最大对陆探测范围
//            maxDetectionRangeLand = (maxDetectionRangeLand >
//                    sensor.getDetectionLand()) ? maxDetectionRangeLand : sensor.getDetectionLand();
//            //获取最大对潜探测范围
//            maxDetectionRangeUnderSea = (maxDetectionRangeUnderSea >
//                    sensor.getDetectionUnderSea()) ? maxDetectionRangeUnderSea : sensor.getDetectionUnderSea();
//        }
//        list.add(maxDetectionRangeSpace);
//        list.add(maxDetectionRangeAir);
//        list.add(maxDetectionRangeSea);
//        list.add(maxDetectionRangeLand);
//        list.add(maxDetectionRangeUnderSea);
//        return list;
//    }
//

//    public List<Float> platformEngageRange(String platformId) {
//        List<Float> list = new ArrayList<>();
//        float maxFireRangeSpace = 0;
//        float maxFireRangeAir = 0;
//        float maxFireRangeSea = 0;
//        float maxFireRangeLand = 0;
//        float maxFireRangeUnderSea = 0;
//
//        List<Engage> weapons = engageService.getByPlatformCode(platformId);
//        for (Engage weapon : weapons) {
//            //获取最大对太空火力范围
//            maxFireRangeSpace = (maxFireRangeSpace >
//                    weapon.getFireSpace()) ? maxFireRangeSpace : weapon.getFireSpace();
//            //获取最大对空火力范围
//            maxFireRangeAir = (maxFireRangeAir >
//                    weapon.getFireAir()) ? maxFireRangeAir : weapon.getFireAir();
//            //获取最大对海火力范围
//            maxFireRangeSea = (maxFireRangeSea >
//                    weapon.getFireSea()) ? maxFireRangeSea : weapon.getFireSea();
//            //获取最大对陆火力范围
//            maxFireRangeLand = (maxFireRangeLand >
//                    weapon.getFireLand()) ? maxFireRangeLand : weapon.getFireLand();
//            //获取最大对潜火力范围
//            maxFireRangeUnderSea = (maxFireRangeUnderSea >
//                    weapon.getFireUnderSea()) ? maxFireRangeUnderSea : weapon.getFireUnderSea();
//        }
//        list.add(maxFireRangeSpace);
//        list.add(maxFireRangeAir);
//        list.add(maxFireRangeSea);
//        list.add(maxFireRangeLand);
//        list.add(maxFireRangeUnderSea);
//        return list;
//    }



}
