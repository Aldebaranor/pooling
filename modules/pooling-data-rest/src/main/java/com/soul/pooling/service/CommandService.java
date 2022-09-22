package com.soul.pooling.service;

import com.soul.pooling.model.CommandAttack;
import com.soul.pooling.model.CommandSearch;
import com.soul.pooling.model.KillingChain;

import java.util.List;

/**
 * @Description: 资源池$
 * @Author: nemo
 * @Date: 2022/8/24 4:29 PM
 */

public interface CommandService {

    KillingChain getTargetResource(CommandAttack command);

    KillingChain getSearchResource();

    List<KillingChain> getKillChain(CommandAttack command);

    List<KillingChain> getSearch(CommandSearch command);

}
