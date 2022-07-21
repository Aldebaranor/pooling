package com.soul.pooling.service.impl;

import com.egova.data.service.AbstractRepositoryBase;
import com.egova.data.service.TemplateService;
import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.soul.pooling.condition.SensorCondition;
import com.soul.pooling.domain.SensorRepository;
import com.soul.pooling.entity.Sensor;
import com.soul.pooling.service.SensorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @date 2022/7/21 10:47
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SensorServiceImpl extends TemplateService<Sensor,String> implements SensorService {

    private final SensorRepository sensorRepository;

    @Override
    protected AbstractRepositoryBase<Sensor, String> getRepository() {
        return sensorRepository;
    }

    @Override
    public String insert(Sensor sensor){

        return super.insert(sensor);
    }

    @Override
    public void update(Sensor sensor){

        super.update(sensor);
    }

    @Override
    public PageResult<Sensor> page(QueryModel<SensorCondition> model){
        return super.page(model.getCondition(),model.getPaging(),model.getSorts());
    }

}
