package com.soul.pooling.controller.free;

import com.alibaba.fastjson.JSONObject;
import com.egova.exception.ExceptionUtils;
import com.egova.redis.RedisUtils;
import com.egova.web.annotation.Api;
import com.flagwind.commons.StringUtils;
import com.soul.pooling.condition.PoolingCondition;
import com.soul.pooling.condition.ResourceCondition;
import com.soul.pooling.config.Constants;
import com.soul.pooling.config.MetaConfig;
import com.soul.pooling.config.PoolingConfig;
import com.soul.pooling.entity.*;
import com.soul.pooling.entity.enums.CommandType;
import com.soul.pooling.model.*;
import com.soul.pooling.mqtt.producer.MqttMsgProducer;
import com.soul.pooling.netty.NettyUdpClient;
import com.soul.pooling.service.PlatformService;
import com.soul.pooling.service.PoolingManagement;
import com.soul.pooling.service.PoolingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


/**
 * @Description:
 * @Author: nemo
 * @Date: 2022/6/22
 */
@Slf4j
@RestController
@RequestMapping("/free/pooling")
@RequiredArgsConstructor
public class PoolingController {

    @Autowired
    private PoolingManagement management;

    @Autowired
    private MqttMsgProducer mqttMsgProducer;

    @Autowired
    private PoolingConfig poolingConfig;

    @Autowired
    private MetaConfig metaConfig;

    @Autowired
    private PoolingService poolingService;

    @Autowired
    private PlatformService platformService;

    @Autowired(required = false)
    private NettyUdpClient nettyUdpClient;

    /**
     * 接收网络通断情况
     *
     * @param data
     */
    @Api
    @PostMapping(value = "/net/state")
    public Boolean getNetState(@RequestBody NetStatusData data) {
        //获取网络通断表及时间并进行处理
        management.setNetData(data);
        return true;
    }

    @Api
    @PostMapping(value = "/net/link")
    public Boolean getNetLink(@RequestBody NetStatusData data) {
        management.setNetLinkData(data);
        return true;
    }

    /**
     * 重启节点
     *
     * @param forces
     * @return
     */
    @Api
    @PostMapping(value = "/restart/platform")
    public JSONObject restart(@RequestBody List<String> forces) throws InterruptedException {
        Long startTime = System.currentTimeMillis();
        System.out.println("重启请求开始时间：" + startTime);
        management.offLine(forces);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        management.clearTimeRecords();
        if (metaConfig.getBeNet()) {
            for (String s : forces) {
                if (management.getOnLineNodes().contains(s)) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("500", "包含上线中的节点" + s);
                    return jsonObject;
                }
            }
        }
        management.onLine(forces);
        while (management.getTimeRecords().get("endTime") == null) {

        }
        if (metaConfig.getBeNet()) {
            for (String s : forces) {
                management.removeOnLineNodes(s);
            }
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("重启请求结束时间：" + endTime);
        management.clearTimeRecords();
        Long totalTime = endTime - startTime;
        for (String id : forces) {
            management.setTimeRecords(id, totalTime);
            management.setLastRecords(id, totalTime);
        }
        System.out.println("重连耗时：" + totalTime);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("200", "重连成功，耗时" + totalTime);
        return jsonObject;
    }

    @Api
    @GetMapping(value = "/force/net")
    public Map<String, List<NetLinkModel>> forceNet() {
        Map<String, List<NetLinkModel>> netMap = new HashMap<>();
        List<PlatformStatus> platformList = getPlatformList(null);
        for (PlatformStatus p : platformList) {
            if (p.getName() != null) {
                netMap.put(p.getPlatformId(), management.getForceNetLink(p, platformList));
            }
        }

        return netMap;
    }

