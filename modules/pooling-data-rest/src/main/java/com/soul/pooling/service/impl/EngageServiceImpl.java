package com.soul.pooling.service.impl;

import com.egova.data.service.AbstractRepositoryBase;
import com.egova.data.service.TemplateService;
import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.flagwind.persistent.model.Sorting;
import com.soul.pooling.condition.EngageCondition;
import com.soul.pooling.condition.WeaponCondition;
import com.soul.pooling.domain.EngageRepository;
import com.soul.pooling.entity.Engage;
import com.soul.pooling.service.EngageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @date 2022/7/21 10:47
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EngageServiceImpl extends TemplateService<Engage, String> implements EngageService {

    private final EngageRepository engageRepository;

    @Override
    protected AbstractRepositoryBase<Engage, String> getRepository() {
        return engageRepository;
    }

    @Override
    public String insert(Engage engage) {

        return super.insert(engage);
    }

    @Override
    public void update(Engage engage) {

        super.update(engage);
    }

    @Override
    public PageResult<Engage> page(QueryModel<EngageCondition> model) {
        return super.page(model.getCondition(), model.getPaging(), model.getSorts());
    }

    @Override
    public List<Engage> getByPlatformCode(String platformCode) {
        WeaponCondition weaponCondition = new WeaponCondition();
        weaponCondition.setPlatformCode(platformCode);
        List<Engage> weaponList = super.query(weaponCondition, new Sorting[]{Sorting.descending("id")});
        return weaponList;
    }
}
