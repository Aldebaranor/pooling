package com.soul.pooling.service.impl;


import com.soul.pooling.entity.Find;
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

import java.util.ArrayList;
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


        if (command.getType() == 25) {
            return getSquid();
        } else if (command.getType() == CommandType.SEARCH.getValue()) {
            return getSearchResource();
        }
        CommandType type = getCommandTpye(command);

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
        List<ResourceModel> find = poolingService.findToList(management.getFindPool(null)).stream().filter(q -> q.getPlatformName().contains("-S")).collect(Collectors.toList());
        List<ResourceModel> fix = poolingService.fixToList(management.getFixPool(CommandType.ATTACK_UNDERSEA).stream().filter(q -> q.getPlatformName().contains("-S")).collect(Collectors.toList()));
        List<ResourceModel> track = poolingService.trackToList(management.getTrackPool(null).stream().filter(q -> q.getPlatformName().contains("-S")).collect(Collectors.toList()));
        List<ResourceModel> target = poolingService.targetToList(management.getTargetPool(null));
        List<ResourceModel> engage = poolingService.engageToList(management.getEngagePool(CommandType.ATTACK_UNDERSEA)).stream().filter(q -> q.getName().contains("灭雷炸弹")).collect(Collectors.toList());
        List<ResourceModel> asses = poolingService.assesToList(management.getAssesPool(null).stream().filter(q -> q.getPlatformName().contains("-S")).collect(Collectors.toList()));

        killingChain.setFind(find);
        killingChain.setFix(fix);
        killingChain.setTrack(track);
        killingChain.setTarget(target);
        killingChain.setEngage(engage);
        killingChain.setAsses(asses);


        return killingChain;
    }

    @Override
    public KillingChain getSearchResource() {
        KillingChain killingChain = new KillingChain();
        List<ResourceModel> find = poolingService.findToList(management.getFindPool(null).stream().filter(q -> management.getPlatformPool().get(q.getPlatformCode()).getBeMineSweep().equals(true)).collect(Collectors.toList()));

        killingChain.setFind(find);
        killingChain.setFix(null);
        killingChain.setTrack(null);
        killingChain.setTarget(null);
        killingChain.setEngage(null);
        killingChain.setAsses(null);
        return killingChain;
    }

    @Override
    public List<KillingChain> getAir(CommandAttack command) {
        //0确定那个方面战，对空按照下面的逻辑
        //1.根据每个目标当前位置A与 航向，航速外推200s得到B
        //2.筛选所有到AB最小距离小于其探测半径的 发现，定位，跟踪资源
        //2.筛选所有到AB最小距离小于其火力半径半径的武器资源
        //3.筛选所有到AB最小距离小于其探测半径的评估资源

        List<KillingChain> list = new ArrayList<>();
        int time = 200;

        for (TargetData targetData : command.getTargets()) {
            KillingChain killingChain = new KillingChain();
            killingChain.setTargetId(targetData.getInstId());

            GeometryUtils.Point start = new GeometryUtils.Point();
            GeometryUtils.Point end = new GeometryUtils.Point();
            start.setX(targetData.getMoveDetect().getLon());
            start.setY(targetData.getMoveDetect().getLat());

            Double heading = targetData.getMoveDetect().getHeading();
            Double speed = targetData.getMoveDetect().getSpeed();
            Double distance = speed * time;
            //得到B点

            end.setX(GeometryUtils.getTargetPoint(start.getX(), start.getY(), heading, distance).getX());
            end.setY(GeometryUtils.getTargetPoint(start.getX(), start.getY(), heading, distance).getY());

//            List<ResourceModel> find = poolingService.findToList(management.getFindPool(CommandType.ATTACK_AIR).stream().filter(p -> p.getMaxDetectRangeAir() > GeometryUtils.minDistanceFromPointToLine(start, end, moveData2Point(management.getPlatformPool().get(p.getPlatformCode()).getPlatformMoveData()))).collect(Collectors.toList()));
            List<ResourceModel> find = getFind(start, end, command);
            killingChain.setFind(find);

//            List<ResourceModel> fix = poolingService.fixToList(management.getFixPool(CommandType.ATTACK_AIR).stream().filter(p -> p.getMaxDetectRangeAir() > GeometryUtils.minDistanceFromPointToLine(start, end, moveData2Point(management.getPlatformPool().get(p.getPlatformCode()).getPlatformMoveData()))).collect(Collectors.toList()));
//            killingChain.setFix(fix);
//
//            List<ResourceModel> track = poolingService.trackToList(management.getTrackPool(CommandType.ATTACK_AIR).stream().filter(p -> p.getMaxDetectRangeAir() > GeometryUtils.minDistanceFromPointToLine(start, end, moveData2Point(management.getPlatformPool().get(p.getPlatformCode()).getPlatformMoveData()))).collect(Collectors.toList()));
//            killingChain.setTrack(track);
//
//            List<ResourceModel> target = poolingService.targetToList(management.getTargetPool(CommandType.ATTACK_AIR));
//            killingChain.setTarget(target);
//
//            List<ResourceModel> engage = poolingService.engageToList(management.getEngagePool(CommandType.ATTACK_AIR).stream().filter(p -> p.getMaxFireRangeAir() > GeometryUtils.minDistanceFromPointToLine(start, end, moveData2Point(management.getPlatformPool().get(p.getPlatformCode()).getPlatformMoveData()))).collect(Collectors.toList()));
//            killingChain.setEngage(engage);
//
//            List<ResourceModel> asses = poolingService.assesToList(management.getAssesPool(CommandType.ATTACK_AIR).stream().filter(p -> p.getMaxDetectRangeAir() > GeometryUtils.minDistanceFromPointToLine(start, end, moveData2Point(management.getPlatformPool().get(p.getPlatformCode()).getPlatformMoveData()))).collect(Collectors.toList()));
//            killingChain.setAsses(asses);

            list.add(killingChain);
        }

        return list;
    }

    @Override
    public GeometryUtils.Point moveData2Point(PlatformMoveData moveData) {
        GeometryUtils.Point point = new GeometryUtils.Point();
        point.setX(moveData.getLon());
        point.setY(moveData.getLat());
        return point;
    }

    public CommandType getCommandTpye(CommandAttack command) {
        CommandType type = CommandType.ATTACK;
        if (command.getType() == CommandType.ATTACK_AIR.getValue()) {
            type = CommandType.ATTACK_AIR;
        } else if (command.getType() == CommandType.ATTACK_SEA.getValue()) {
            type = CommandType.ATTACK_SEA;
        } else if (command.getType() == CommandType.ATTACK_LAND.getValue()) {
            type = CommandType.ATTACK_LAND;
        } else if (command.getType() == CommandType.ATTACK_UNDERSEA.getValue()) {
            type = CommandType.ATTACK_UNDERSEA;
        } else {
            log.info("commandType 错误，取值不在21，22，23，24");
            return null;
        }
        return type;
    }


    public List<ResourceModel> getFind(GeometryUtils.Point start, GeometryUtils.Point end, CommandAttack command) {
        List<Find> list = new ArrayList<>();
        List<Find> finds = management.getFindPool(getCommandTpye(command));

        for (Find find : finds) {
            PlatformMoveData moveData = management.getPlatformPool().get(find.getPlatformCode()).getPlatformMoveData();
            if (moveData != null) {
                GeometryUtils.Point point = moveData2Point(moveData);

                double distance = GeometryUtils.minDistanceFromPointToLine(start, end, point) / 1000;
                if (find.getMaxDetectRangeAir() > distance) {
                    list.add(find);
                }
            }
        }

        return poolingService.findToList(list);
    }
}
