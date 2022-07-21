package com.soul.pooling.service;

import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.soul.pooling.condition.SensorCondition;
import com.soul.pooling.entity.Sensor;

import java.util.List;

/**
 * @author Administrator
 * @date 2022/7/21 10:47
 */
public interface SensorService {

    /**
     * 查找
     * @param id
     * @return
     */
    Sensor getById(String id);

    /**
     * 查询所有
     * @return
     */
    List<Sensor> getAll();

    /**
     * 插入
     * @param sensor
     * @return
     */
    String insert(Sensor sensor);

    /**
     * 更新
     * @param sensor
     */
    void update(Sensor sensor);

    /**
     * 主键删除
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 分页查询
     * @param model
     * @return
     */
    PageResult<Sensor> page(QueryModel<SensorCondition> model);
}
