package com.soul.pooling.service;

import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.soul.pooling.condition.TrackCondition;
import com.soul.pooling.entity.Track;

import java.util.List;

/**
 * @author Administrator
 * @date 2022/7/21 10:47
 */
public interface TrackService {

    /**
     * 查找
     *
     * @param id
     * @return
     */
    Track getById(String id);

    /**
     * 查询所有
     *
     * @return
     */
    List<Track> getAll();

    /**
     * 插入
     *
     * @param track
     * @return
     */
    String insert(Track track);

    /**
     * 更新
     *
     * @param track
     */
    void update(Track track);

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 主键批量删除
     *
     * @param ids
     * @return
     */
    int deleteByIds(List<String> ids);

    /**
     * 分页查询
     *
     * @param model
     * @return
     */
    PageResult<Track> page(QueryModel<TrackCondition> model);

    /**
     * 查询指定平台所有传感器
     *
     * @param platformCode
     * @return
     */
    List<Track> getByPlatformCode(String platformCode);

    /**
     * 主键查询 id
     *
     * @param id
     * @return
     */
    Track seekById(String id);

}
