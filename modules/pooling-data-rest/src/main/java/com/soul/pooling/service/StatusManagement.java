package com.soul.pooling.service;

import com.egova.exception.ExceptionUtils;
import com.soul.pooling.model.ForcesStatus;
import lombok.extern.slf4j.Slf4j;
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

    private final ConcurrentMap<String, ForcesStatus> forceStatusData = new ConcurrentHashMap();


    public Map<String, ForcesStatus> getAll() {
        return forceStatusData;
    }

    public ForcesStatus getForcesData(String id) {
        return forceStatusData.get(id);
    }

    public void initForce(String id){
        if(!forceStatusData.containsKey(id)){
            ForcesStatus status = new ForcesStatus();
            status.setInitStatus(true);
            status.setActiveStatus(false);
            forceStatusData.put(id,status);
        }else{
            ForcesStatus forcesStatus = forceStatusData.get(id);
            if(forcesStatus.getInitStatus()){
                throw ExceptionUtils.api(String.format("该兵力已经初始化"));
            }else{
                forcesStatus.setInitStatus(true);
                forcesStatus.setActiveStatus(false);
                forceStatusData.put(id,forcesStatus);
            }
        }
    }
    public void activeForce(String id){
        if(forceStatusData.containsKey(id)){
            ForcesStatus forcesStatus = forceStatusData.get(id);
            if(!forcesStatus.getInitStatus()){
                throw ExceptionUtils.api(String.format("该兵力未初始化"));
            }
            forcesStatus.setActiveStatus(true);
            forceStatusData.put(id,forcesStatus);
        }else{
            throw ExceptionUtils.api(String.format("该兵力未初始化"));
        }
    }
    public void disActiveForce(String id){
        if(forceStatusData.containsKey(id)){
            ForcesStatus forcesStatus = forceStatusData.get(id);
            if(!forcesStatus.getInitStatus()){
                throw ExceptionUtils.api(String.format("该兵力未初始化"));
            }
            if(!forcesStatus.getActiveStatus()){
                throw ExceptionUtils.api(String.format("该兵力已经注销"));
            }
            forcesStatus.setActiveStatus(false);
            forceStatusData.put(id,forcesStatus);
        }else{
            throw ExceptionUtils.api(String.format("该兵力未初始化"));
        }
    }
    public void deleteForce(String id){
        if(!forceStatusData.containsKey(id)){
            throw ExceptionUtils.api(String.format("该兵力未初始化"));
        }else{
            ForcesStatus forcesStatus = forceStatusData.get(id);
            forcesStatus.setInitStatus(false);
            forcesStatus.setActiveStatus(false);
            forceStatusData.put(id,forcesStatus);
        }
    }

    public void cleanForce(){
        forceStatusData.clear();
    }





}
