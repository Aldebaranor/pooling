package com.soul.pooling.service.impl;


import com.soul.pooling.entity.enums.CommandType;
import com.soul.pooling.model.*;
import com.soul.pooling.service.CommandService;
import com.soul.pooling.service.PoolingManagement;
import com.soul.pooling.service.PoolingService;
import com.soul.pooling.utils.GeometryUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Song
 * @Date 2022/8/25 9:51
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommandServiceImpl implements CommandService {

    @Autowired
    private PoolingManagement management;

    @Autowired
    private PoolingService poolingService;

    @Override
    public KillingChain getTargetResource(CommandAttack command) {

        CommandType type = CommandType.ATTACK;
        if (command.getType() == CommandType.ATTACK_AIR.getValue()) {
            type = CommandType.ATTACK_AIR;
        } else if (command.getType() == CommandType.ATTACK_SEA.getValue()) {
            type = CommandType.ATTACK_SEA;
        } else if (command.getType() == CommandType.ATTACK_LAND.getValue()) {
            type = CommandType.ATTACK_LAND;
        } else if (command.getType() == CommandType.ATTACK_UNDERSEA.getValue()) {
            type = CommandType.ATTACK_UNDERSEA;
        } else if (command.getType() == 25) {
            return getSquid();
        } else {
            log.info("commandType 错误，取值不在21，22，23，24，25");
            return null;
        }
        //TODO 反水雷

        KillingChain killingChain = new KillingChain();
        List<ResourceModel> find = poolingService.findToList(management.getFindPool(type));
        List<ResourceModel> fix = poolingService.fixToList(management.getFixPool(type));
        List<ResourceModel> track = poolingService.trackToList(management.getTrackPool(type));
        List<ResourceModel> target = poolingService.targetToList(management.getTargetPool(type));
        List<ResourceModel> engage = poolingService.engageToList(management.getEngagePool(type));
        List<ResourceModel> asses = poolingService.assesToList(management.getAssesPool(type));

        killingChain.setFind(find);
        killingChain.setFix(fix);
        killingChain.setTrack(track);
        killingChain.setTarget(target);
        killingChain.setEngage(engage);
        killingChain.setAsses(asses);


        return killingChain;
    }


    @Override
    public KillingChain getSquid() {
        KillingChain killingChain = new KillingChain();
        List<ResourceModel> find = poolingService.findToList(management.getFindPool(null)).stream().filter(q -> q.getPlatformName().contains("U")).collect(Collectors.toList());
        List<ResourceModel> fix = poolingService.fixToList(management.getFixPool(CommandType.ATTACK_UNDERSEA).stream().filter(q -> q.getPlatformName().contains("U")).collect(Collectors.toList()));
        List<ResourceModel> track = poolingService.trackToList(management.getTrackPool(null).stream().filter(q -> q.getPlatformName().contains("U")).collect(Collectors.toList()));
        List<ResourceModel> target = poolingService.targetToList(management.getTargetPool(null));
        List<ResourceModel> engage = poolingService.engageToList(management.getEngagePool(CommandType.ATTACK_UNDERSEA)).stream().filter(q -> q.getName().contains("灭雷炸弹")).collect(Collectors.toList());
        List<ResourceModel> asses = poolingService.assesToList(management.getAssesPool(null).stream().filter(q -> q.getPlatformName().contains("U")).collect(Collectors.toList()));

        killingChain.setFind(find);
        killingChain.setFix(fix);
        killingChain.setTrack(track);
        killingChain.setTarget(target);
        killingChain.setEngage(engage);
        killingChain.setAsses(asses);


        return killingChain;
    }

    @Override
    public KillingChain getAir(CommandAttack command) {
        //0确定那个方面战，对空按照下面的逻辑
        //1.根据每个目标当前位置A与 航向，航速外推200s得到B
        //2.筛选所有到AB最小距离小于其探测半径的 发现，定位，跟踪资源
        //2.筛选所有到AB最小距离小于其火力半径半径的武器资源
        //3.筛选所有到AB最小距离小于其探测半径的评估资源

        KillingChain killingChain = new KillingChain();
        int time = 200;

        for (TargetData targetData : command.getTargets()) {
            Point start = new Point();
            Point end = new Point();
            start.setLon(targetData.getMoveDetect().getLon());
            start.setLat(targetData.getMoveDetect().getLat());

            Double heading = targetData.getMoveDetect().getHeading();
            Double speed = targetData.getMoveDetect().getSpeed();
            Double distance = speed * time;

            end.setAlt(targetData.getMoveDetect().getAlt());
            end.setLat(GeometryUtils.getTargetPoint(start.getLon(), start.getLat(), heading, distance).getX());
            end.setLat(GeometryUtils.getTargetPoint(start.getLon(), start.getLat(), heading, distance).getX());

        }

        return killingChain;
    }
}
