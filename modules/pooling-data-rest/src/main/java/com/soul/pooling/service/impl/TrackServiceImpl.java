package com.soul.pooling.service.impl;

import com.egova.data.service.AbstractRepositoryBase;
import com.egova.data.service.TemplateService;
import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.flagwind.persistent.model.Sorting;
import com.soul.pooling.condition.TrackCondition;
import com.soul.pooling.domain.TrackRepository;
import com.soul.pooling.entity.Track;
import com.soul.pooling.service.TrackService;
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
public class TrackServiceImpl extends TemplateService<Track, String> implements TrackService {

    private final TrackRepository trackRepository;

    @Override
    protected AbstractRepositoryBase<Track, String> getRepository() {
        return trackRepository;
    }

    @Override
    public String insert(Track track) {

        return super.insert(track);
    }

    @Override
    public void update(Track track) {

        super.update(track);
    }

    @Override
    public PageResult<Track> page(QueryModel<TrackCondition> model) {
        return super.page(model.getCondition(), model.getPaging(), model.getSorts());
    }

    @Override
    public List<Track> getByPlatformCode(String platformCode) {
        TrackCondition sensorCondition = new TrackCondition();
        sensorCondition.setPlatformCode(platformCode);
        List<Track> sensorList = super.query(sensorCondition, new Sorting[]{Sorting.descending("id")});
        return sensorList;
    }

}
