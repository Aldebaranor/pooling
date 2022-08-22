package com.soul.pooling.service.impl;

import com.egova.data.service.AbstractRepositoryBase;
import com.egova.data.service.TemplateService;
import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.flagwind.persistent.model.Sorting;
import com.soul.pooling.condition.AssesCondition;
import com.soul.pooling.domain.AssesRepository;
import com.soul.pooling.entity.Asses;
import com.soul.pooling.service.AssesService;
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
public class AssesServiceImpl extends TemplateService<Asses, String> implements AssesService {

    private final AssesRepository assesRepository;

    @Override
    protected AbstractRepositoryBase<Asses, String> getRepository() {
        return assesRepository;
    }

    @Override
    public String insert(Asses asses) {

        return super.insert(asses);
    }

    @Override
    public void update(Asses asses) {

        super.update(asses);
    }

    @Override
    public PageResult<Asses> page(QueryModel<AssesCondition> model) {
        return super.page(model.getCondition(), model.getPaging(), model.getSorts());
    }

    @Override
    public List<Asses> getByPlatformCode(String platformCode) {
        AssesCondition sensorCondition = new AssesCondition();
        sensorCondition.setPlatformCode(platformCode);
        List<Asses> sensorList = super.query(sensorCondition, new Sorting[]{Sorting.descending("id")});
        return sensorList;
    }

}
