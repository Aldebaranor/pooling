package com.soul.pooling.service;

import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.soul.pooling.condition.FindCondition;
import com.soul.pooling.entity.Find;

import java.util.List;

/**
 * @author Administrator
 * @date 2022/7/21 10:47
 */
public interface FindService {

    /**
     * 查找
     *
     * @param id
     * @return
     */
    Find getById(String id);

    /**
     * 查询所有
     *
     * @return
     */
    List<Find> getAll();

    /**
     * 插入
     *
     * @param find
     * @return
     */
    String insert(Find find);

    /**
     * 更新
     *
     * @param find
     */
    void update(Find find);

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
    PageResult<Find> page(QueryModel<FindCondition> model);

    /**
     * 查询指定平台所有传感器
     *
     * @param platformCode
     * @return
     */
    List<Find> getByPlatformCode(String platformCode);

    /**
     * 主键查询 id
     *
     * @param id
     * @return
     */
    Find seekById(String id);

}
