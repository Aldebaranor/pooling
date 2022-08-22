package com.soul.pooling.service;

import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.soul.pooling.condition.EngageCondition;
import com.soul.pooling.entity.Engage;

import java.util.List;

/**
 * @author Administrator
 * @date 2022/7/21 10:47
 */
public interface EngageService {

    /**
     * 查找
     *
     * @param id
     * @return
     */
    Engage getById(String id);

    /**
     * 查询所有
     *
     * @return
     */
    List<Engage> getAll();

    /**
     * 插入
     *
     * @param engage
     * @return
     */
    String insert(Engage engage);

    /**
     * 更新
     *
     * @param engage
     */
    void update(Engage engage);

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
    PageResult<Engage> page(QueryModel<EngageCondition> model);

    /**
     * 查询指定平台所有武器
     *
     * @param platformCode
     * @return
     */
    List<Engage> getByPlatformCode(String platformCode);


}
