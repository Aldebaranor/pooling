package com.soul.pooling.service;

import com.soul.pooling.entity.*;
import com.soul.pooling.model.ResourceModel;

import java.util.List;

/**
 * @Description: 资源池$
 * @Author: nemo
 * @Date: 2022/8/24 4:29 PM
 */

public interface PoolingService {

    List<ResourceModel> findToList(List<Find> collect);

    List<ResourceModel> fixToList(List<Fix> collect);

    List<ResourceModel> trackToList(List<Track> collect);

    List<ResourceModel> targetToList(List<Target> collect);

    List<ResourceModel> engageToList(List<Engage> collect);

    List<ResourceModel> assesToList(List<Asses> collect);


}
