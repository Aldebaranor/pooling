package com.soul.pooling.service;

import com.soul.pooling.model.CommandAttack;
import com.soul.pooling.model.KillingChain;

/**
 * @Description: 资源池$
 * @Author: nemo
 * @Date: 2022/8/24 4:29 PM
 */

public interface CommandService {

    KillingChain getTargetResource(CommandAttack command);

    KillingChain getSquid();

    KillingChain getAir(CommandAttack command);

}
