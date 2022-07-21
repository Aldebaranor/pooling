package com.soul.pooling.service;

import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.soul.pooling.condition.PlatformCondition;
import com.soul.pooling.entity.Platform;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author Administrator
 * @date 2022/7/21 10:45
 */
public interface PlatformService {

    /**
     * 查找
     * @param id
     * @return
     */
    Platform getById(String id);

    /**
     * 获取所有平台
     * @return
     */
    List<Platform> getAll();

    /**
     * 插入
     * @param platform
     * @return
     */
    String insert(Platform platform);

    /**
     * 更新
     * @param platform
     */
    void update(Platform platform);

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
    PageResult<Platform> page(QueryModel<PlatformCondition> model);

}