    @Api
    @PostMapping(value = "/fake/net")
    public Short[][] fake(@RequestBody Map<String, List<NetLinkModel>> map) {
        Short[][] netState = new Short[150][150];
        Short[] arr = new Short[150];
        short delay = -1;
        Arrays.fill(arr, delay);
        Arrays.fill(netState, arr);
        for (Map.Entry<String, List<NetLinkModel>> entry : map.entrySet()) {
            List<NetLinkModel> list = entry.getValue();
            for (NetLinkModel netLink : list) {
                netState[Integer.valueOf(entry.getKey())][Integer.valueOf(netLink.getId())] = Short.valueOf(netLink.getMaxTimeDelay().toString());
            }
        }
        management.setState(netState);
        return netState;
    }

    /**
     * 开始试验，清除缓存
     *
     * @param experiment
     * @return
     */
    @Api
    @GetMapping(value = "/start/{experiment}")
    public Boolean pumpStart(@PathVariable String experiment) {
        if (StringUtils.isBlank(experiment)) {
            throw ExceptionUtils.api("structName can not be null");
        }
        management.cleanForce();

        return true;
    }

    /**
     * 获取所有上电的节点信息
     *
     * @return
     */
    @Api
    @GetMapping(value = "/platform")
    public List<PlatformStatus> platform() {

        return getPlatformList(null);
    }

    /**
     * 获取上电节点上电时间
     *
     * @return
     */
    @Api
    @GetMapping(value = "/online/time")
    public Map<String, Object> getOnLineTime() {
        Map<String, Object> time = new ConcurrentHashMap<>();
        Set<Object> keys = RedisUtils.getService(19).getTemplate().opsForHash().keys(Constants.POOLING_TIME_ONLINE);
        for (Object key : keys) {
            time.put(key.toString(), RedisUtils.getService(19).getTemplate().opsForHash().get(Constants.POOLING_TIME_ONLINE, key.toString()));
        }
        return time;
    }


    /**
     * 接收resource的初始化
     *
     * @param forces
     * @return
     */
    @Api
    @PostMapping(value = "/init/platform")
    public Boolean forcesInit(@RequestBody List<String> forces) {
        for (String id : forces) {
            management.initForce(id);
        }
        return true;
    }

    /**
     * 接收试验管理的注册指令，通知节点进行入云注册
     *
     * @param forces
     * @return
     */
    @Api
    @PostMapping(value = "/activate/platform")
    public JSONObject forcesActivate(@RequestBody List<String> forces) throws InterruptedException {
        if (metaConfig.getBeNet()) {
            for (String s : forces) {
                if (management.getOnLineNodes().contains(s)) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("500", "包含上线中的节点" + s);
                    return jsonObject;
                }
            }
        }
        Long startTime = System.currentTimeMillis();
        System.out.println("上线请求开始时间：" + startTime);
        management.onLine(forces);
//        if (!metaConfig.getBeNet()) {
//            management.setNetData(null);
//        }
        while (management.getTimeRecords().get("endTime") == null) {

        }
        if (metaConfig.getBeNet()) {
            for (String s : forces) {
                management.removeOnLineNodes(s);
            }
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("上线请求结束时间：" + endTime);
        management.clearTimeRecords();
        Long totalTime = endTime - startTime;
        for (String id : forces) {
            management.setTimeRecords(id, totalTime);
            management.setLastRecords(id, totalTime);
        }
        System.out.println("上线耗时：" + totalTime);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("200", "上线成功，耗时：" + totalTime);
        return jsonObject;
    }

    /**
     * 接收resource的入云注册
     *
     * @param platformId
     * @return
     */
    @Api
    @GetMapping(value = "/activated/{platformId}")
    public Boolean forcesActivated(@PathVariable String platformId) {
        PlatformStatus forcesData = management.getForcesData(platformId);
        if (forcesData == null) {
            throw ExceptionUtils.api(String.format("该兵力未注册") + platformId);
        }
        if (!forcesData.getInitStatus()) {
            throw ExceptionUtils.api(String.format("该兵力未初始化") + platformId);
        }

        forcesData.setActiveStatus(true);
        //往redis写上线时间
        RedisUtils.getService(19).getTemplate().opsForHash().put(Constants.POOLING_TIME_ONLINE, platformId, String.valueOf(System.currentTimeMillis()));
        //通知上线

        management.sendActivated(platformId);

        management.activeForce(platformId);
        return true;
    }

