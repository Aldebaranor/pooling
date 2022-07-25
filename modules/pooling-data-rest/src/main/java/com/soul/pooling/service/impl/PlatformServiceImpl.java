package com.soul.pooling.service.impl;

import com.egova.data.service.AbstractRepositoryBase;
import com.egova.data.service.TemplateService;
import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.soul.pooling.condition.PlatformCondition;
import com.soul.pooling.domain.PlatformRepository;
import com.soul.pooling.entity.Platform;
import com.soul.pooling.entity.Sensor;
import com.soul.pooling.entity.Weapon;
import com.soul.pooling.service.PlatformService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @date 2022/7/21 10:44
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlatformServiceImpl extends TemplateService<Platform,String> implements PlatformService {

    private final PlatformRepository platformRepository;

    @Override
    protected AbstractRepositoryBase<Platform, String> getRepository() {
        return platformRepository;
    }

    @Override
    public String insert(Platform platform){

        return super.insert(platform);
    }

    @Override
    public void update(Platform platform){

        super.update(platform);
    }

    @Override
    public PageResult<Platform> page(QueryModel<PlatformCondition> model){
        return super.page(model.getCondition(),model.getPaging(),model.getSorts());
    }

}
