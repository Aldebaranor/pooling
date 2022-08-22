package com.soul.pooling.domain;

import com.egova.data.service.AbstractRepositoryBase;
import com.soul.pooling.entity.Fix;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author Administrator
 * @date 2022/7/21 10:52
 */
@CacheConfig(cacheNames = Fix.NAME)
public interface FixRepository extends AbstractRepositoryBase<Fix, String> {

}
