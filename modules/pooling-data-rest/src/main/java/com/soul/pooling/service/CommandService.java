package com.soul.pooling.service;

import com.soul.pooling.model.CommandAttack;
import com.soul.pooling.model.KillingChain;
import com.soul.pooling.model.PlatformMoveData;
import com.soul.pooling.utils.GeometryUtils;

import java.util.List;

/**
 * @Description: 资源池$
 * @Author: nemo
 * @Date: 2022/8/24 4:29 PM
 */

public interface CommandService {

    KillingChain getTargetResource(CommandAttack command);

    KillingChain getSquid();

    KillingChain getSearchResource();

    GeometryUtils.Point moveData2Point(PlatformMoveData moveData);

    List<KillingChain> getAir(CommandAttack command);

}
