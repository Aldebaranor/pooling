package com.soul.pooling.service;

import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.soul.pooling.condition.AssesCondition;
import com.soul.pooling.entity.Asses;

import java.util.List;

/**
 * @author Administrator
 * @date 2022/7/21 10:47
 */
public interface AssesService {

    /**
     * 查找
     *
     * @param id
     * @return
     */
    Asses getById(String id);

    /**
     * 查询所有
     *
     * @return
     */
    List<Asses> getAll();

    /**
     * 插入
     *
     * @param asses
     * @return
     */
    String insert(Asses asses);

    /**
     * 更新
     *
     * @param asses
     */
    void update(Asses asses);

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
    PageResult<Asses> page(QueryModel<AssesCondition> model);

    /**
     * 查询指定平台所有传感器
     *
     * @param platformCode
     * @return
     */
    List<Asses> getByPlatformCode(String platformCode);

    /**
     * 主键查询 id
     *
     * @param id
     * @return
     */
    Asses seekById(String id);

}
