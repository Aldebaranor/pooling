package com.soul.pooling.controller.free;

import com.egova.exception.ExceptionUtils;
import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.egova.web.annotation.Api;
import com.flagwind.commons.StringUtils;
import com.soul.pooling.condition.TrackCondition;
import com.soul.pooling.entity.Track;
import com.soul.pooling.service.TrackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


/**
 * @author Administrator
 * @date 2022/7/21 10:46
 */
@Slf4j
@RestController
@RequestMapping("/free/pooling/resource")
@RequiredArgsConstructor
public class TrackController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TrackService trackService;

    /**
     * 新增传感器
     *
     * @param track
     */
    @Api
    @GetMapping(value = "/track/insert")
    public void insert(@RequestBody Track track) {
        trackService.insert(track);
    }

    /**
     * 根据主键id删除传感器
     *
     * @param trackId
     * @return
     */
    @Api
    @DeleteMapping(value = "/track/delete/{trackId}")
    public Boolean deleteById(@PathVariable("trackId") String trackId) {
        if (StringUtils.isBlank(trackId)) {
            throw ExceptionUtils.api("id can not be null");
        }
        trackService.deleteById(trackId);
        return true;
    }

    /**
     * 修改传感器
     *
     * @param track
     */
    @Api
    @PutMapping(value = "/track/update")
    public void update(@RequestBody Track track) {
        trackService.update(track);
    }

    /**
     * 获取作战资源池传感器列表信息
     *
     * @return
     */
    @Api
    @GetMapping(value = "/list/track")
    public List<Track> listTrack() {

        return trackService.getAll();
    }

    /**
     * 模糊查询作战资源池传感器列表信息
     *
     * @param condition
     * @return
     */
    @Api
    @PostMapping(value = "/page/track")
    public PageResult<Track> pageTrack(@RequestBody TrackCondition condition) {

        return trackService.page(new QueryModel<>(condition));
    }

    /**
     * 查找指定平台下所有武器
     *
     * @param platformCode
     * @return
     */
    @Api
    @GetMapping(value = "/track/queryByPlat/{platformCode}")
    public List<Track> getByPlatformCode(@PathVariable("platformCode") String platformCode) {
        return trackService.getByPlatformCode(platformCode);
    }

    /**
     * 批量主键删除
     *
     * @param ids
     * @return
     */
    @Api
    @DeleteMapping(value = "/track/delete/batch")
    public int batchDelete(@RequestBody List<String> ids) {
        return trackService.deleteByIds(ids);
    }


}
