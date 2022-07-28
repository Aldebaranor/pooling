package com.soul.pooling.service;

import com.egova.exception.ExceptionUtils;
import com.soul.pooling.entity.Platform;
import com.soul.pooling.entity.Sensor;
import com.soul.pooling.entity.Weapon;
import com.soul.pooling.model.PlatformStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Priority;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * @Description:
 * @Author: nemo
 * @Date: 2022/4/1
 */
@Service
@Slf4j
@Priority(5)
public class StatusManagement {

    @Autowired
    private PlatformService platformService;


    private final ConcurrentMap<String, PlatformStatus> forceStatusData = new ConcurrentHashMap();

    private final ConcurrentMap<String, Platform> platformPool = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Sensor> sensorPool = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Weapon> weaponPool = new ConcurrentHashMap<>();

    public Map<String, PlatformStatus> getAll() {
        return forceStatusData;
    }

    public Map<String, Platform> getAllPlatform(){ return platformPool;}

    public PlatformStatus getForcesData(String id) {
        return forceStatusData.get(id);
    }

    /**
     * 初始化兵力
     * @param id
     */
    public void initForce(String id){
        if(!forceStatusData.containsKey(id)){
            PlatformStatus status = new PlatformStatus();
            status.setPlatformId(id);
            status.setInitStatus(true);
            status.setActiveStatus(false);
            forceStatusData.put(id,status);
            log.info("--->兵力"+id+"初始化成功");

        }else{
            PlatformStatus forcesStatus = forceStatusData.get(id);
            if(forcesStatus.getInitStatus()){
                log.info("--->兵力"+id+"已经初始化成功，无需重新初始化");
                return;
            }else{
                forcesStatus.setInitStatus(true);
                forcesStatus.setActiveStatus(false);
                forceStatusData.put(id,forcesStatus);
                log.info("--->兵力"+id+"初始化成功");
            }
        }
    }

    /**
     * 向资源中心注册
     * @param id
     */
    public void activeForce(String id){
        if(forceStatusData.containsKey(id)){
            PlatformStatus forcesStatus = forceStatusData.get(id);
            if(!forcesStatus.getInitStatus()){
                throw ExceptionUtils.api(String.format("该兵力未初始化"));
            }
            forcesStatus.setActiveStatus(true);
            forceStatusData.put(id,forcesStatus);
            log.info("--->兵力"+id+"激活成功");

        }else{
            throw ExceptionUtils.api(String.format("该兵力未初始化"));
        }
    }

    /**
     * 资源池注册
     * @param id
     */
    public void activeSource(String id){
        if(forceStatusData.containsKey(id)) {
            Platform platform = platformService.seekById(id);
            platformPool.put(id, platform);
            for (Sensor sensor : platform.getSensors()) {
                sensorPool.put(sensor.getId(),sensor);
            }
            for (Weapon weapon : platform.getWeapons()) {
                weaponPool.put(weapon.getId(), weapon);
            }
        }else{
            throw ExceptionUtils.api(String.format("该兵力未初始化"));
        }
    }

    /**
     * 从资源中心注销
     * @param id
     */
    public void disActiveForce(String id){
        if(forceStatusData.containsKey(id)){
            PlatformStatus forcesStatus = forceStatusData.get(id);
            if(!forcesStatus.getInitStatus()){
                throw ExceptionUtils.api(String.format("该兵力未初始化"));
            }
            if(!forcesStatus.getActiveStatus()){
                throw ExceptionUtils.api(String.format("该兵力已经注销"));
            }
            forcesStatus.setActiveStatus(false);
            forceStatusData.put(id,forcesStatus);
            log.info("--->兵力"+id+"注销成功");
        }else{
            throw ExceptionUtils.api(String.format("该兵力未初始化"));
        }
    }


    /**
     * 资源池注销
     * @param id
     */
    public void disActiveSource(String id){
        if(forceStatusData.containsKey(id)) {
            Platform platform = platformService.seekById(id);
            for (Sensor sensor : platform.getSensors()) {
                sensorPool.remove(sensor.getId());
            }
            for (Weapon weapon : platform.getWeapons()) {
                weaponPool.remove(weapon.getId());
            }
            platformPool.remove(id);
        }else{
            throw ExceptionUtils.api(String.format("该兵力未初始化"));
        }
    }

    /**
     * 兵力删除
     * @param id
     */
    public void deleteForce(String id){
        if(!forceStatusData.containsKey(id)){
            throw ExceptionUtils.api(String.format("该兵力未初始化"));
        }else{
            PlatformStatus forcesStatus = forceStatusData.get(id);
            forcesStatus.setInitStatus(false);
            forcesStatus.setActiveStatus(false);
            forceStatusData.put(id,forcesStatus);
            log.info("--->兵力"+id+"注销成功");
        }
    }

    /**
     * 清除资源库
     */
    public void cleanForce(){
        forceStatusData.clear();
        platformPool.clear();
        sensorPool.clear();
        weaponPool.clear();
    }

    public boolean isInited(String id){
        return forceStatusData.containsKey(id);
    }



}
