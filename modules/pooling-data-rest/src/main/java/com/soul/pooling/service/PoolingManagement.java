package com.soul.pooling.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.egova.exception.ExceptionUtils;
import com.egova.json.utils.JsonUtils;
import com.egova.redis.RedisUtils;
import com.soul.pooling.config.Constants;
import com.soul.pooling.config.MetaConfig;
import com.soul.pooling.config.PoolingConfig;
import com.soul.pooling.entity.*;
import com.soul.pooling.entity.enums.CommandType;
import com.soul.pooling.entity.enums.ResourceStatus;
import com.soul.pooling.model.*;
import com.soul.pooling.mqtt.producer.MqttMsgProducer;
import com.soul.pooling.utils.GeometryUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Priority;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


/**
 * @Description:
 * @Author: nemo
 * @Date: 2022/4/1
 */
@Service
@Slf4j
@Priority(5)
public class PoolingManagement {

    @Autowired
    private PlatformService platformService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MqttMsgProducer mqttMsgProducer;

    @Autowired
    private PoolingConfig poolingConfig;

    @Autowired
    private MetaConfig metaConfig;

    private final ConcurrentMap<String, PlatformStatus> forceStatusData = new ConcurrentHashMap();

    private final ConcurrentMap<String, Platform> platformPool = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Find> findPool = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Fix> fixPool = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Track> trackPool = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Target> targetPool = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Engage> engagePool = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Asses> assesPool = new ConcurrentHashMap<>();

    private NetStatusData netStatusData = new NetStatusData();

    private NetStatusData netLinkData = new NetStatusData();

    private Map<String, Long> timeRecords = new HashMap<>();

    private Map<String, Long> lastRecords = new HashMap<>();

    private Set<String> onLineNodes = new HashSet<>();

    private Short[][] state = new Short[150][150];

    public Set<String> getOnLineNodes() {
        return onLineNodes;
    }

    public void setState(Short[][] arr) {
        state = arr;
    }

    public void setOnLineNodes(String s) {
        onLineNodes.add(s);
    }

    public void removeOnLineNodes(String s) {
        onLineNodes.remove(s);
    }

    public Map<String, Long> getLastRecords() {
        return lastRecords;
    }

    public void setLastRecords(String id, Long time) {
        lastRecords.put(id, time);
    }

    public Map<String, Long> getTimeRecords() {
        return timeRecords;
    }

    public void setTimeRecords(String id, Long time) {
        timeRecords.put(id, time);
    }

    public void clearTimeRecords() {
        timeRecords.clear();
    }

    public NetStatusData getNetData() {
        return netStatusData;
    }

    public NetStatusData getNetLinkData() {
        return netLinkData;
    }

    public void setNetData(NetStatusData data) {
        int head = 19020;
        if (metaConfig.getBeNet()) {
            if (data.getMgmt_head() == head) {
                Short[] index = data.getMgmt_150();
                Short[][] arr = data.getMgmt_150x150();
                Short[][] res = new Short[150][150];
                for (int i = 0; i < index.length; i++) {
                    for (int j = 0; j < index.length; j++) {
                        res[i][j] = -1;
                    }
                }
                for (int i = 0; i < index.length; i++) {
                    for (int j = 0; j < index.length; j++) {
                        if (index[i] * index[j] != 0 && index[i] < index.length && index[j] < index.length && index[i] >= -1 && index[j] >= -1) {
                            res[index[i]][index[j]] = arr[i][j];
                        }
                    }
                }

                netStatusData.setMgmt_150x150(res);

            } else {
                netStatusData.setTimes(data.getTimes());
                System.out.println("上下线组网返回时间" + data.getTimes());
                //重连截止时间
                Long endTime = System.currentTimeMillis();
                //TODO:根据id设置endTime
                timeRecords.put("endTime", endTime);
            }
        } else {
            double time = Math.random() * 10 + 15;
            netStatusData.setTimes(time);
            System.out.println("上下线组网返回时间" + time);
            //重连截止时间
            Long endTime = System.currentTimeMillis();
            //TODO:根据id设置endTime
            timeRecords.put("endTime", endTime);
        }
        return;
    }