    /**
     * 接收resource的注销
     *
     * @param platformId
     * @return
     */

    @Api
    @GetMapping(value = "/dis-activated/{platformId}")
    public Boolean forcesDisActivated(@PathVariable String platformId) {
        PlatformStatus forcesData = management.getForcesData(platformId);
        if (forcesData == null) {
            throw ExceptionUtils.api(String.format("该兵力未注册") + platformId);
        }
        if (!forcesData.getInitStatus()) {
            throw ExceptionUtils.api(String.format("该兵力未初始化") + platformId);
        }
        if (!forcesData.getActiveStatus()) {
            throw ExceptionUtils.api(String.format("该兵力未被激活") + platformId);
        }

        //通知下线

        management.sendDisActivated(platformId);

        management.disActiveForce(platformId);

        forcesData.setActiveStatus(false);
        return true;
    }

    @Api
    @PostMapping(value = "/dis-activated/platformIds")
    public JSONObject forcesDisActivatedBatch(@RequestBody List<String> platformIds) {
        Long startTime = System.currentTimeMillis();
        System.out.println("下线请求开始时间：" + startTime);
        management.offLine(platformIds);
        while (management.getTimeRecords().get("endTime") == null) {

        }
        Long endTime = System.currentTimeMillis();
        System.out.println("下线请求结束时间：" + endTime);
        management.clearTimeRecords();
        Long totalTime = endTime - startTime;
        for (String id : platformIds) {

            management.setTimeRecords(id, totalTime);
            management.setLastRecords(id, totalTime);
        }
        System.out.println("下线操作耗时：" + totalTime);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("200", "下线成功，耗时：" + totalTime);
        return jsonObject;
    }

    /**
     * 根据平台id获取movedata
     *
     * @param platformId
     * @return
     */
    @Api
    @GetMapping(value = "/platform/moveData/{platformId}")
    public PlatformMoveData platformMoveData(@PathVariable String platformId) {
        return management.getPlatformPool().get(platformId).getPlatformMoveData();
    }

    @Api
    @PostMapping(value = "/weapon/status")
    public void weaponNum(@RequestBody ResourceModel model) {
        Engage engage = management.getEngageByPlatform(model.getPlatformCode()).stream().filter(q -> q.getDeviceCode().equals(model.getDeviceCode())).collect(Collectors.toList()).get(0);
        int num = engage.getNumber() - model.getNum();
        engage.setNumber(num);
    }


    @Api
    @PostMapping(value = "/find")
    public Find getFindById(@RequestBody JSONObject id) {
        return management.getFindById(id.getString("id"));
    }

    @Api
    @PostMapping(value = "/fix")
    public Fix getFixById(@RequestBody JSONObject id) {
        return management.getFixById(id.getString("id"));
    }

    @Api
    @PostMapping(value = "/track")
    public Track getTrackById(@RequestBody JSONObject id) {
        return management.getTrackById(id.getString("id"));
    }

    @Api
    @PostMapping(value = "/target")
    public Target getTargetById(@RequestBody JSONObject id) {
        return management.getTargetById(id.getString("id"));
    }

    @Api
    @PostMapping(value = "/engage")
    public Engage getEngageById(@RequestBody JSONObject id) {
        return management.getEngageById(id.getString("id"));
    }

    @Api
    @PostMapping(value = "/asses")
    public Asses getAssesById(@RequestBody JSONObject id) {
        return management.getAssesById(id.getString("id"));
    }

