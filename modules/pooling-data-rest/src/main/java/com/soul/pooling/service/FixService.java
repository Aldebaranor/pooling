package com.soul.pooling.service;

import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.soul.pooling.condition.FixCondition;
import com.soul.pooling.entity.Fix;

import java.util.List;

/**
 * @author Administrator
 * @date 2022/7/21 10:47
 */
public interface FixService {

    /**
     * 查找
     *
     * @param id
     * @return
     */
    Fix getById(String id);

    /**
     * 查询所有
     *
     * @return
     */
    List<Fix> getAll();

    /**
     * 插入
     *
     * @param fix
     * @return
     */
    String insert(Fix fix);

    /**
     * 更新
     *
     * @param fix
     */
    void update(Fix fix);

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
    PageResult<Fix> page(QueryModel<FixCondition> model);

    /**
     * 查询指定平台所有传感器
     *
     * @param platformCode
     * @return
     */
    List<Fix> getByPlatformCode(String platformCode);

    /**
     * 主键查询 id
     *
     * @param id
     * @return
     */
    Fix seekById(String id);

}