    public void setNetLinkData(NetStatusData data) {
        int head = 19534;
        if (metaConfig.getBeNet()) {
            if (data.getMgmt_head() == head) {
                Short[] index = data.getMgmt_150();
                Short[][] arr = data.getMgmt_150x150();
                Short[][] res = new Short[150][150];
                for (int i = 0; i < index.length; i++) {
                    for (int j = 0; j < index.length; j++) {
                        res[i][j] = -1;
                    }
                }
                for (int i = 0; i < index.length; i++) {
                    for (int j = 0; j < index.length; j++) {
                        if (index[i] * index[j] != 0 && index[i] < index.length && index[j] < index.length && index[i] >= -1 && index[j] >= -1) {
                            res[index[i]][index[j]] = arr[i][j];
                        }
                    }
                }

                netLinkData.setMgmt_150x150(res);

            } else {
                netLinkData.setTimes(data.getTimes());
                System.out.println("上下线组网返回时间" + data.getTimes());
                //重连截止时间
                Long endTime = System.currentTimeMillis();
                //TODO:根据id设置endTime
                timeRecords.put("endTime", endTime);
            }
        } else {
            double time = Math.random() * 10 + 15;
            netLinkData.setTimes(time);
            System.out.println("上下线组网返回时间" + time);
            //重连截止时间
            Long endTime = System.currentTimeMillis();
            //TODO:根据id设置endTime
            timeRecords.put("endTime", endTime);
        }
        return;
    }

    public Map<String, PlatformStatus> getAll() {
        return forceStatusData;
    }

    public Map<String, Platform> getPlatformPool() {
        return platformPool;
    }

    public Find getFindById(String id) {
        Find find = findPool.get(id);
        Platform platform = platformPool.get(find.getPlatformCode());
        if (platform != null && platform.getPlatformMoveData() != null) {
            find.set("lon", platform.getPlatformMoveData().getLon());
            find.set("lat", platform.getPlatformMoveData().getLat());
            find.set("alt", platform.getPlatformMoveData().getAlt());
            find.set("heading", platform.getPlatformMoveData().getHeading());
            find.set("pitch", platform.getPlatformMoveData().getPitch());
            find.set("roll", platform.getPlatformMoveData().getRoll());
            find.set("speed", platform.getPlatformMoveData().getSpeed());
        }

        return find;
    }

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

    public Fix getFixById(String id) {
        Fix fix = fixPool.get(id);
        Platform platform = platformPool.get(fix.getPlatformCode());
        if (platform != null && platform.getPlatformMoveData() != null) {
            fix.set("lon", platform.getPlatformMoveData().getLon());
            fix.set("lat", platform.getPlatformMoveData().getLat());
            fix.set("alt", platform.getPlatformMoveData().getAlt());
            fix.set("heading", platform.getPlatformMoveData().getHeading());
            fix.set("pitch", platform.getPlatformMoveData().getPitch());
            fix.set("roll", platform.getPlatformMoveData().getRoll());
            fix.set("speed", platform.getPlatformMoveData().getSpeed());
        }

        return fix;
    }

    public Track getTrackById(String id) {
        Track track = trackPool.get(id);
        Platform platform = platformPool.get(track.getPlatformCode());
        if (platform != null && platform.getPlatformMoveData() != null) {
            track.set("lon", platform.getPlatformMoveData().getLon());
            track.set("lat", platform.getPlatformMoveData().getLat());
            track.set("alt", platform.getPlatformMoveData().getAlt());
            track.set("heading", platform.getPlatformMoveData().getHeading());
            track.set("pitch", platform.getPlatformMoveData().getPitch());
            track.set("roll", platform.getPlatformMoveData().getRoll());
            track.set("speed", platform.getPlatformMoveData().getSpeed());
        }

        return track;
    }

    public Target getTargetById(String id) {
        Target target = targetPool.get(id);
        Platform platform = platformPool.get(target.getPlatformCode());
        if (platform != null && platform.getPlatformMoveData() != null) {
            target.set("lon", platform.getPlatformMoveData().getLon());
            target.set("lat", platform.getPlatformMoveData().getLat());
            target.set("alt", platform.getPlatformMoveData().getAlt());
            target.set("heading", platform.getPlatformMoveData().getHeading());
            target.set("pitch", platform.getPlatformMoveData().getPitch());
            target.set("roll", platform.getPlatformMoveData().getRoll());
            target.set("speed", platform.getPlatformMoveData().getSpeed());
        }

        return target;
    }

    public Engage getEngageById(String id) {
        Engage engage = engagePool.get(id);
        Platform platform = platformPool.get(engage.getPlatformCode());
        if (platform != null && platform.getPlatformMoveData() != null) {
            engage.set("lon", platform.getPlatformMoveData().getLon());
            engage.set("lat", platform.getPlatformMoveData().getLat());
            engage.set("alt", platform.getPlatformMoveData().getAlt());
            engage.set("heading", platform.getPlatformMoveData().getHeading());
            engage.set("pitch", platform.getPlatformMoveData().getPitch());
            engage.set("roll", platform.getPlatformMoveData().getRoll());
            engage.set("speed", platform.getPlatformMoveData().getSpeed());
        }

        return engage;
    }

