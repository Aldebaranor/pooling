package com.soul.pooling.service.impl;


import com.soul.pooling.entity.*;
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

        //0确定那个方面战，对空按照下面的逻辑
        //1.根据每个目标当前位置A与 航向，航速外推200s得到B
        //2.筛选所有到AB最小距离小于其探测半径的 发现，定位，跟踪资源
        //2.筛选所有到AB最小距离小于其火力半径半径的武器资源
        //3.筛选所有到AB最小距离小于其探测半径的评估资源

        if (command.getType() == 25) {
            return getSquid();
        } else if (command.getType() == CommandType.SEARCH.getValue()) {
            return getSearchResource();
        }
        CommandType type = getCommandTpye(command);

        KillingChain killingChain = new KillingChain();
        List<ResourceModel> find = new ArrayList<>();
        List<ResourceModel> fix = new ArrayList<>();
        List<ResourceModel> track = new ArrayList<>();
        List<ResourceModel> target = new ArrayList<>();
        List<ResourceModel> engage = new ArrayList<>();
        List<ResourceModel> asses = new ArrayList<>();

        if (command.getTargets().size() == 0) {

            find = poolingService.findToList(management.getFindPool(type));
            fix = poolingService.fixToList(management.getFixPool(type));
            track = poolingService.trackToList(management.getTrackPool(type));
            target = poolingService.targetToList(management.getTargetPool(type));
            engage = poolingService.engageToList(management.getEngagePool(type));
            asses = poolingService.assesToList(management.getAssesPool(type));

            killingChain.setFind(find);
            killingChain.setFix(fix);
            killingChain.setTrack(track);
            killingChain.setTarget(target);
            killingChain.setEngage(engage);
            killingChain.setAsses(asses);

        } else {
            int time = 200;
            for (TargetData targetData : command.getTargets()) {
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

                for (ResourceModel model : getFind(start, end, command)) {
                    if (!find.contains(model)) {
                        find.add(model);
                    }
                }
                for (ResourceModel model : getFix(start, end, command)) {
                    if (!fix.contains(model)) {
                        fix.add(model);
                    }
                }
                for (ResourceModel model : getTrack(start, end, command)) {
                    if (!track.contains(model)) {
                        track.add(model);
                    }
                }
                for (ResourceModel model : getTarget(start, end, command)) {
                    if (!target.contains(model)) {
                        target.add(model);
                    }
                }
                for (ResourceModel model : getEngage(start, end, command)) {
                    if (!engage.contains(model)) {
                        engage.add(model);
                    }
                }
                for (ResourceModel model : getAssses(start, end, command)) {
                    if (!asses.contains(model)) {
                        asses.add(model);
                    }
                }

                killingChain.setFind(find);
                killingChain.setFix(fix);
                killingChain.setTrack(track);
                killingChain.setTarget(target);
                killingChain.setEngage(engage);
                killingChain.setAsses(asses);

            }
        }

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
    public List<KillingChain> getKillChain(CommandAttack command) {


        /**
         * 智能调度
         *
         * @param command
         * @return <目标,打击连></>
         * //0确定那个方面战，对空按照下面的逻辑
         * //1.根据每个目标当前位置A与 航向，航速外推200s得到B
         * //2.筛选出所有武器到AB的最小距离小于武器射程
         * //3.按武器所在位置距离A的距离排序，排序得到前两个武器
         * //4.在前两个中根据先余弹量后距离排序选出一个的武器进行打击，根据命中概率计算需要几发弹
         * //5.选择这个武器所属平台的传感器开机作为跟踪资源
         * //6.选择离A点最近的find资源
         * //7.选择离A点最近的fix track资源作为传感器+执行打击武器的track资源
         * //8。选择离B点最近的asses
         */

        List<KillingChain> list = new ArrayList<>();
        int time = 200;
        List<ResourceModel> find = new ArrayList<>();
        List<ResourceModel> fix = new ArrayList<>();
        List<ResourceModel> track = new ArrayList<>();
        List<ResourceModel> target = new ArrayList<>();
        List<ResourceModel> engage = new ArrayList<>();
        List<ResourceModel> asses = new ArrayList<>();

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


            for (ResourceModel model : getFind(start, end, command)) {
                if (!find.contains(model)) {
                    find.add(model);
                }
            }
            for (ResourceModel model : getFix(start, end, command)) {
                if (!fix.contains(model)) {
                    fix.add(model);
                }
            }
            for (ResourceModel model : getTrack(start, end, command)) {
                if (!track.contains(model)) {
                    track.add(model);
                }
            }
            for (ResourceModel model : getTarget(start, end, command)) {
                if (!target.contains(model)) {
                    target.add(model);
                }
            }
            for (ResourceModel model : getEngage(start, end, command)) {
                if (!engage.contains(model)) {
                    engage.add(model);
                }
            }
            for (ResourceModel model : getAssses(start, end, command)) {
                if (!asses.contains(model)) {
                    asses.add(model);
                }
            }


            killingChain.setFind(find);
            killingChain.setFix(fix);
            killingChain.setTrack(track);
            killingChain.setTarget(target);
            killingChain.setEngage(engage);
            killingChain.setAsses(asses);


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

                double distance = GeometryUtils.minDistanceFromPointToLine(start, end, point);

                if (getCommandTpye(command).getValue() == 21 && (find.getMaxDetectRangeAir() * 1000 > distance)) {
                    list.add(find);
                } else if (getCommandTpye(command).getValue() == 22 && (find.getMaxDetectRangeSea() * 1000 > distance)) {
                    list.add(find);
                } else if (getCommandTpye(command).getValue() == 23 && (find.getMaxDetectRangeLand() * 1000 > distance)) {
                    list.add(find);
                } else if (getCommandTpye(command).getValue() == 24 && (find.getMaxDetectRangeUnderSea() * 1000 > distance)) {
                    list.add(find);
                }

            }
        }


        return poolingService.findToList(list);
    }

    public List<ResourceModel> getFix(GeometryUtils.Point start, GeometryUtils.Point end, CommandAttack command) {
        List<Fix> list = new ArrayList<>();
        List<Fix> fixes = management.getFixPool(getCommandTpye(command));

        for (Fix fix : fixes) {
            PlatformMoveData moveData = management.getPlatformPool().get(fix.getPlatformCode()).getPlatformMoveData();
            if (moveData != null) {
                GeometryUtils.Point point = moveData2Point(moveData);

                double distance = GeometryUtils.minDistanceFromPointToLine(start, end, point);
                fix.set("distance", distance);
                if (getCommandTpye(command).getValue() == 21 && (fix.getMaxDetectRangeAir() * 1000 > distance)) {
                    list.add(fix);
                } else if (getCommandTpye(command).getValue() == 22 && (fix.getMaxDetectRangeSea() * 1000 > distance)) {
                    list.add(fix);
                } else if (getCommandTpye(command).getValue() == 23 && (fix.getMaxDetectRangeLand() * 1000 > distance)) {
                    list.add(fix);
                } else if (getCommandTpye(command).getValue() == 24 && (fix.getMaxDetectRangeUnderSea() * 1000 > distance)) {
                    list.add(fix);
                }
            }
        }

//        list.sort();
        return poolingService.fixToList(list);
    }

    public List<ResourceModel> getTarget(GeometryUtils.Point start, GeometryUtils.Point end, CommandAttack command) {
        List<Target> list = new ArrayList<>();
        List<Target> targets = management.getTargetPool(getCommandTpye(command));

        for (Target target : targets) {
            PlatformMoveData moveData = management.getPlatformPool().get(target.getPlatformCode()).getPlatformMoveData();
            if (moveData != null) {
                list.add(target);
            }
        }

        return poolingService.targetToList(list);
    }

    public List<ResourceModel> getTrack(GeometryUtils.Point start, GeometryUtils.Point end, CommandAttack command) {
        List<Track> list = new ArrayList<>();
        List<Track> tracks = management.getTrackPool(getCommandTpye(command));

        for (Track track : tracks) {
            PlatformMoveData moveData = management.getPlatformPool().get(track.getPlatformCode()).getPlatformMoveData();
            if (moveData != null) {
                GeometryUtils.Point point = moveData2Point(moveData);

                double distance = GeometryUtils.minDistanceFromPointToLine(start, end, point);
                if (getCommandTpye(command).getValue() == 21 && (track.getMaxDetectRangeAir() * 1000 > distance)) {
                    list.add(track);
                } else if (getCommandTpye(command).getValue() == 22 && (track.getMaxDetectRangeSea() * 1000 > distance)) {
                    list.add(track);
                } else if (getCommandTpye(command).getValue() == 23 && (track.getMaxDetectRangeLand() * 1000 > distance)) {
                    list.add(track);
                } else if (getCommandTpye(command).getValue() == 24 && (track.getMaxDetectRangeUnderSea() * 1000 > distance)) {
                    list.add(track);
                }
            }
        }

        return poolingService.trackToList(list);
    }

    public List<ResourceModel> getAssses(GeometryUtils.Point start, GeometryUtils.Point end, CommandAttack command) {
        List<Asses> list = new ArrayList<>();
        List<Asses> assesList = management.getAssesPool(getCommandTpye(command));

        for (Asses asses : assesList) {
            PlatformMoveData moveData = management.getPlatformPool().get(asses.getPlatformCode()).getPlatformMoveData();
            if (moveData != null) {
                GeometryUtils.Point point = moveData2Point(moveData);

                double distance = GeometryUtils.minDistanceFromPointToLine(start, end, point);
                if (getCommandTpye(command).getValue() == 21 && (asses.getMaxDetectRangeAir() * 1000 > distance)) {
                    list.add(asses);
                } else if (getCommandTpye(command).getValue() == 22 && (asses.getMaxDetectRangeSea() * 1000 > distance)) {
                    list.add(asses);
                } else if (getCommandTpye(command).getValue() == 23 && (asses.getMaxDetectRangeLand() * 1000 > distance)) {
                    list.add(asses);
                } else if (getCommandTpye(command).getValue() == 24 && (asses.getMaxDetectRangeUnderSea() * 1000 > distance)) {
                    list.add(asses);
                }
            }
        }

        return poolingService.assesToList(list);
    }

    public List<ResourceModel> getEngage(GeometryUtils.Point start, GeometryUtils.Point end, CommandAttack command) {
        List<Engage> list = new ArrayList<>();
        List<Engage> engages = management.getEngagePool(getCommandTpye(command));

        for (Engage engage : engages) {
            PlatformMoveData moveData = management.getPlatformPool().get(engage.getPlatformCode()).getPlatformMoveData();
            if (moveData != null) {
                GeometryUtils.Point point = moveData2Point(moveData);

                double distance = GeometryUtils.minDistanceFromPointToLine(start, end, point);
                if (getCommandTpye(command).getValue() == 21 && (engage.getMaxFireRangeAir() * 1000 > distance)) {
                    list.add(engage);
                } else if (getCommandTpye(command).getValue() == 22 && (engage.getMaxFireRangeSea() * 1000 > distance)) {
                    list.add(engage);
                } else if (getCommandTpye(command).getValue() == 23 && (engage.getMaxFireRangeLand() * 1000 > distance)) {
                    list.add(engage);
                } else if (getCommandTpye(command).getValue() == 24 && (engage.getMaxFireRangeUnderSea() * 1000 > distance)) {
                    list.add(engage);
                }
            }
        }

        return poolingService.engageToList(list);
    }
}
