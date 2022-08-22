package com.soul.pooling.service.impl;

import com.egova.data.service.AbstractRepositoryBase;
import com.egova.data.service.TemplateService;
import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.flagwind.persistent.model.Sorting;
import com.soul.pooling.condition.FindCondition;
import com.soul.pooling.domain.FindRepository;
import com.soul.pooling.entity.Find;
import com.soul.pooling.service.FindService;
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
public class FindServiceImpl extends TemplateService<Find, String> implements FindService {

    private final FindRepository findRepository;

    @Override
    protected AbstractRepositoryBase<Find, String> getRepository() {
        return findRepository;
    }

    @Override
    public String insert(Find find) {

        return super.insert(find);
    }

    @Override
    public void update(Find find) {

        super.update(find);
    }

    @Override
    public PageResult<Find> page(QueryModel<FindCondition> model) {
        return super.page(model.getCondition(), model.getPaging(), model.getSorts());
    }

    @Override
    public List<Find> getByPlatformCode(String platformCode) {
        FindCondition sensorCondition = new FindCondition();
        sensorCondition.setPlatformCode(platformCode);
        List<Find> sensorList = super.query(sensorCondition, new Sorting[]{Sorting.descending("id")});
        return sensorList;
    }

}