    public Asses getAssesById(String id) {
        Asses asses = assesPool.get(id);
        Platform platform = platformPool.get(asses.getPlatformCode());
        if (platform != null && platform.getPlatformMoveData() != null) {
            asses.set("lon", platform.getPlatformMoveData().getLon());
            asses.set("lat", platform.getPlatformMoveData().getLat());
            asses.set("alt", platform.getPlatformMoveData().getAlt());
            asses.set("heading", platform.getPlatformMoveData().getHeading());
            asses.set("pitch", platform.getPlatformMoveData().getPitch());
            asses.set("roll", platform.getPlatformMoveData().getRoll());
            asses.set("speed", platform.getPlatformMoveData().getSpeed());
        }

        return asses;
    }

    public List<Find> getFindPool(CommandType type) {
        List<Find> result = findPool.values().stream().collect(Collectors.toList());
        if (type == null) {
            return result;
        }
        if (type == CommandType.ATTACK_AIR) {
            result = result.stream().filter(q -> q.getMaxDetectRangeAir() > 0).collect(Collectors.toList());
        }
        if (type == CommandType.ATTACK_SEA) {
            result = result.stream().filter(q -> q.getMaxDetectRangeSea() > 0).collect(Collectors.toList());
        }
        if (type == CommandType.ATTACK_LAND) {
            result = result.stream().filter(q -> q.getMaxDetectRangeLand() > 0).collect(Collectors.toList());
        }
        if (type == CommandType.ATTACK_UNDERSEA) {
            result = result.stream().filter(q -> q.getMaxDetectRangeUnderSea() > 0).collect(Collectors.toList());
        }
        return result;
    }

    public List<Fix> getFixPool(CommandType type) {
        List<Fix> result = fixPool.values().stream().collect(Collectors.toList());
        if (type == null) {
            return result;
        }
        if (type == CommandType.ATTACK_AIR) {
            result = result.stream().filter(q -> q.getMaxDetectRangeAir() > 0).collect(Collectors.toList());
        }
        if (type == CommandType.ATTACK_SEA) {
            result = result.stream().filter(q -> q.getMaxDetectRangeSea() > 0).collect(Collectors.toList());
        }
        if (type == CommandType.ATTACK_LAND) {
            result = result.stream().filter(q -> q.getMaxDetectRangeLand() > 0).collect(Collectors.toList());
        }
        if (type == CommandType.ATTACK_UNDERSEA) {
            result = result.stream().filter(q -> q.getMaxDetectRangeUnderSea() > 0).collect(Collectors.toList());
        }
        return result;

    }
    
    public List<Track> getTrackPool(CommandType type) {
        List<Track> result = trackPool.values().stream().collect(Collectors.toList());
        if (type == null) {
            return result;
        }
        if (type == CommandType.ATTACK_AIR) {
            result = result.stream().filter(q -> q.getMaxDetectRangeAir() > 0).collect(Collectors.toList());
        }
        if (type == CommandType.ATTACK_SEA) {
            result = result.stream().filter(q -> q.getMaxDetectRangeSea() > 0).collect(Collectors.toList());
        }
        if (type == CommandType.ATTACK_LAND) {
            result = result.stream().filter(q -> q.getMaxDetectRangeLand() > 0).collect(Collectors.toList());
        }
        if (type == CommandType.ATTACK_UNDERSEA) {
            result = result.stream().filter(q -> q.getMaxDetectRangeUnderSea() > 0).collect(Collectors.toList());
        }
        return result;
    }

    public List<Target> getTargetPool(CommandType type) {
        List<Target> result = targetPool.values().stream().collect(Collectors.toList());

        return result;
    }

    public List<Engage> getEngagePool(CommandType type) {
        List<Engage> result = engagePool.values().stream().collect(Collectors.toList());
        if (type == null) {
            return result;
        }
        if (type == CommandType.ATTACK_AIR) {
            result = result.stream().filter(q -> q.getMaxFireRangeAir() > 0).collect(Collectors.toList());
        }
        if (type == CommandType.ATTACK_SEA) {
            result = result.stream().filter(q -> q.getMaxFireRangeSea() > 0).collect(Collectors.toList());
        }
        if (type == CommandType.ATTACK_LAND) {
            result = result.stream().filter(q -> q.getMaxFireRangeLand() > 0).collect(Collectors.toList());
        }
        if (type == CommandType.ATTACK_UNDERSEA) {
            result = result.stream().filter(q -> q.getMaxFireRangeUnderSea() > 0).collect(Collectors.toList());
        }

        return result;
    }