    @Api
    @PostMapping(value = "/list/platform")
    public List<PlatformStatus> getPlatformList(@RequestBody PoolingCondition condition) {

        List<PlatformStatus> list = new ArrayList<>();
        for (Map.Entry<String, PlatformStatus> map : management.getAll().entrySet()) {
            list.add(map.getValue());
        }

        for (PlatformStatus platform : list) {
            String onlineTime = String.valueOf(RedisUtils.getService(19).getTemplate().opsForHash().get(Constants.POOLING_TIME_ONLINE, platform.getPlatformId()));
            if (StringUtils.isBlank(onlineTime) || onlineTime.equals("null")) {
                platform.setLastOnlineTime(0L);
            } else {
                platform.setLastOnlineTime(Long.valueOf(onlineTime));
            }
//            Long opTime = management.getTimeRecords().get(platform.getPlatformId());
            if (platform.getActiveStatus()) {
                Long opTime = management.getLastRecords().get(platform.getPlatformId());
                if (opTime == null || opTime.equals(null)) {
                    opTime = 0L;
                }
                platform.setLastOperationUsedTime(opTime);
            }

        }

        if (condition == null) {
            return list;
        }

        if (!StringUtils.isBlank(condition.getName())) {

            list = list.stream().filter(q -> q.getName() != null && q.getName().contains(condition.getName())).collect(Collectors.toList());
        }
        if (!StringUtils.isBlank(condition.getPlatformId())) {


            list = list.stream().filter(q -> q.getPlatformId() != null && StringUtils.equals(condition.getPlatformId(), q.getPlatformId())).collect(Collectors.toList());

        }
        if (!StringUtils.isBlank(condition.getCode())) {
            list = list.stream().filter(q -> q.getCode() != null && q.getCode().equals(condition.getCode())).collect(Collectors.toList());
        }
        if (condition.getActiveStatus() != null) {


            list = list.stream().filter(q -> q.getActiveStatus() != null && (q.getActiveStatus().equals(condition.getActiveStatus()))).collect(Collectors.toList());

        }
        if (!StringUtils.isBlank(condition.getType())) {

            list = list.stream().filter(q -> q.getType() != null && StringUtils.equals(condition.getType(), q.getType())).collect(Collectors.toList());

        }
        if (!StringUtils.isBlank(condition.getKind())) {

            list = list.stream().filter(q -> q.getKind().toString() != null && q.getKind().toString().contains(condition.getKind())).collect(Collectors.toList());
        }
        return list;

    }

    @Api
    @PostMapping(value = "/list/find")
    public List<ResourceModel> getFindList(@RequestBody ResourceCondition condition) {

        List<Find> collect = management.getFindPool(null);
        List<ResourceModel> list = poolingService.findToList(collect);

        if (condition == null) {
            return list;
        }

        if (!StringUtils.isBlank(condition.getName())) {
            list = list.stream().filter(q -> q.getDeviceType().contains(condition.getName())).collect(Collectors.toList());
        }
        if (!StringUtils.isBlank(condition.getId())) {
            list = list.stream().filter(q -> StringUtils.equals(condition.getId(), q.getId())).collect(Collectors.toList());

        }
        if (!StringUtils.isBlank(condition.getPlatformCode())) {
            list = list.stream().filter(q -> StringUtils.equals(condition.getPlatformCode(), q.getPlatformCode())).collect(Collectors.toList());

        }
        if (!StringUtils.isBlank(condition.getType())) {
            list = list.stream().filter(q -> StringUtils.equals(condition.getType(), q.getType())).collect(Collectors.toList());

        }

        return list;

    }


    @Api
    @PostMapping(value = "/list/fix")
    public List<ResourceModel> getFindLix(@RequestBody ResourceCondition condition) {

        List<Fix> collect = management.getFixPool(null);
        List<ResourceModel> list = poolingService.fixToList(collect);

        if (condition == null) {
            return list;
        }

        if (!StringUtils.isBlank(condition.getName())) {
            list = list.stream().filter(q -> q.getDeviceType().contains(condition.getName())).collect(Collectors.toList());
        }
        if (!StringUtils.isBlank(condition.getId())) {
            list = list.stream().filter(q -> StringUtils.equals(condition.getId(), q.getId())).collect(Collectors.toList());

        }
        if (!StringUtils.isBlank(condition.getPlatformCode())) {
            list = list.stream().filter(q -> StringUtils.equals(condition.getPlatformCode(), q.getPlatformCode())).collect(Collectors.toList());

        }
        if (!StringUtils.isBlank(condition.getType())) {
            list = list.stream().filter(q -> StringUtils.equals(condition.getType(), q.getType())).collect(Collectors.toList());

        }

        return list;

    }


