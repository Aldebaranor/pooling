package com.soul.pooling.service;

import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.soul.pooling.condition.WeaponCondition;
import com.soul.pooling.entity.Weapon;

import java.util.List;

/**
 * @author Administrator
 * @date 2022/7/21 10:47
 */
public interface WeaponService {

    /**
     * 查找
     * @param id
     * @return
     */
    Weapon getById(String id);

    /**
     * 查询所有
     * @return
     */
    List<Weapon> getAll();

    /**
     * 插入
     * @param weapon
     * @return
     */
    String insert(Weapon weapon);

    /**
     * 更新
     * @param weapon
     */
    void update(Weapon weapon);

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
    PageResult<Weapon> page(QueryModel<WeaponCondition> model);


}