    public List<Asses> getAssesPool(CommandType type) {
        List<Asses> result = assesPool.values().stream().collect(Collectors.toList());
        if (type == null) {
            return result;
        }
        if (type == CommandType.ATTACK_AIR) {
            result = result.stream().filter(q -> q.getMaxDetectRangeAir() > 0).collect(Collectors.toList());
        }
        if (type == CommandType.ATTACK_SEA) {
            result = result.stream().filter(q -> q.getMaxDetectRangeSea() > 0).collect(Collectors.toList());
        }
        if (type == CommandType.ATTACK_LAND) {
            result = result.stream().filter(q -> q.getMaxDetectRangeLand() > 0).collect(Collectors.toList());
        }
        if (type == CommandType.ATTACK_UNDERSEA) {
            result = result.stream().filter(q -> q.getMaxDetectRangeUnderSea() > 0).collect(Collectors.toList());
        }
        return result;
    }

    public List<Find> getFindByPlatform(String code) {
        List<Find> list = new ArrayList<>();
        for (String id : findPool.keySet()) {
            if (findPool.get(id).getPlatformCode().equals(code)) {
                list.add(findPool.get(id));
            }
        }
        return list;
    }

    public List<Fix> getFixByPlatform(String code) {
        List<Fix> list = new ArrayList<>();
        for (String id : fixPool.keySet()) {
            if (fixPool.get(id).getPlatformCode().equals(code)) {
                list.add(fixPool.get(id));
            }
        }
        return list;
    }

    public List<Track> getTrackByPlatform(String code) {
        List<Track> list = new ArrayList<>();
        for (String id : trackPool.keySet()) {
            if (trackPool.get(id).getPlatformCode().equals(code)) {
                list.add(trackPool.get(id));
            }
        }
        return list;
    }

    public List<Target> getTargetByPlatform(String code) {
        List<Target> list = new ArrayList<>();
        for (String id : targetPool.keySet()) {
            if (targetPool.get(id).getPlatformCode().equals(code)) {
                list.add(targetPool.get(id));
            }
        }
        return list;
    }

    public List<Engage> getEngageByPlatform(String code) {
        List<Engage> list = new ArrayList<>();
        for (String id : engagePool.keySet()) {
            if (engagePool.get(id).getPlatformCode().equals(code)) {
                list.add(engagePool.get(id));
            }
        }
        return list;
    }

    public List<Asses> getAssesByPlatform(String code) {
        List<Asses> list = new ArrayList<>();
        for (String id : assesPool.keySet()) {
            if (assesPool.get(id).getPlatformCode().equals(code)) {
                list.add(assesPool.get(id));
            }
        }
        return list;
    }

    public PlatformStatus getForcesData(String id) {
        return forceStatusData.get(id);
    }

    /**
     * 初始化兵力
     *
     * @param id
     */
    public void initForce(String id) {
        if (!forceStatusData.containsKey(id)) {
            PlatformStatus status = new PlatformStatus();
            status.setPlatformId(id);
            status.setInitStatus(true);
            status.setActiveStatus(false);
            Platform platform = platformService.getById(id);
            if (platform != null) {
                status.setCode(platform.getCode());
                status.setBeMineSweep(platform.getBeMineSweep());
                status.setBeRealEquipment(platform.getBeRealEquipment());
                status.setName(platform.getName());
                status.setType(platform.getType());
                status.setSpeed(platform.getSpeed());
                status.setKind(platform.getKind());
            }

            forceStatusData.put(id, status);
            log.info("--->兵力" + id + "初始化成功");

        } else {
            PlatformStatus forcesStatus = forceStatusData.get(id);
            if (forcesStatus.getInitStatus()) {
//                log.info("--->兵力" + id + "已经初始化成功，无需重新初始化");
                return;
            } else {
                forcesStatus.setInitStatus(true);
                forcesStatus.setActiveStatus(false);
                forceStatusData.put(id, forcesStatus);
                log.info("--->兵力" + id + "初始化成功");
            }
        }
    }

