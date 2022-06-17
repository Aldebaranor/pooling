package com.soul.pooling.service.impl;

import com.egova.data.service.AbstractRepositoryBase;
import com.egova.data.service.TemplateService;
import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.soul.pooling.condition.MapPointCondition;
import com.soul.pooling.domain.MapPointRepository;
import com.soul.pooling.entity.MapPoint;
import com.soul.pooling.service.MapPointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Priority;
import java.sql.Timestamp;


/**
 * created by yangL
 */
@Slf4j
@Service
@Priority(5)
@RequiredArgsConstructor
public class MapPointServiceImpl extends TemplateService<MapPoint, String> implements MapPointService {

    private final MapPointRepository mapPointRepository;

    @Override
    protected AbstractRepositoryBase<MapPoint, String> getRepository() {
        return mapPointRepository;
    }

    @Override
    public String insert(MapPoint mapPoint) {
        mapPoint.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return super.insert(mapPoint);
    }

    @Override
    public void update(MapPoint mapPoint) {
        mapPoint.setModifyTime(new Timestamp(System.currentTimeMillis()));
        super.update(mapPoint);
    }

    @Override
    public PageResult<MapPoint> page(QueryModel<MapPointCondition> model) {
        return super.page(model.getCondition(), model.getPaging(), model.getSorts());
    }

}
