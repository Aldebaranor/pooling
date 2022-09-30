package com.soul.pooling.service;

import com.soul.pooling.entity.enums.CommandType;
import com.soul.pooling.model.Command;
import com.soul.pooling.model.KillingChain;

import java.util.List;

/**
 * @Description: 资源池$
 * @Author: nemo
 * @Date: 2022/8/24 4:29 PM
 */

public interface CommandService {

    KillingChain getTargetResource(Command command);

    KillingChain getSearchResource();

    List<KillingChain> getKillChain(Command command);

    List<KillingChain> getSearch(Command command);

    CommandType getCommandType(Command command);

}