    @Api
    @PostMapping(value = "/list/track")
    public List<ResourceModel> getTrackList(@RequestBody ResourceCondition condition) {

        List<Track> collect = management.getTrackPool(null);
        List<ResourceModel> list = poolingService.trackToList(collect);

        if (condition == null) {
            return list;
        }

        if (!StringUtils.isBlank(condition.getName())) {
            list = list.stream().filter(q -> q.getDeviceType().contains(condition.getName())).collect(Collectors.toList());
        }
        if (!StringUtils.isBlank(condition.getId())) {
            list = list.stream().filter(q -> StringUtils.equals(condition.getId(), q.getId())).collect(Collectors.toList());

        }
        if (!StringUtils.isBlank(condition.getPlatformCode())) {
            list = list.stream().filter(q -> StringUtils.equals(condition.getPlatformCode(), q.getPlatformCode())).collect(Collectors.toList());

        }
        if (!StringUtils.isBlank(condition.getType())) {
            list = list.stream().filter(q -> StringUtils.equals(condition.getType(), q.getType())).collect(Collectors.toList());

        }

        return list;

    }

    @Api
    @PostMapping(value = "/list/target")
    public List<ResourceModel> getTargetList(@RequestBody ResourceCondition condition) {

        List<Target> collect = management.getTargetPool(null);
        List<ResourceModel> list = poolingService.targetToList(collect);


        if (condition == null) {
            return list;
        }

        if (!StringUtils.isBlank(condition.getName())) {
            list = list.stream().filter(q -> q.getDeviceType().contains(condition.getName())).collect(Collectors.toList());
        }
        if (!StringUtils.isBlank(condition.getId())) {
            list = list.stream().filter(q -> StringUtils.equals(condition.getId(), q.getId())).collect(Collectors.toList());

        }
        if (!StringUtils.isBlank(condition.getPlatformCode())) {
            list = list.stream().filter(q -> StringUtils.equals(condition.getPlatformCode(), q.getPlatformCode())).collect(Collectors.toList());

        }
        if (!StringUtils.isBlank(condition.getType())) {
            list = list.stream().filter(q -> StringUtils.equals(condition.getType(), q.getType())).collect(Collectors.toList());

        }

        return list;
    }


    @Api
    @PostMapping(value = "/list/engage")
    public List<ResourceModel> getEngageList(@RequestBody ResourceCondition condition) {

        List<Engage> collect = management.getEngagePool(null);
        List<ResourceModel> list = poolingService.engageToList(collect);


        if (condition == null) {
            return list;
        }

        if (!StringUtils.isBlank(condition.getName())) {
            list = list.stream().filter(q -> q.getDeviceType().contains(condition.getName())).collect(Collectors.toList());
        }
        if (!StringUtils.isBlank(condition.getId())) {
            list = list.stream().filter(q -> StringUtils.equals(condition.getId(), q.getId())).collect(Collectors.toList());

        }
        if (!StringUtils.isBlank(condition.getPlatformCode())) {
            list = list.stream().filter(q -> StringUtils.equals(condition.getPlatformCode(), q.getPlatformCode())).collect(Collectors.toList());

        }
        if (!StringUtils.isBlank(condition.getType())) {
            list = list.stream().filter(q -> StringUtils.equals(condition.getType(), q.getType())).collect(Collectors.toList());

        }

        return list;

    }

