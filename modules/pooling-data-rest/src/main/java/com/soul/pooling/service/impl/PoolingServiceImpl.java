package com.soul.pooling.service.impl;


import com.soul.pooling.entity.*;
import com.soul.pooling.model.ResourceModel;
import com.soul.pooling.service.PoolingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Song
 * @Date 2022/8/25 9:51
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PoolingServiceImpl implements PoolingService {


    @Override
    public List<ResourceModel> findToList(List<Find> collect) {
        List<ResourceModel> list = new ArrayList<>();

        for (Find find : collect) {
            ResourceModel model = new ResourceModel();
            model.setId(find.getId());
            model.setName(find.getName());
            model.setDeviceCode(find.getDeviceCode());
            model.setType(find.getType());
            model.setPlatformCode(find.getPlatformCode());
            model.setPlatformName(find.getPlatformName());
            model.setStatus(find.getStatus());
            model.setDeviceTpye(find.getDeviceType());
            list.add(model);
        }
        return list;
    }

    @Override
    public List<ResourceModel> fixToList(List<Fix> collect) {
        List<ResourceModel> list = new ArrayList<>();
        for (Fix fix : collect) {
            ResourceModel model = new ResourceModel();
            model.setId(fix.getId());
            model.setName(fix.getName());
            model.setDeviceCode(fix.getDeviceCode());
            model.setType(fix.getType());
            model.setPlatformCode(fix.getPlatformCode());
            model.setPlatformName(fix.getPlatformName());
            model.setStatus(fix.getStatus());
            model.setDeviceTpye(fix.getDeviceType());
            list.add(model);
        }
        return list;
    }

    @Override
    public List<ResourceModel> trackToList(List<Track> collect) {
        List<ResourceModel> list = new ArrayList<>();
        for (Track track : collect) {
            ResourceModel model = new ResourceModel();
            model.setId(track.getId());
            model.setName(track.getName());
            model.setDeviceCode(track.getDeviceCode());
            model.setType(track.getType());
            model.setPlatformCode(track.getPlatformCode());
            model.setPlatformName(track.getPlatformName());
            model.setStatus(track.getStatus());
            model.setDeviceTpye(track.getDeviceType());
            list.add(model);
        }
        return list;
    }

    @Override
    public List<ResourceModel> targetToList(List<Target> collect) {
        List<ResourceModel> list = new ArrayList<>();

        for (Target target : collect) {
            ResourceModel model = new ResourceModel();
            model.setId(target.getId());
            model.setName(target.getName());
            model.setType(target.getType());
            model.setPlatformCode(target.getPlatformCode());
            model.setPlatformName(target.getPlatformName());
            model.setStatus(target.getStatus());
            model.setDeviceTpye("指控系统");
            list.add(model);
        }
        return list;
    }

    @Override
    public List<ResourceModel> engageToList(List<Engage> collect) {
        List<ResourceModel> list = new ArrayList<>();

        for (Engage engage : collect) {
            ResourceModel model = new ResourceModel();
            model.setId(engage.getId());
            model.setName(engage.getName());
            model.setDeviceCode(engage.getDeviceCode());
            model.setType(engage.getType());
            model.setPlatformCode(engage.getPlatformCode());
            model.setPlatformName(engage.getPlatformName());
            model.setStatus(engage.getStatus());
            model.setNum(Integer.valueOf(engage.getNumber()));
            model.setDeviceTpye(engage.getDeviceType());
            list.add(model);
        }
        return list;
    }

    @Override
    public List<ResourceModel> assesToList(List<Asses> collect) {
        List<ResourceModel> list = new ArrayList<>();

        for (Asses asses : collect) {
            ResourceModel model = new ResourceModel();
            model.setId(asses.getId());
            model.setName(asses.getName());
            model.setType(asses.getType());
            model.setDeviceCode(asses.getDeviceCode());
            model.setPlatformCode(asses.getPlatformCode());
            model.setPlatformName(asses.getPlatformName());
            model.setStatus(asses.getStatus());
            model.setDeviceTpye(asses.getDeviceType());
            list.add(model);
        }
        return list;
    }


}
