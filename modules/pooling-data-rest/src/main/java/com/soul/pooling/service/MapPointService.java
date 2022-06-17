package com.soul.pooling.service;


import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.soul.pooling.condition.MapPointCondition;
import com.soul.pooling.entity.MapPoint;

/**
 * created by yangL
 */
public interface MapPointService  {


    MapPoint getById(String id);

    /**
     * 保存
     *
     * @param mapPoint 地图中心点
     * @return 主键
     */

    String insert( MapPoint mapPoint);

    /**
     * 更新
     *
     * @param mapPoint 地图中心点
     */

    void update( MapPoint mapPoint);

    /**
     * 主键删除
     *
     * @param id 主键
     * @return 影响记录行数
     */
    int deleteById(String id);

    PageResult<MapPoint> page(QueryModel<MapPointCondition> model);


}