    @Api
    @PostMapping(value = "/list/asses")
    public List<ResourceModel> getAssesList(@RequestBody ResourceCondition condition) {

        List<Asses> collect = management.getAssesPool(null);
        List<ResourceModel> list = poolingService.assesToList(collect);

        if (condition == null) {
            return list;
        }

        if (!StringUtils.isBlank(condition.getName())) {
            list = list.stream().filter(q -> q.getDeviceType().contains(condition.getName())).collect(Collectors.toList());
        }
        if (!StringUtils.isBlank(condition.getId())) {
            list = list.stream().filter(q -> StringUtils.equals(condition.getId(), q.getId())).collect(Collectors.toList());

        }
        if (!StringUtils.isBlank(condition.getPlatformCode())) {
            list = list.stream().filter(q -> StringUtils.equals(condition.getPlatformCode(), q.getPlatformCode())).collect(Collectors.toList());

        }
        if (!StringUtils.isBlank(condition.getType())) {
            list = list.stream().filter(q -> StringUtils.equals(condition.getType(), q.getType())).collect(Collectors.toList());

        }

        return list;

    }

    @Api
    @GetMapping(value = "/all/platform")
    public List<PlatformStatus> getPlatformAll() {

        return management.getAll().values().stream().collect(Collectors.toList());
    }

    @Api
    @GetMapping(value = "/all/find")
    public List<Find> getFindAll() {
        return management.getFindPool(null);
    }


    @Api
    @GetMapping(value = "/all/fix")
    public List<Fix> getFixAll() {

        return management.getFixPool(null);
    }


    @Api
    @GetMapping(value = "/all/track")
    public List<Track> getTrackAll() {

        return management.getTrackPool(null);
    }

    @Api
    @GetMapping(value = "/all/target")
    public List<Target> getTargetAll() {

        return management.getTargetPool(null);

    }


    @Api
    @GetMapping(value = "/all/engage")
    public List<Engage> getEngageAll() {

        return management.getEngagePool(null);
    }

    @Api
    @GetMapping(value = "/all/asses")
    public List<Asses> getAssesAll() {

        return management.getAssesPool(null);
    }

//    @Api
//    @PostMapping(value = "/all-resource")
//    public Map<String, Object> allResource(@RequestBody Command command) {
//
//        return management.selectResource(command, metaConfig.getBeNet());
//    }

    @Api
    @PostMapping(value = "/all-resource")
    public Map<String, Object> allResource(@RequestBody Command command) {
        Map<String, Object> result = new HashMap<String, Object>();
        CommandType type = management.getCommandType(command);

        List<Find> finds = management.getFindPool(type);
        List<Fix> fixes = new ArrayList<>();
        List<Track> tracks = new ArrayList<>();
        List<Target> targets = new ArrayList<>();
        List<Engage> engages = new ArrayList<>();
        List<Asses> asses = new ArrayList<>();
        Short[][] netState1 = new Short[150][150];
        for (int i = 0; i < 150; i++) {
            for (int j = 0; j < 150; j++) {
                ThreadLocalRandom tlr = ThreadLocalRandom.current();
                int random = tlr.nextInt(1, 1000);
                netState1[i][j] = (short) random;
            }
        }

        Short[][] netState = management.getNetData().getMgmt_150x150();
        if (type != CommandType.SEARCH) {
            fixes = management.getFixPool(type);
            tracks = management.getTrackPool(type);
            targets = management.getTargetPool(type);
            engages = management.getEngagePool(type);
            asses = management.getAssesPool(type);

        }
        List<PlatformStatus> platforms = management.getAll().values().stream().collect(Collectors.toList());
        result.put("find", finds);
        result.put("fix", fixes);
        result.put("track", tracks);
        result.put("target", targets);
        result.put("engage", engages);
        result.put("asses", asses);
        result.put("platform", platforms);
        if (metaConfig.getBeNet()) {
            result.put("netState", netState);
        } else {
            result.put("netState", netState1);
        }
        return result;
    }

}