    /**
     * 向资源中心注册
     *
     * @param id
     */
    public void activeForce(String id) {
        if (forceStatusData.containsKey(id)) {
            PlatformStatus forcesStatus = forceStatusData.get(id);
            if (!forcesStatus.getInitStatus()) {
                throw ExceptionUtils.api(String.format("该兵力未初始化") + id);
            }
            forcesStatus.setActiveStatus(true);
            log.info("--->兵力" + id + "激活成功");
            Platform platform = platformService.seekById(id);
            if (platform != null) {
                forcesStatus.setPlatformId(id);
                forcesStatus.setCode(platform.getCode());
                forcesStatus.setName(platform.getName());
                forcesStatus.setType(platform.getType());
                forcesStatus.setBeMineSweep(platform.getBeMineSweep());
                forcesStatus.setBeRealEquipment(platform.getBeRealEquipment());
                forcesStatus.setSpeed(platform.getSpeed());
                forcesStatus.setKind(platform.getKind());
                forceStatusData.put(id, forcesStatus);
                if (platform == null) {
                    throw ExceptionUtils.api(String.format("数据库没有平台数据"));
                } else {
                    platformPool.put(id, platform);
                    for (Find find : platform.getFinds()) {
                        find.setStatus(ResourceStatus.AVAILABLE);
                        findPool.put(find.getId(), find);
                    }
                    for (Fix fix : platform.getFixes()) {
                        fix.setStatus(ResourceStatus.AVAILABLE);
                        fixPool.put(fix.getId(), fix);
                    }
                    for (Track track : platform.getTracks()) {
                        track.setStatus(ResourceStatus.AVAILABLE);
                        trackPool.put(track.getId(), track);
                    }
                    for (Target target : platform.getTargets()) {
                        target.setStatus(ResourceStatus.AVAILABLE);
                        targetPool.put(target.getId(), target);
                    }
                    for (Engage engage : platform.getEngages()) {
                        engage.setStatus(ResourceStatus.AVAILABLE);
                        engagePool.put(engage.getId(), engage);
                    }
                    for (Asses asses : platform.getAsses()) {
                        asses.setStatus(ResourceStatus.AVAILABLE);
                        assesPool.put(asses.getId(), asses);
                    }
                }
            }

        } else {
            throw ExceptionUtils.api(String.format("该兵力未初始化") + id);
        }
    }

    /**
     * 从资源中心注销
     *
     * @param id
     */
    public void disActiveForce(String id) {
        if (forceStatusData.containsKey(id)) {
            PlatformStatus forcesStatus = forceStatusData.get(id);
            if (!forcesStatus.getInitStatus()) {
                throw ExceptionUtils.api(String.format("该兵力未初始化") + id);
            }
            if (!forcesStatus.getActiveStatus()) {
                throw ExceptionUtils.api(String.format("该兵力已经注销") + id);
            }
            forcesStatus.setActiveStatus(false);
            forceStatusData.put(id, forcesStatus);
            Platform platform = platformService.seekById(id);
            if (platform == null) {
                throw ExceptionUtils.api(String.format("数据库没有平台数据"));
            } else {
                for (Find find : platform.getFinds()) {
                    findPool.remove(find.getId());
                }
                for (Fix fix : platform.getFixes()) {
                    fixPool.remove(fix.getId());
                }
                for (Track track : platform.getTracks()) {
                    trackPool.remove(track.getId());
                }
                for (Target target : platform.getTargets()) {
                    targetPool.remove(target.getId());
                }
                for (Engage engage : platform.getEngages()) {
                    engagePool.remove(engage.getId());
                }
                for (Asses asses : platform.getAsses()) {
                    assesPool.remove(asses.getId());
                }
                platformPool.remove(id);
                log.info("--->兵力" + id + "注销成功");
            }

        } else {
            throw ExceptionUtils.api(String.format("该兵力未初始化") + id);
        }
    }

    /**
     * 兵力删除
     *
     * @param id
     */
    public void deleteForce(String id) {

        if (!forceStatusData.containsKey(id)) {
            throw ExceptionUtils.api(String.format("该兵力未初始化") + id);
        } else {
            PlatformStatus forcesStatus = forceStatusData.get(id);
            forcesStatus.setInitStatus(false);
            forcesStatus.setActiveStatus(false);
            forcesStatus.setCode("");
            forcesStatus.setName("");
            forcesStatus.setPlatformId("");
            forcesStatus.setType("");
            forcesStatus.setKind(-1);
            forceStatusData.put(id, forcesStatus);
            log.info("--->兵力" + id + "注销成功");
            Platform platform = platformService.seekById(id);
            if (platform == null) {
                throw ExceptionUtils.api(String.format("数据库没有平台数据"));
            } else {
                for (Find find : platform.getFinds()) {
                    findPool.remove(find.getId());
                }
                for (Fix fix : platform.getFixes()) {
                    fixPool.remove(fix.getId());
                }
                for (Track track : platform.getTracks()) {
                    trackPool.remove(track.getId());
                }
                for (Target target : platform.getTargets()) {
                    targetPool.remove(target.getId());
                }
                for (Engage engage : platform.getEngages()) {
                    engagePool.remove(engage.getId());
                }
                for (Asses asses : platform.getAsses()) {
                    assesPool.remove(asses.getId());
                }
                platformPool.remove(id);
            }
        }
    }

