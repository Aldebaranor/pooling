package com.soul.pooling.domain;


import com.egova.data.service.AbstractRepositoryBase;
import com.soul.pooling.entity.Platform;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author Administrator
 * @date 2022/7/21 10:45
 */

@CacheConfig(cacheNames = Platform.NAME)
public interface PlatformRepository extends AbstractRepositoryBase<Platform, String> {

}
