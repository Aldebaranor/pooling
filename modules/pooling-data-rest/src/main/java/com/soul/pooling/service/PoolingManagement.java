package com.soul.pooling.service;

import com.egova.exception.ExceptionUtils;
import com.soul.pooling.config.Constants;
import com.soul.pooling.config.PoolingConfig;
import com.soul.pooling.entity.*;
import com.soul.pooling.entity.enums.CommandType;
import com.soul.pooling.entity.enums.ResourceStatus;
import com.soul.pooling.model.ActivatedModel;
import com.soul.pooling.model.PlatformStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Priority;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
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
    private PoolingConfig poolingConfig;


    private final ConcurrentMap<String, PlatformStatus> forceStatusData = new ConcurrentHashMap();

    private final ConcurrentMap<String, Platform> platformPool = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Find> findPool = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Fix> fixPool = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Track> trackPool = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Target> targetPool = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Engage> engagePool = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Asses> assesPool = new ConcurrentHashMap<>();

    public Map<String, PlatformStatus> getAll() {
        return forceStatusData;
    }

    public Map<String, Platform> getPlatformPool() {
        return platformPool;
    }

    public Find getFindById(String id){
        return findPool.get(id);
    }

    public Fix getFixById(String id){
        return fixPool.get(id);
    }

    public Track getTrackById(String id){
        return trackPool.get(id);
    }

    public Target getTargetById(String id){
        return targetPool.get(id);
    }

    public Engage getEngageById(String id){
        return engagePool.get(id);
    }

    public Asses getAssesById(String id){
        return assesPool.get(id);
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
        if (type == null) {
            return result;
        }

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
            result = result.stream().filter(q -> q.getMaxFireDepthUnderSea() > 0).collect(Collectors.toList());
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
            forceStatusData.put(id, status);
            log.info("--->兵力" + id + "初始化成功");

        } else {
            PlatformStatus forcesStatus = forceStatusData.get(id);
            if (forcesStatus.getInitStatus()) {
                log.info("--->兵力" + id + "已经初始化成功，无需重新初始化");
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
                throw ExceptionUtils.api(String.format("该兵力未初始化"));
            }
            forcesStatus.setActiveStatus(true);
            log.info("--->兵力" + id + "激活成功");
            Platform platform = platformService.seekById(id);
            forcesStatus.setCode(platform.getCode());
            forcesStatus.setName(platform.getName());
            forcesStatus.setType(platform.getType());
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

        } else {
            throw ExceptionUtils.api(String.format("该兵力未初始化"));
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
                throw ExceptionUtils.api(String.format("该兵力未初始化"));
            }
            if (!forcesStatus.getActiveStatus()) {
                throw ExceptionUtils.api(String.format("该兵力已经注销"));
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
            throw ExceptionUtils.api(String.format("该兵力未初始化"));
        }
    }


    /**
     * 兵力删除
     *
     * @param id
     */
    public void deleteForce(String id) {
        if (!forceStatusData.containsKey(id)) {
            throw ExceptionUtils.api(String.format("该兵力未初始化"));
        } else {
            PlatformStatus forcesStatus = forceStatusData.get(id);
            forcesStatus.setInitStatus(false);
            forcesStatus.setActiveStatus(false);
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
        forceStatusData.clear();
        platformPool.clear();
        findPool.clear();
        fixPool.clear();
        targetPool.clear();
        trackPool.clear();
        engagePool.clear();
        assesPool.clear();
    }

    public boolean isInited(String id) {
        return forceStatusData.containsKey(id);
    }

    public Boolean sendActivated(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ActivatedModel model = new ActivatedModel();
        model.setId(id);
        model.setType("1");
        HttpEntity<Object> request = new HttpEntity<>(model, headers);
        try {
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ActivatedModel model = new ActivatedModel();
        model.setId(id);
        model.setType("0");
        HttpEntity<Object> request = new HttpEntity<>(model, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(poolingConfig.getSimulationUrlHead() + Constants.OPERATE_FORCE_URL, request, String.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw ExceptionUtils.api("仿真引擎未开启");
            }
        } catch (Exception ex) {
            throw ExceptionUtils.api("仿真引擎未开启");
        }
        return true;

    }


}
