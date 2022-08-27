package com.soul.pooling.service.impl;


import com.soul.pooling.model.KillingChain;
import com.soul.pooling.service.CommandService;
import com.soul.pooling.service.PoolingManagement;
import com.soul.pooling.service.PoolingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Song
 * @Date 2022/8/25 9:51
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommandServiceImpl implements CommandService {

    @Autowired
    private PoolingManagement management;

    @Autowired
    private PoolingService poolingService;

    KillingChain getTargetResourceAir() {
        KillingChain killingChain = new KillingChain();


        return killingChain;
    }

}
