package com.soul.pooling.domain;

import com.egova.data.service.AbstractRepositoryBase;
import com.soul.pooling.entity.Asses;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author Administrator
 * @date 2022/7/21 10:52
 */
@CacheConfig(cacheNames = Asses.NAME)
public interface AssesRepository extends AbstractRepositoryBase<Asses, String> {

}
