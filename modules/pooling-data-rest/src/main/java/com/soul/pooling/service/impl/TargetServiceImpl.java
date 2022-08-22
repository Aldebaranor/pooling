package com.soul.pooling.service.impl;

import com.egova.data.service.AbstractRepositoryBase;
import com.egova.data.service.TemplateService;
import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.flagwind.persistent.model.Sorting;
import com.soul.pooling.condition.TargetCondition;
import com.soul.pooling.domain.TargetRepository;
import com.soul.pooling.entity.Target;
import com.soul.pooling.service.TargetService;
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
public class TargetServiceImpl extends TemplateService<Target, String> implements TargetService {

    private final TargetRepository targetRepository;

    @Override
    protected AbstractRepositoryBase<Target, String> getRepository() {
        return targetRepository;
    }

    @Override
    public String insert(Target target) {

        return super.insert(target);
    }

    @Override
    public void update(Target target) {

        super.update(target);
    }

    @Override
    public PageResult<Target> page(QueryModel<TargetCondition> model) {
        return super.page(model.getCondition(), model.getPaging(), model.getSorts());
    }

    @Override
    public List<Target> getByPlatformCode(String platformCode) {
        TargetCondition sensorCondition = new TargetCondition();
        sensorCondition.setPlatformCode(platformCode);
        List<Target> sensorList = super.query(sensorCondition, new Sorting[]{Sorting.descending("id")});
        return sensorList;
    }

}
