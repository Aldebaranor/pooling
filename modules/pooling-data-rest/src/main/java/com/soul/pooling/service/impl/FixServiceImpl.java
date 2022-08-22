package com.soul.pooling.service.impl;

import com.egova.data.service.AbstractRepositoryBase;
import com.egova.data.service.TemplateService;
import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.flagwind.persistent.model.Sorting;
import com.soul.pooling.condition.FixCondition;
import com.soul.pooling.domain.FixRepository;
import com.soul.pooling.entity.Fix;
import com.soul.pooling.service.FixService;
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
public class FixServiceImpl extends TemplateService<Fix, String> implements FixService {

    private final FixRepository fixRepository;

    @Override
    protected AbstractRepositoryBase<Fix, String> getRepository() {
        return fixRepository;
    }

    @Override
    public String insert(Fix fix) {

        return super.insert(fix);
    }

    @Override
    public void update(Fix fix) {

        super.update(fix);
    }

    @Override
    public PageResult<Fix> page(QueryModel<FixCondition> model) {
        return super.page(model.getCondition(), model.getPaging(), model.getSorts());
    }

    @Override
    public List<Fix> getByPlatformCode(String platformCode) {
        FixCondition sensorCondition = new FixCondition();
        sensorCondition.setPlatformCode(platformCode);
        List<Fix> sensorList = super.query(sensorCondition, new Sorting[]{Sorting.descending("id")});
        return sensorList;
    }

}
