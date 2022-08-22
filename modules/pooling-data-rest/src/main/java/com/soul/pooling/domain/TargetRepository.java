package com.soul.pooling.domain;

import com.egova.data.service.AbstractRepositoryBase;
import com.soul.pooling.entity.Target;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author Administrator
 * @date 2022/7/21 10:52
 */
@CacheConfig(cacheNames = Target.NAME)
public interface TargetRepository extends AbstractRepositoryBase<Target, String> {

}
