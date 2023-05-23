package com.soul.pooling.service.impl;


import com.soul.pooling.config.MetaConfig;
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

import java.util.*;
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

    @Autowired
    private MetaConfig metaConfig;

    @Override
    public KillingChain getTargetResources(Command command) {
        if (command.getType() == 25) {
            return getSquid();
        } else if (command.getType() == CommandType.SEARCH.getValue()) {
            return getSearchResource();
        }
        CommandType type = getCommandType(command);

        KillingChain killingChain = new KillingChain();
        List<ResourceModel> find = new ArrayList<>();
        List<ResourceModel> fix = new ArrayList<>();
        List<ResourceModel> track = new ArrayList<>();
        List<ResourceModel> target = new ArrayList<>();
        List<ResourceModel> engage = new ArrayList<>();
        List<ResourceModel> asses = new ArrayList<>();

        find = poolingService.findToList(management.getFindPool(type));
        fix = poolingService.fixToList(management.getFixPool(type));
        track = poolingService.trackToList(management.getTrackPool(type));
        engage = poolingService.engageToList(management.getEngagePool(type));
        killingChain.setEngage(engage);
        for (ResourceModel model : getTarget(engage)) {
            if (!target.contains(model)) {
                target.add(model);
            }
        }
        asses = poolingService.assesToList(management.getAssesPool(type));

        killingChain.setFind(find);
        killingChain.setFix(fix);
        killingChain.setTrack(track);
        killingChain.setTarget(target);
        killingChain.setAsses(asses);
        return killingChain;
    }

    @Override
    public KillingChain getTargetResource(Command command) {

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
        CommandType type = getCommandType(command);

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
            engage = poolingService.engageToList(management.getEngagePool(type));
            killingChain.setEngage(engage);
            for (ResourceModel model : getTarget(engage)) {
                if (!target.contains(model)) {
                    target.add(model);
                }
            }
            asses = poolingService.assesToList(management.getAssesPool(type));

            killingChain.setFind(find);
            killingChain.setFix(fix);
            killingChain.setTrack(track);
            killingChain.setTarget(target);
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
                for (ResourceModel model : getEngage(start, end, command)) {
                    if (!engage.contains(model)) {
                        engage.add(model);
                    }
                }
                killingChain.setEngage(engage);
                for (ResourceModel model : getTarget(engage)) {
                    if (!target.contains(model)) {
                        target.add(model);
                    }
                }
                for (ResourceModel model : getAsses(start, end, command)) {
                    if (!asses.contains(model)) {
                        asses.add(model);
                    }
                }

                killingChain.setFind(find);
                killingChain.setFix(fix);
                killingChain.setTrack(track);
                killingChain.setTarget(target);
                killingChain.setAsses(asses);
                killingChain.setCommandType(command.getType());

            }
        }

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
    public List<KillingChain> getKillChain(Command command) {

        if (command.getType() == 25) {
            return getMineSweep(command);
        }

        /**
         * 智能调度
         *
         * @param command
         * @return <目标,打击链></>
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
        int time = 240;
        List<ResourceModel> find = new ArrayList<>();
        List<ResourceModel> fix = new ArrayList<>();
        List<ResourceModel> track = new ArrayList<>();
        List<ResourceModel> target = new ArrayList<>();
        List<ResourceModel> engage = new ArrayList<>();
        List<ResourceModel> asses = new ArrayList<>();
        List<ResourceModel> tempEngages = new ArrayList<>();


        for (TargetData targetData : command.getTargets()) {

            List<ResourceModel> killChainFind = new ArrayList<>();
            List<ResourceModel> killChainFix = new ArrayList<>();
            List<ResourceModel> killChainTrack = new ArrayList<>();
            List<ResourceModel> killChainTarget = new ArrayList<>();
            List<ResourceModel> killChainEngage = new ArrayList<>();
            List<ResourceModel> killChainAsses = new ArrayList<>();

            KillingChain killingChain = new KillingChain();

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

//            选择离A点最近的find资源
            for (ResourceModel model : getFind(start, end, command)) {
                //排除J-15
                if (!model.getPlatformName().contains("J-15")) {
                    if (!find.contains(model)) {
                        find.add(model);
                    }
                }
            }
            if (find.size() != 0) {
                killChainFind.add(find.get(0));
            } else {
                //为空就安排一个卫星
                Find star = management.getFindById("135");
                ResourceModel model = new ResourceModel();
                model.setId(star.getId());
                model.setName(star.getName());
                model.setDeviceCode(star.getDeviceCode());
                model.setType(star.getType());
                model.setPlatformCode(star.getPlatformCode());
                model.setPlatformName(star.getPlatformName());
                model.setStatus(star.getStatus());
                model.setDeviceType(star.getDeviceType());
                killChainFind.add(model);
            }

//            选择离A点最近的fix
            for (ResourceModel model : getFix(start, end, command)) {
                //排除J-15
                if (!model.getPlatformName().contains("J-15")) {
                    if (!fix.contains(model)) {
                        fix.add(model);
                    }
                }
            }
            if (fix.size() != 0) {
                killChainFix.add(fix.get(0));
            } else {
                Fix star = management.getFixById("135");
                ResourceModel model = new ResourceModel();
                model.setId(star.getId());
                model.setName(star.getName());
                model.setDeviceCode(star.getDeviceCode());
                model.setType(star.getType());
                model.setPlatformCode(star.getPlatformCode());
                model.setPlatformName(star.getPlatformName());
                model.setStatus(star.getStatus());
                model.setDeviceType(star.getDeviceType());
                killChainFix.add(model);
            }
//            选择离A点最近的track
            for (ResourceModel model : getTrack(start, end, command)) {
                //排除J-15
                if (!model.getPlatformName().contains("J-15")) {
                    if (!track.contains(model)) {
                        track.add(model);
                    }
                }
            }
            if (track.size() != 0) {
                killChainTrack.add(track.get(0));
            } else {
                Track star = management.getTrackById("135");
                ResourceModel model = new ResourceModel();
                model.setId(star.getId());
                model.setName(star.getName());
                model.setDeviceCode(star.getDeviceCode());
                model.setType(star.getType());
                model.setPlatformCode(star.getPlatformCode());
                model.setPlatformName(star.getPlatformName());
                model.setStatus(star.getStatus());
                model.setDeviceType(star.getDeviceType());
                killChainTrack.add(model);
            }


//            按武器所在位置距离A的距离排序，排序得到前两个武器
            for (ResourceModel model : getEngage(start, end, command)) {
                //排除J-15
                if (!model.getPlatformName().contains("J-15")) {
                    if (!engage.contains(model)) {
                        engage.add(model);
                    }
                }
            }

            for (ResourceModel model : engage) {
                ResourceModel tempEngage = new ResourceModel();
                tempEngage.setId(model.getId());
                tempEngage.setName(model.getName());
                tempEngage.setDeviceCode(model.getDeviceCode());
                tempEngage.setType(model.getType());
                tempEngage.setStatus(model.getStatus());
                tempEngage.setPlatformCode(model.getPlatformCode());
                tempEngage.setPlatformName(model.getPlatformName());
                tempEngage.setNum(model.getNum());
                if (!tempEngages.contains(tempEngage)) {
                    tempEngages.add(tempEngage);
                }
            }

//            在前两个中根据先余弹量后距离排序选出一个的武器进行打击，根据命中概率计算需要几发弹
//            选择这个武器所属平台的传感器开机作为跟踪资源
            if (tempEngages.size() != 0) {
                //对海对陆采用饱和攻击
                if (command.getType() == 22 || command.getType() == 23) {
                    int wpSize = tempEngages.size() > 5 ? 5 : tempEngages.size();
                    for (int i = 0; i < wpSize; i++) {
                        ResourceModel killEngage = new ResourceModel();
                        killEngage = setWeapon(killEngage, tempEngages, i);
                        killChainEngage.add(killEngage);
                        if (getTrackByEngage(killEngage, command) != null) {
                            killChainTrack.add(getTrackByEngage(killEngage, command));
                        }
                    }
                } else if (tempEngages.get(1).getNum() > tempEngages.get(0).getNum()) {
                    ResourceModel killEngage = new ResourceModel();
                    killEngage = setWeapon(killEngage, tempEngages, 1);
                    killChainEngage.add(killEngage);
                    if (getTrackByEngage(killEngage, command) != null) {
                        killChainTrack.add(getTrackByEngage(killEngage, command));
                    }
                } else {
                    ResourceModel killEngage = new ResourceModel();
                    killEngage = setWeapon(killEngage, tempEngages, 0);
                    killChainEngage.add(killEngage);
                    if (getTrackByEngage(killEngage, command) != null) {
                        killChainTrack.add(getTrackByEngage(killEngage, command));
                    }
                }
            }

            for (ResourceModel model : getTarget(killChainEngage)) {

                if (!target.contains(model)) {
                    target.add(model);
                }

            }
            if (target.size() != 0) {
                killChainTarget.add(target.get(0));
            }

//            选择离B点最近的asses
            for (ResourceModel model : getAsses(start, end, command)) {
                //排除J-15
                if (!model.getPlatformName().contains("J-15")) {
                    if (!asses.contains(model)) {
                        asses.add(model);
                    }
                }
            }
            if (asses.size() != 0) {
                killChainAsses.add(asses.get(0));
            } else {
                Asses star = management.getAssesById("135");
                ResourceModel model = new ResourceModel();
                model.setId(star.getId());
                model.setName(star.getName());
                model.setDeviceCode(star.getDeviceCode());
                model.setType(star.getType());
                model.setPlatformCode(star.getPlatformCode());
                model.setPlatformName(star.getPlatformName());
                model.setStatus(star.getStatus());
                model.setDeviceType(star.getDeviceType());
                killChainAsses.add(model);
            }


            killingChain.setFind(killChainFind);
            killingChain.setFix(killChainFix);
            killingChain.setTrack(killChainTrack);
            killingChain.setTarget(killChainTarget);
            killingChain.setEngage(killChainEngage);
            killingChain.setAsses(killChainAsses);

            killingChain.setTargetId(targetData.getInstId());
            killingChain.setTargetName(targetData.getName());
            killingChain.setCommandType(command.getType());

            list.add(killingChain);
        }


        return list;
    }

    @Override
    public List<KillingChain> getSearch(Command command) {
        /**
         * 获取搜索平台列表 List<Platform> UAV-S UUV-S
         * 根据多边形计算面积 S
         * 计算无人机UAV-S单位时间扫描面积s1 无人艇UUV-S单位时间扫描面积s2
         * 根据时间计算所需平台数量
         * 返回对应killingChain
         */
        List<KillingChain> list = new ArrayList<>();
        List<Find> uav = getUAV_S();
        List<Find> uuv = getUUV_S();


        for (Polygon polygon : command.getPolygons()) {
            KillingChain killingChain = new KillingChain();
            killingChain.setTargetName(polygon.getName());
            killingChain.setPolygon(polygon);
            List<GeometryUtils.Point> points = new ArrayList<>();
            for (Point point : polygon.getPoints()) {
                GeometryUtils.Point gPoint = new GeometryUtils.Point();
                gPoint.setX(point.getLon());
                gPoint.setY(point.getLat());
                points.add(gPoint);
            }
            double area = GeometryUtils.getPolygonArea(points);
//            double area = GeometryUtils.getDistance(points.get(0).getLon(), points.get(0).getLat(), points.get(1).getLon(), points.get(1).getLat()) * GeometryUtils.getDistance(points.get(1).getLon(), points.get(1).getLat(), points.get(2).getLon(), points.get(2).getLat());
            double speedUAV = 0;
            double speedUUV = 0;
            double rangeUAV = 0;
            double rangeUUV = 0;

            Iterator<Map.Entry<String, Platform>> it = management.getPlatformPool().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Platform> entry = it.next();
                if (entry.getValue().getName().contains("UAV") && entry.getValue().getBeMineSweep()) {
                    speedUAV = entry.getValue().getSpeed();
                    rangeUAV = entry.getValue().getFinds().get(0).getMaxDetectRangeSea();
                } else if (entry.getValue().getName().contains("UUV") && entry.getValue().getBeMineSweep()) {
                    speedUUV = entry.getValue().getSpeed();
                    rangeUUV = entry.getValue().getFinds().get(0).getMaxDetectRangeUnderSea();
                }
                if (speedUAV * speedUUV * rangeUUV * rangeUAV != 0) {
                    break;
                }
            }

            //单位时间内单个平台扫描面积 m²/s
            double areaUAV = speedUAV * rangeUAV * 2000;
            double areaUUV = speedUUV * rangeUUV * 2000;
            int numUAV = 0;
            int numUUV = 0;

            if (command.getLimitTime() != null) {
                numUAV = (int) Math.ceil((area / (command.getLimitTime() * areaUAV)));
                numUUV = (int) Math.ceil((area / (command.getLimitTime() * areaUUV)));
                if (numUAV > uav.size()) {
                    numUAV = uav.size();
                }
                if (numUUV > uuv.size()) {
                    numUUV = uuv.size();
                }
            } else {
                numUAV = uav.size();
                numUUV = uuv.size();
            }
            List<Find> res = new ArrayList<>();
            for (int i = 0; i < numUAV; i++) {
                res.add(uav.get(i));
            }
            for (int i = 0; i < numUUV; i++) {
                res.add(uuv.get(i));
            }
            killingChain.setFind(poolingService.findToList(res));
            list.add(killingChain);

        }

        return list;
    }

    public ResourceModel setWeapon(ResourceModel killEngage, List<ResourceModel> tempEngages, int i) {
        killEngage.setId(tempEngages.get(i).getId());
        killEngage.setName(tempEngages.get(i).getName());
        killEngage.setDeviceCode(tempEngages.get(i).getDeviceCode());
        killEngage.setType(tempEngages.get(i).getType());
        killEngage.setStatus(tempEngages.get(i).getStatus());
        killEngage.setPlatformCode(tempEngages.get(i).getPlatformCode());
        killEngage.setPlatformName(tempEngages.get(i).getPlatformName());
        killEngage.setNum(getWeaponNum(management.getEngageById(killEngage.getId()).getHitRate()));
        tempEngages.get(i).setNum(tempEngages.get(i).getNum() - killEngage.getNum());
        return killEngage;
    }

    public GeometryUtils.Point moveData2Point(PlatformMoveData moveData) {
        GeometryUtils.Point point = new GeometryUtils.Point();
        point.setX(moveData.getLon());
        point.setY(moveData.getLat());
        return point;
    }

    public KillingChain getSquid() {
        KillingChain killingChain = new KillingChain();


        List<ResourceModel> find = poolingService.findToList(management.getFindPool(null)).stream().filter(q -> q.getPlatformName().contains("-S")).collect(Collectors.toList());
        List<ResourceModel> fix = poolingService.fixToList(management.getFixPool(CommandType.ATTACK_UNDERSEA).stream().filter(q -> q.getPlatformName().contains("-S")).collect(Collectors.toList()));
        List<ResourceModel> track = poolingService.trackToList(management.getTrackPool(null).stream().filter(q -> q.getPlatformName().contains("-S")).collect(Collectors.toList()));
        List<ResourceModel> engage = poolingService.engageToList(management.getEngagePool(CommandType.ATTACK_UNDERSEA)).stream().filter(q -> q.getName().contains("灭雷炸弹")).collect(Collectors.toList());
        List<ResourceModel> asses = poolingService.assesToList(management.getAssesPool(null).stream().filter(q -> q.getPlatformName().contains("-S")).collect(Collectors.toList()));

        killingChain.setFind(find);
        killingChain.setFix(fix);
        killingChain.setTrack(track);
        killingChain.setEngage(engage);
        killingChain.setAsses(asses);

        Target t = getTargetById("100");
        List<Target> tl = new ArrayList<>();
        tl.add(t);
        List<ResourceModel> target = poolingService.targetToList(tl);
        killingChain.setTarget(target);

        return killingChain;
    }

    public List<KillingChain> getMineSweep(Command command) {
        List<KillingChain> list = new ArrayList<>();

        /**
         * 1.确定可以调用来扫雷的艇及其资源 List<KillingChain> USV_S;
         * 每个killingChain对应一个USV-S的全部资源
         * 2.轮询调度艇
         * 对目标targets按UAV_S.size()划分，进行轮询
         * 3.分配给艇离当前距离最近的雷
         * 没有被分配过任务，计算离platformMoveData最近的雷作为目标
         * 有被分配过任务，计算离上一次任务目标target.getMoveData最近的雷作为新目标
         **/

//        确定可以调用来扫雷的艇及其资源 List<KillingChain> USV_S;
        List<KillingChain> USV = getUSV_S();

//        轮询调度艇
//        对目标targets按UAV_S.size()划分，进行轮询
        int num = USV.size();
        int count = 0;
        int realEquip = 0;
        List<TargetData> targets = new ArrayList<>();
        for (TargetData t : command.getTargets()) {
            targets.add(t);
        }

        while (targets.size() != 0) {
            KillingChain killingChain = new KillingChain();
            killingChain.setCommandType(25);
            if (metaConfig.getBeRealEquipment() && realEquip < 2) {
//              设置资源
                killingChain.setFind(USV.get(0).getFind());
                killingChain.setFix(USV.get(0).getFix());
                killingChain.setTrack(USV.get(0).getTrack());
                killingChain.setTarget(USV.get(0).getTarget());
                killingChain.setEngage(USV.get(0).getEngage());
                killingChain.setAsses(USV.get(0).getAsses());
                GeometryUtils.Point point = new GeometryUtils.Point();
                PlatformMoveData moveData = new PlatformMoveData();
//              计算离platformMoveData最近的雷作为目标
                moveData = management.getPlatformPool().get(killingChain.getFind().get(0).getPlatformCode()).getPlatformMoveData();
                point.setX(moveData.getLon());
                point.setY(moveData.getLat());
//              计算距离point最近的雷
                TargetData target = getClosestTarget(point, targets);
                targets.remove(target);
                killingChain.setTargetName(target.getName());
                killingChain.setTargetId(target.getInstId());
                list.add(killingChain);

//              调度的UUV、USV实装一次之后就要从列表里面删除
                realEquip++;
                USV.remove(0);
                num--;
            } else {
                killingChain.setFind(USV.get(count % num).getFind());
                killingChain.setFix(USV.get(count % num).getFix());
                killingChain.setTrack(USV.get(count % num).getTrack());
                killingChain.setTarget(USV.get(count % num).getTarget());
                killingChain.setEngage(USV.get(count % num).getEngage());
                killingChain.setAsses(USV.get(count % num).getAsses());

                GeometryUtils.Point point = new GeometryUtils.Point();
                PlatformMoveData moveData = new PlatformMoveData();
//            没有被分配过任务，计算离platformMoveData最近的雷作为目标
                if (count < num) {
                    moveData = management.getPlatformPool().get(killingChain.getFind().get(0).getPlatformCode()).getPlatformMoveData();
                }
//            有被分配过任务，计算离上一次任务目标target.getMoveData最近的雷作为新目标
                else {
                    moveData = getTargetById(command.getTargets(), list.get(count - num).getTargetId()).getMoveDetect();
                }
                point.setX(moveData.getLon());
                point.setY(moveData.getLat());
//            计算距离point最近的雷
                TargetData target = getClosestTarget(point, targets);
                targets.remove(target);
                killingChain.setTargetName(target.getName());
                killingChain.setTargetId(target.getInstId());
                list.add(killingChain);
                count++;
            }
        }
//        分配给艇离当前距离最近的雷

        return list;
    }

    public List<KillingChain> getUSV_S() {
        List<KillingChain> list = new ArrayList<>();
        List<Platform> platforms = management.getPlatformPool().values().stream().collect(Collectors.toList());
        if (metaConfig.getBeRealEquipment()) {
            //考虑全部实装
//            platforms = platforms.stream().filter(q -> q.getBeRealEquipment() != null
//                    && q.getBeMineSweep() != null && (q.getBeMineSweep().equals(true))
//                    && q.getName().contains("USV-S")).collect(Collectors.toList());
            //暂时只考虑一个实装USV-S_4
            //如果考虑实装，扫雷资源里面会添加实装的UUV-S_15
            platforms = platforms.stream().filter(q -> q.getBeRealEquipment() != null && (q.getBeRealEquipment().equals(false))
                    && q.getBeMineSweep() != null && (q.getBeMineSweep().equals(true))
                    && q.getName().contains("USV-S")).collect(Collectors.toList());
            platforms.add(0, management.getPlatformPool().get("78"));
            platforms.add(0, management.getPlatformPool().get("87"));
        } else {
            //不考虑实装
            platforms = platforms.stream().filter(q -> q.getBeRealEquipment() != null && (q.getBeRealEquipment().equals(false))
                    && q.getBeMineSweep() != null && (q.getBeMineSweep().equals(true))
                    && q.getName().contains("USV-S")).collect(Collectors.toList());
        }

        //无人机指控id为100，后续可以新增寻找最近的带指控的平台作为指控
        Target t = getTargetById("100");
        List<Target> tl = new ArrayList<>();
        tl.add(t);
        for (Platform platform : platforms) {
            KillingChain killingChain = new KillingChain();
            killingChain.setFind(poolingService.findToList(platform.getFinds()));
            killingChain.setFix(poolingService.fixToList(platform.getFixes()));
            killingChain.setTarget(poolingService.targetToList(tl));
            killingChain.setTrack(poolingService.trackToList(platform.getTracks()));
            killingChain.setEngage(poolingService.engageToList(platform.getEngages()));
            killingChain.setAsses(poolingService.assesToList(platform.getAsses()));
            list.add(killingChain);
        }

        return list;
    }

    public List<Find> getUAV_S() {

        List<Find> finds = management.getFindPool(null).stream().collect(Collectors.toList());
        if (metaConfig.getBeRealEquipment()) {
            //考虑全部实装
//            finds = finds.stream().filter(q -> management.getPlatformPool().get(q.getPlatformCode()).getBeRealEquipment() != null
//                    && management.getPlatformPool().get(q.getPlatformCode()).getBeMineSweep() != null && (management.getPlatformPool().get(q.getPlatformCode()).getBeMineSweep().equals(true))
//                    && management.getPlatformPool().get(q.getPlatformCode()).getName().contains("UAV-S")).collect(Collectors.toList());
            //只考虑一个实装UAV-S_4
            finds = finds.stream().filter(q -> management.getPlatformPool().get(q.getPlatformCode()).getBeRealEquipment() != null && (management.getPlatformPool().get(q.getPlatformCode()).getBeRealEquipment().equals(false))
                    && management.getPlatformPool().get(q.getPlatformCode()).getBeMineSweep() != null && (management.getPlatformPool().get(q.getPlatformCode()).getBeMineSweep().equals(true))
                    && management.getPlatformPool().get(q.getPlatformCode()).getName().contains("UAV-S")).collect(Collectors.toList());
            finds.add(0, management.getFindPool(null).stream().filter(q -> q.getPlatformCode().equals("42")).collect(Collectors.toList()).get(0));
        } else {
            //不考虑实装
            finds = finds.stream().filter(q -> management.getPlatformPool().get(q.getPlatformCode()).getBeRealEquipment() != null && (management.getPlatformPool().get(q.getPlatformCode()).getBeRealEquipment().equals(false))
                    && management.getPlatformPool().get(q.getPlatformCode()).getBeMineSweep() != null && (management.getPlatformPool().get(q.getPlatformCode()).getBeMineSweep().equals(true))
                    && management.getPlatformPool().get(q.getPlatformCode()).getName().contains("UAV-S")).collect(Collectors.toList());
        }
        return finds;
    }

    public List<Find> getUUV_S() {

        List<Find> finds = management.getFindPool(null).stream().collect(Collectors.toList());
        if (metaConfig.getBeRealEquipment()) {
            //考虑全部实装
//            finds = finds.stream().filter(q -> management.getPlatformPool().get(q.getPlatformCode()).getBeRealEquipment() != null
//                    && management.getPlatformPool().get(q.getPlatformCode()).getBeMineSweep() != null && (management.getPlatformPool().get(q.getPlatformCode()).getBeMineSweep().equals(true))
//                    && management.getPlatformPool().get(q.getPlatformCode()).getName().contains("UUV-S")).collect(Collectors.toList());
            //只考虑一个实装，但是这个UUV-S_15不会用于搜索，只能用于打击，这里不加进去
            finds = finds.stream().filter(q -> management.getPlatformPool().get(q.getPlatformCode()).getBeRealEquipment() != null && (management.getPlatformPool().get(q.getPlatformCode()).getBeRealEquipment().equals(false))
                    && management.getPlatformPool().get(q.getPlatformCode()).getBeMineSweep() != null && (management.getPlatformPool().get(q.getPlatformCode()).getBeMineSweep().equals(true))
                    && management.getPlatformPool().get(q.getPlatformCode()).getName().contains("UUV-S")).collect(Collectors.toList());
        } else {
            //不考虑实装
            finds = finds.stream().filter(q -> management.getPlatformPool().get(q.getPlatformCode()).getBeRealEquipment() != null && (management.getPlatformPool().get(q.getPlatformCode()).getBeRealEquipment().equals(false))
                    && management.getPlatformPool().get(q.getPlatformCode()).getBeMineSweep() != null && (management.getPlatformPool().get(q.getPlatformCode()).getBeMineSweep().equals(true))
                    && management.getPlatformPool().get(q.getPlatformCode()).getName().contains("UUV-S")).collect(Collectors.toList());
        }
        return finds;
    }

    public TargetData getClosestTarget(GeometryUtils.Point point, List<TargetData> targets) {
        TargetData res = new TargetData();
        double distance = Double.MAX_VALUE;
        GeometryUtils.Point tPoint = new GeometryUtils.Point();
        for (TargetData target : targets) {
            tPoint.setX(target.getMoveDetect().getLon());
            tPoint.setY(target.getMoveDetect().getLat());
            double pDistance = GeometryUtils.getDistance(point.getX(), point.getY(), tPoint.getX(), tPoint.getY());
            if (distance > pDistance) {
                distance = pDistance;
                res = target;
            }
        }

        return res;
    }

    @Override
    public CommandType getCommandType(Command command) {
        CommandType type = CommandType.ATTACK;
        if (command.getType() == CommandType.ATTACK_AIR.getValue()) {
            type = CommandType.ATTACK_AIR;
        } else if (command.getType() == CommandType.ATTACK_SEA.getValue()) {
            type = CommandType.ATTACK_SEA;
        } else if (command.getType() == CommandType.ATTACK_LAND.getValue()) {
            type = CommandType.ATTACK_LAND;
        } else if (command.getType() == CommandType.ATTACK_UNDERSEA.getValue()) {
            type = CommandType.ATTACK_UNDERSEA;
        } else if (command.getType() == CommandType.SEARCH.getValue()) {
            type = CommandType.SEARCH;
        } else {
            log.info("commandType 错误，取值不在10，21，22，23，24");
            return null;
        }
        return type;
    }

    /**
     * 根据武器命中率计算所需弹药
     *
     * @param rate
     * @return
     */
    public Integer getWeaponNum(Float rate) {
        Integer num = 0;
        double hitRate = 0;
        for (int i = 1; hitRate < 0.95; i++) {
            hitRate += rate * Math.pow(1 - rate, i - 1);
            num = i;
        }
        return num;
    }

    /**
     * 根据engage获取对应的track
     *
     * @param engage
     * @param command
     * @return
     */
    public ResourceModel getTrackByEngage(ResourceModel engage, Command command) {
        ResourceModel model = new ResourceModel();
        List<Track> tracks = management.getPlatformPool().get(engage.getPlatformCode()).getTracks();
        if (getCommandType(command).getValue() == 21) {
            tracks.sort(Comparator.comparing(Track::getMaxDetectRangeAir).reversed());
            if (tracks.size() != 0 && tracks.get(0).getMaxDetectRangeAir() > 0) {
                List<ResourceModel> list = poolingService.trackToList(tracks);
                model = list.get(0);
                return model;
            }
        } else if (getCommandType(command).getValue() == 22) {
            tracks.sort(Comparator.comparing(Track::getMaxDetectRangeSea).reversed());
            if (tracks.size() != 0 && tracks.get(0).getMaxDetectRangeSea() > 0) {
                List<ResourceModel> list = poolingService.trackToList(tracks);
                model = list.get(0);
                return model;
            }
        } else if (getCommandType(command).getValue() == 23) {
            tracks.sort(Comparator.comparing(Track::getMaxDetectRangeLand).reversed());
            if (tracks.size() != 0 && tracks.get(0).getMaxDetectRangeLand() > 0) {
                List<ResourceModel> list = poolingService.trackToList(tracks);
                model = list.get(0);
                return model;
            }
        } else if (getCommandType(command).getValue() == 24) {
            tracks.sort(Comparator.comparing(Track::getMaxDetectRangeUnderSea).reversed());
            if (tracks.size() != 0 && tracks.get(0).getMaxDetectRangeUnderSea() > 0) {
                List<ResourceModel> list = poolingService.trackToList(tracks);
                model = list.get(0);
                return model;
            }
        }

        return null;
    }

    public TargetData getTargetById(List<TargetData> targets, String id) {
        for (TargetData target : targets) {
            if (target.getInstId().equals(id)) {
                return target;
            }
        }
        return null;
    }

    public Target getTargetById(String id) {
        Target t = new Target();
        for (Target target : management.getTargetPool(null)) {
            if (target.getId().equals(id)) {
                t = target;
                break;
            }
        }

        return t;
    }

    public List<ResourceModel> getFind(GeometryUtils.Point start, GeometryUtils.Point end, Command command) {
        List<Find> list = new ArrayList<>();
        List<Find> finds = management.getFindPool(getCommandType(command));

        for (Find find : finds) {
            PlatformMoveData moveData = management.getPlatformPool().get(find.getPlatformCode()).getPlatformMoveData();
            GeometryUtils.Point point = new GeometryUtils.Point();
            if (moveData != null) {
                point = moveData2Point(moveData);
            } else {
                point.setX(management.getPlatformPool().get(find.getPlatformCode()).getLon());
                point.setY(management.getPlatformPool().get(find.getPlatformCode()).getLat());
            }
            double distance = GeometryUtils.minDistanceFromPointToLine(start, end, point);
            find.setDistance(distance);
            if (getCommandType(command).getValue() == 21 && (find.getMaxDetectRangeAir() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(start.getX(), start.getY(), point.getX(), point.getY());
                find.setDistance(distance);
                list.add(find);
            } else if (getCommandType(command).getValue() == 22 && (find.getMaxDetectRangeSea() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(start.getX(), start.getY(), point.getX(), point.getY());
                find.setDistance(distance);
                list.add(find);
            } else if (getCommandType(command).getValue() == 23 && (find.getMaxDetectRangeLand() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(start.getX(), start.getY(), point.getX(), point.getY());
                find.setDistance(distance);
                list.add(find);
            } else if (getCommandType(command).getValue() == 24 && (find.getMaxDetectRangeUnderSea() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(start.getX(), start.getY(), point.getX(), point.getY());
                find.setDistance(distance);
                list.add(find);
            }


        }

        list.sort(Comparator.comparing(Find::getDistance));

        return poolingService.findToList(list);
    }

    public List<ResourceModel> getFix(GeometryUtils.Point start, GeometryUtils.Point end, Command command) {
        List<Fix> list = new ArrayList<>();
        List<Fix> fixes = management.getFixPool(getCommandType(command));

        for (Fix fix : fixes) {
            PlatformMoveData moveData = management.getPlatformPool().get(fix.getPlatformCode()).getPlatformMoveData();
            GeometryUtils.Point point = new GeometryUtils.Point();
            if (moveData != null) {
                point = moveData2Point(moveData);
            } else {
                point.setX(management.getPlatformPool().get(fix.getPlatformCode()).getLon());
                point.setY(management.getPlatformPool().get(fix.getPlatformCode()).getLat());
            }
            double distance = GeometryUtils.minDistanceFromPointToLine(start, end, point);
            fix.setDistance(distance);
            if (getCommandType(command).getValue() == 21 && (fix.getMaxDetectRangeAir() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(start.getX(), start.getY(), point.getX(), point.getY());
                fix.setDistance(distance);
                list.add(fix);
            } else if (getCommandType(command).getValue() == 22 && (fix.getMaxDetectRangeSea() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(start.getX(), start.getY(), point.getX(), point.getY());
                fix.setDistance(distance);
                list.add(fix);
            } else if (getCommandType(command).getValue() == 23 && (fix.getMaxDetectRangeLand() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(start.getX(), start.getY(), point.getX(), point.getY());
                fix.setDistance(distance);
                list.add(fix);
            } else if (getCommandType(command).getValue() == 24 && (fix.getMaxDetectRangeUnderSea() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(start.getX(), start.getY(), point.getX(), point.getY());
                fix.setDistance(distance);
                list.add(fix);
            }


        }

        list.sort(Comparator.comparing(Fix::getDistance));
        return poolingService.fixToList(list);
    }

    public List<ResourceModel> getTarget(List<ResourceModel> Engage) {
        List<Target> list = new ArrayList<>();
        for (ResourceModel model : Engage) {
            if (management.getTargetByPlatform(model.getPlatformCode()).size() != 0) {
                list.add(management.getTargetByPlatform(model.getPlatformCode()).get(0));
            } else {
                //TODO:magic value 100
                list.add(management.getTargetByPlatform("100").get(0));
            }
        }

        return poolingService.targetToList(list);
    }

    public List<ResourceModel> getTrack(GeometryUtils.Point start, GeometryUtils.Point end, Command command) {
        List<Track> list = new ArrayList<>();
        List<Track> tracks = management.getTrackPool(getCommandType(command));

        for (Track track : tracks) {
            PlatformMoveData moveData = management.getPlatformPool().get(track.getPlatformCode()).getPlatformMoveData();
            GeometryUtils.Point point = new GeometryUtils.Point();
            if (moveData != null) {
                point = moveData2Point(moveData);
            } else {
                point.setX(management.getPlatformPool().get(track.getPlatformCode()).getLon());
                point.setY(management.getPlatformPool().get(track.getPlatformCode()).getLat());
            }
            double distance = GeometryUtils.minDistanceFromPointToLine(start, end, point);
            track.setDistance(distance);
            if (getCommandType(command).getValue() == 21 && (track.getMaxDetectRangeAir() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(start.getX(), start.getY(), point.getX(), point.getY());
                track.setDistance(distance);
                list.add(track);
            } else if (getCommandType(command).getValue() == 22 && (track.getMaxDetectRangeSea() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(start.getX(), start.getY(), point.getX(), point.getY());
                track.setDistance(distance);
                list.add(track);
            } else if (getCommandType(command).getValue() == 23 && (track.getMaxDetectRangeLand() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(start.getX(), start.getY(), point.getX(), point.getY());
                track.setDistance(distance);
                list.add(track);
            } else if (getCommandType(command).getValue() == 24 && (track.getMaxDetectRangeUnderSea() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(start.getX(), start.getY(), point.getX(), point.getY());
                track.setDistance(distance);
                list.add(track);
            }


        }

        list.sort(Comparator.comparing(Track::getDistance));
        return poolingService.trackToList(list);
    }

    public List<ResourceModel> getAsses(GeometryUtils.Point start, GeometryUtils.Point end, Command command) {
        List<Asses> list = new ArrayList<>();
        List<Asses> assesList = management.getAssesPool(getCommandType(command));

        for (Asses asses : assesList) {
            PlatformMoveData moveData = management.getPlatformPool().get(asses.getPlatformCode()).getPlatformMoveData();
            GeometryUtils.Point point = new GeometryUtils.Point();
            if (moveData != null) {
                point = moveData2Point(moveData);
            } else {
                point.setX(management.getPlatformPool().get(asses.getPlatformCode()).getLon());
                point.setY(management.getPlatformPool().get(asses.getPlatformCode()).getLat());
            }

            double distance = GeometryUtils.minDistanceFromPointToLine(start, end, point);
            asses.setDistance(distance);
            if (getCommandType(command).getValue() == 21 && (asses.getMaxDetectRangeAir() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(end.getX(), end.getY(), point.getX(), point.getY());
                asses.setDistance(distance);
                list.add(asses);
            } else if (getCommandType(command).getValue() == 22 && (asses.getMaxDetectRangeSea() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(end.getX(), end.getY(), point.getX(), point.getY());
                asses.setDistance(distance);
                list.add(asses);
            } else if (getCommandType(command).getValue() == 23 && (asses.getMaxDetectRangeLand() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(end.getX(), end.getY(), point.getX(), point.getY());
                asses.setDistance(distance);
                list.add(asses);
            } else if (getCommandType(command).getValue() == 24 && (asses.getMaxDetectRangeUnderSea() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(end.getX(), end.getY(), point.getX(), point.getY());
                asses.setDistance(distance);
                list.add(asses);
            }


        }

        list.sort(Comparator.comparing(Asses::getDistance));
        return poolingService.assesToList(list);
    }

    public List<ResourceModel> getEngage(GeometryUtils.Point start, GeometryUtils.Point end, Command command) {
        List<Engage> list = new ArrayList<>();
        List<Engage> engages = management.getEngagePool(getCommandType(command));

        for (Engage engage : engages) {
            PlatformMoveData moveData = management.getPlatformPool().get(engage.getPlatformCode()).getPlatformMoveData();
            GeometryUtils.Point point = new GeometryUtils.Point();
            if (moveData != null) {
                point = moveData2Point(moveData);
            } else {
                point.setX(management.getPlatformPool().get(engage.getPlatformCode()).getLon());
                point.setY(management.getPlatformPool().get(engage.getPlatformCode()).getLat());
            }
            double distance = GeometryUtils.minDistanceFromPointToLine(start, end, point);
            engage.setDistance(distance);
            if (getCommandType(command).getValue() == 21 && (engage.getMaxFireRangeAir() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(start.getX(), start.getY(), point.getX(), point.getY());
                engage.setDistance(distance);
                list.add(engage);
            } else if (getCommandType(command).getValue() == 22 && (engage.getMaxFireRangeSea() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(start.getX(), start.getY(), point.getX(), point.getY());
                engage.setDistance(distance);
                list.add(engage);
            } else if (getCommandType(command).getValue() == 23 && (engage.getMaxFireRangeLand() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(start.getX(), start.getY(), point.getX(), point.getY());
                engage.setDistance(distance);
                list.add(engage);
            } else if (getCommandType(command).getValue() == 24 && (engage.getMaxFireRangeUnderSea() * 1000 > distance)) {
                distance = GeometryUtils.getDistance(start.getX(), start.getY(), point.getX(), point.getY());
                engage.setDistance(distance);
                list.add(engage);
            }


        }

        list.sort(Comparator.comparing(Engage::getDistance));
        return poolingService.engageToList(list);
    }

}
