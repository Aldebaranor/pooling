package com.soul.pooling.domain;

import com.egova.data.service.AbstractRepositoryBase;
import com.soul.pooling.entity.MapPoint;
import org.springframework.cache.annotation.CacheConfig;


/**
* @Description:
* @Author: nemo
* @Date: 2022/6/22
*/
@CacheConfig(cacheNames = MapPoint.NAME)
public interface MapPointRepository extends AbstractRepositoryBase<MapPoint, String> {


}
