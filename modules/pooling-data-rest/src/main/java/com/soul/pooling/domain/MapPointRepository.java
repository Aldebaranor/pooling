package com.soul.pooling.domain;

import com.egova.data.service.AbstractRepositoryBase;
import com.soul.pooling.entity.MapPoint;
import org.springframework.cache.annotation.CacheConfig;

/**
 * created by yangL
 */
@CacheConfig(cacheNames = MapPoint.NAME)
public interface MapPointRepository extends AbstractRepositoryBase<MapPoint, String> {


}