    /**
     * 清除资源库
     */
    public void cleanForce() {
        //TODO：维护上下线时间的Map清空
        onLineNodes.clear();
        forceStatusData.clear();
        platformPool.clear();
        findPool.clear();
        fixPool.clear();
        targetPool.clear();
        trackPool.clear();
        engagePool.clear();
        assesPool.clear();
        Set<String> sKeys = RedisUtils.getService(19).keys(Constants.POOLING_TIME_ONLINE);
        List<String> lKeys = new ArrayList<String>(sKeys);
        RedisUtils.getService(19).deletes(lKeys);
    }

    public boolean isInited(String id) {

        return forceStatusData.containsKey(id);
    }

    public Boolean sendActivated(String id) {

        if (org.apache.commons.lang3.StringUtils.equals(id, "42")) {
            id = "16385";
        }
        if (org.apache.commons.lang3.StringUtils.equals(id, "43")) {
            id = "20481";
        }
        if (org.apache.commons.lang3.StringUtils.equals(id, "78")) {
            id = "2049";
        }
        if (org.apache.commons.lang3.StringUtils.equals(id, "79")) {
            id = "2305";
        }
        if (org.apache.commons.lang3.StringUtils.equals(id, "87")) {
            id = "24577";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ActivatedModel model = new ActivatedModel();
        model.setId(id);
        model.setType("1");
        HttpEntity<Object> request = new HttpEntity<>(model, headers);
        try {
//            System.out.println("仿真上线节点" + JsonUtils.serialize(model));

            ResponseEntity<String> response = restTemplate.postForEntity(poolingConfig.getSimulationUrlHead() + Constants.OPERATE_FORCE_URL, request, String.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw ExceptionUtils.api("仿真引擎未开启");
            }
        } catch (Exception ex) {
            throw ExceptionUtils.api("仿真引擎未开启");
        }
        return true;

    }

    public Boolean sendDisActivated(String id) {
        if (org.apache.commons.lang3.StringUtils.equals(id, "42")) {
            id = "16385";
        }
        if (org.apache.commons.lang3.StringUtils.equals(id, "43")) {
            id = "20481";
        }
        if (org.apache.commons.lang3.StringUtils.equals(id, "78")) {
            id = "2049";
        }
        if (org.apache.commons.lang3.StringUtils.equals(id, "79")) {
            id = "2305";
        }
        if (org.apache.commons.lang3.StringUtils.equals(id, "87")) {
            id = "24577";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ActivatedModel model = new ActivatedModel();
        model.setId(id);
        model.setType("0");
        HttpEntity<Object> request = new HttpEntity<>(model, headers);
        try {
            System.out.println("仿真下线节点" + JsonUtils.serialize(model));
            ResponseEntity<String> response = restTemplate.postForEntity(poolingConfig.getSimulationUrlHead() + Constants.OPERATE_FORCE_URL, request, String.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw ExceptionUtils.api("仿真引擎未开启");
            }
        } catch (Exception ex) {
            throw ExceptionUtils.api("仿真引擎未开启");
        }
        return true;

    }

    public void onLine(List<String> forces) throws InterruptedException {
        NetNoticeData netNoticeData = new NetNoticeData();
        List<NetPosition> netPositions = new ArrayList<>();
        netNoticeData.setSign(0);

        for (String s : forces) {
            if (metaConfig.getBeNet()) {
//                setOnLineNodes(s);
            }

            Platform platform = platformService.seekById(s);
            NetPosition position = new NetPosition();
            position.setId(s);
            if (platform != null) {
                position.setKind(platform.getKind());
            }
            //获取离线节点位置信息
            if (getPlatformPool().get(s) != null && getPlatformPool().get(s).getPlatformMoveData() != null) {
                PlatformMoveData moveData = getPlatformPool().get(s).getPlatformMoveData();
                position.setLon(moveData.getLon());
                position.setLat(moveData.getLat());
                position.setAlt(moveData.getAlt());
            } else {
//                System.out.println("收到的ID：" + s);
//                System.out.println("平台信息" + JsonUtils.serialize(platform));
                position.setLon(platform.getLon());
                position.setLat(platform.getLat());
                position.setAlt(platform.getAlt());
            }
            netPositions.add(position);
            if (platform.getBeRealEquipment()) {
                String url = metaConfig.getBusUrl() + "api/register";
                RestTemplate template = new RestTemplate();
                JSONObject object = new JSONObject();
                object.put("number", s);
                object.put("type", "1");
                try {
                    template.postForEntity(url, object, String.class);
                } catch (Exception e) {

                }
                System.out.println("已向总线发送实装上线请求id：" + s);
            }
        }
        netNoticeData.setNodesInfo(netPositions);
        String url = "";
        if (metaConfig.getBeNet()) {
            url = metaConfig.getBusUrl() + "free/pooling/resource/init/position";
            String json = JSON.toJSONString(netNoticeData);
            RestTemplate template = new RestTemplate();
            try {
                template.postForEntity(url, json, String.class);
            } catch (Exception e) {

            }
            System.out.println("已向总线发送当前态势");
        } else {
            url = metaConfig.getBusUrl() + "api/postTest";
            RestTemplate template = new RestTemplate();
            String json = JSON.toJSONString("noNet");
            try {
                template.postForEntity(url, json, String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //上线操作
        for (String platformId : forces) {

            List<String> list = new ArrayList<>();
            list.add(platformId);
            mqttMsgProducer.producerMsg(poolingConfig.getActivateTopic(), JsonUtils.serialize(list));
            Thread.sleep(1);
        }

        System.out.println("上线：已向网络仿真发送节点位置信息");

        return;
    }

    public void offLine(List<String> forces) {
        NetNoticeData netNoticeData = new NetNoticeData();
        List<NetPosition> netPositions = new ArrayList<>();
        netNoticeData.setSign(1);

        for (String platformId : forces) {

            //通知下线
            NetPosition position = new NetPosition();
            position.setId(platformId);
            netPositions.add(position);
            Platform platform = platformService.seekById(platformId);
            if (platform.getBeRealEquipment()) {
                String url = metaConfig.getBusUrl() + "api/register";
                RestTemplate template = new RestTemplate();
                JSONObject object = new JSONObject();
                object.put("number", platformId);
                object.put("type", "1");
                try {
                    template.postForEntity(url, object, String.class);
                } catch (Exception e) {

                }
                System.out.println("已向总线发送实装下线请求id：" + platformId);
            }
        }
        netNoticeData.setNodesInfo(netPositions);
        String url = "";
        if (metaConfig.getBeNet()) {
            url = metaConfig.getBusUrl() + "free/pooling/resource/init/position";
            String json = JSON.toJSONString(netNoticeData);
            RestTemplate template = new RestTemplate();
            try {
                template.postForEntity(url, json, String.class);
            } catch (Exception e) {

            }
            System.out.println("已向总线发送当前态势");

        } else {
            url = metaConfig.getBusUrl() + "api/postTest";
            RestTemplate template = new RestTemplate();
            String json = JSON.toJSONString("noNet");
            try {
                template.postForEntity(url, json, String.class);
            } catch (Exception e) {

            }
        }
        //下线操作
        for (String platformId : forces) {
            PlatformStatus forcesData = getForcesData(platformId);
            sendDisActivated(platformId);
            disActiveForce(platformId);
            forcesData.setActiveStatus(false);
        }
        System.out.println("下线：已向网络仿真发送节点位置信息");

        return;
    }


    public Map<String, Object> selectResource(Command command, Boolean beNet) {

        Map<String, Object> result = new HashMap<String, Object>();
        CommandType type = getCommandType(command);

        List<Find> finds = getFindPool(type);
        List<Fix> fixes = new ArrayList<>();
        List<Track> tracks = new ArrayList<>();
        List<Target> targets = new ArrayList<>();
        List<Engage> engages = new ArrayList<>();
        List<Asses> asses = new ArrayList<>();

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

            for (Find model : getFind(start, end, command)) {
                if (!finds.contains(model)) {
                    finds.add(model);
                }
            }
            for (Fix model : getFix(start, end, command)) {
                if (!fixes.contains(model)) {
                    fixes.add(model);
                }
            }
            for (Track model : getTrack(start, end, command)) {
                if (!tracks.contains(model)) {
                    tracks.add(model);
                }
            }
            for (Engage model : getEngage(start, end, command)) {
                if (!engages.contains(model)) {
                    engages.add(model);
                }
            }
            for (Target model : getTarget(engages)) {
                if (!targets.contains(model)) {
                    targets.add(model);
                }
            }
            for (Asses model : getAsses(start, end, command)) {
                if (!asses.contains(model)) {
                    asses.add(model);
                }
            }
        }

        Short[][] netState1 = new Short[150][150];
        for (int i = 0; i < 150; i++) {
            for (int j = 0; j < 150; j++) {
                ThreadLocalRandom tlr = ThreadLocalRandom.current();
                int random = tlr.nextInt(-1, 1000);
                netState1[i][j] = (short) random;
            }
        }
        Short[][] netState = getNetData().getMgmt_150x150();

        List<PlatformStatus> platforms = getAll().values().stream().collect(Collectors.toList());
        result.put("find", finds);
        result.put("fix", fixes);
        result.put("track", tracks);
        result.put("target", targets);
        result.put("engage", engages);
        result.put("asses", asses);
        result.put("platform", platforms);
        if (beNet) {
            result.put("netState", netState);
        } else {
            result.put("netState", netState1);
        }
        return result;

    }

    public GeometryUtils.Point moveData2Point(PlatformMoveData moveData) {
        GeometryUtils.Point point = new GeometryUtils.Point();
        point.setX(moveData.getLon());
        point.setY(moveData.getLat());
        return point;
    }

    public List<Find> getFind(GeometryUtils.Point start, GeometryUtils.Point end, Command command) {
        List<Find> list = new ArrayList<>();
        List<Find> finds = getFindPool(getCommandType(command));

        for (Find find : finds) {
            PlatformMoveData moveData = getPlatformPool().get(find.getPlatformCode()).getPlatformMoveData();
            if (moveData != null) {
                GeometryUtils.Point point = moveData2Point(moveData);

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
        }

        list.sort(Comparator.comparing(Find::getDistance));

        return list;
    }

    public List<Fix> getFix(GeometryUtils.Point start, GeometryUtils.Point end, Command command) {
        List<Fix> list = new ArrayList<>();
        List<Fix> fixes = getFixPool(getCommandType(command));

        for (Fix fix : fixes) {
            PlatformMoveData moveData = getPlatformPool().get(fix.getPlatformCode()).getPlatformMoveData();
            if (moveData != null) {
                GeometryUtils.Point point = moveData2Point(moveData);

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
        }

        list.sort(Comparator.comparing(Fix::getDistance));
        return list;
    }

    public List<Target> getTarget(List<Engage> engages) {
        List<Target> list = new ArrayList<>();
        for (Engage model : engages) {
            if (getTargetByPlatform(model.getPlatformCode()).size() != 0) {
                list.add(getTargetByPlatform(model.getPlatformCode()).get(0));
            } else {
                //TODO:magic value 36
                list.add(getTargetByPlatform("100").get(0));
            }
        }

        return list;
    }

    public List<Track> getTrack(GeometryUtils.Point start, GeometryUtils.Point end, Command command) {
        List<Track> list = new ArrayList<>();
        List<Track> tracks = getTrackPool(getCommandType(command));

        for (Track track : tracks) {
            PlatformMoveData moveData = getPlatformPool().get(track.getPlatformCode()).getPlatformMoveData();
            if (moveData != null) {
                GeometryUtils.Point point = moveData2Point(moveData);

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
        }

        list.sort(Comparator.comparing(Track::getDistance));
        return list;
    }

    public List<Asses> getAsses(GeometryUtils.Point start, GeometryUtils.Point end, Command command) {
        List<Asses> list = new ArrayList<>();
        List<Asses> assesList = getAssesPool(getCommandType(command));

        for (Asses asses : assesList) {
            PlatformMoveData moveData = getPlatformPool().get(asses.getPlatformCode()).getPlatformMoveData();
            if (moveData != null) {
                GeometryUtils.Point point = moveData2Point(moveData);

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
        }

        list.sort(Comparator.comparing(Asses::getDistance));
        return list;
    }

    public List<Engage> getEngage(GeometryUtils.Point start, GeometryUtils.Point end, Command command) {
        List<Engage> list = new ArrayList<>();
        List<Engage> engages = getEngagePool(getCommandType(command));

        for (Engage engage : engages) {
            PlatformMoveData moveData = getPlatformPool().get(engage.getPlatformCode()).getPlatformMoveData();
            if (moveData != null) {
                GeometryUtils.Point point = moveData2Point(moveData);

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
        }

        list.sort(Comparator.comparing(Engage::getDistance));
        return list;
    }

    public List<NetLinkModel> getForceNetLink(PlatformStatus platformStatus, List<PlatformStatus> platformList) {
        List<NetLinkModel> list = new ArrayList<>();
        Short[][] netState = new Short[150][150];
        if (metaConfig.getBeNet()) {
            netState = getNetLinkData().getMgmt_150x150();
        } else {
            if (state != null) {
                netState = state;
            }
//            for (int i = 0; i < 150; i++) {
//                for (int j = 0; j < 150; j++) {
//                    ThreadLocalRandom tlr = ThreadLocalRandom.current();
//                    int random = tlr.nextInt(-1, 1000);
//                    netState[i][j] = (short) random;
//                }
//            }
        }
        for (PlatformStatus p : platformList) {
            int cId = Integer.parseInt(p.getPlatformId());
            int rId = Integer.parseInt(platformStatus.getPlatformId());
            short delay = netState[cId][rId];
            if (cId != rId && delay >= 0 && p.getName() != null) {
                NetLinkModel n = new NetLinkModel();
                n.setId(p.getPlatformId());
                n.setType("0");
                n.setTransRate(1.0);
                n.setBandWidth(200.0);
                n.setMaxTimeDelay((int) delay);
                n.setMinTimeDelay((int) delay);
                list.add(n);
            }
        }

        return list;
    }

}
