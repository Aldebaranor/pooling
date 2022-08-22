package com.soul.pooling.domain;

import com.egova.data.service.AbstractRepositoryBase;
import com.soul.pooling.entity.Track;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author Administrator
 * @date 2022/7/21 10:52
 */
@CacheConfig(cacheNames = Track.NAME)
public interface TrackRepository extends AbstractRepositoryBase<Track, String> {

}
