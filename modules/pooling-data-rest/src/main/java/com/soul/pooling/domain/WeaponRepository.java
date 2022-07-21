package com.soul.pooling.domain;

import com.egova.data.service.AbstractRepositoryBase;
import com.soul.pooling.entity.Weapon;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author Administrator
 * @date 2022/7/21 10:55
 */
@CacheConfig(cacheNames = Weapon.NAME)
public interface WeaponRepository extends AbstractRepositoryBase<Weapon,String> {

}
