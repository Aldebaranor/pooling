package com.soul.pooling.service;

import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.soul.pooling.condition.TargetCondition;
import com.soul.pooling.entity.Target;

import java.util.List;

/**
 * @author Administrator
 * @date 2022/7/21 10:47
 */
public interface TargetService {

    /**
     * 查找
     *
     * @param id
     * @return
     */
    Target getById(String id);

    /**
     * 查询所有
     *
     * @return
     */
    List<Target> getAll();

    /**
     * 插入
     *
     * @param target
     * @return
     */
    String insert(Target target);

    /**
     * 更新
     *
     * @param target
     */
    void update(Target target);

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
    PageResult<Target> page(QueryModel<TargetCondition> model);

    /**
     * 查询指定平台所有传感器
     *
     * @param platformCode
     * @return
     */
    List<Target> getByPlatformCode(String platformCode);

    /**
     * 主键查询 id
     *
     * @param id
     * @return
     */
    Target seekById(String id);

}
