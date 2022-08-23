package com.soul.pooling.controller.free;

import com.egova.exception.ExceptionUtils;
import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.egova.web.annotation.Api;
import com.flagwind.commons.StringUtils;
import com.soul.pooling.condition.TargetCondition;
import com.soul.pooling.entity.Target;
import com.soul.pooling.service.TargetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


/**
 * @author Administrator
 * @date 2022/7/21 10:46
 */
@Slf4j
@RestController
@RequestMapping("/free/pooling/resource")
@RequiredArgsConstructor
public class TargetController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TargetService targetService;

    /**
     * 新增传感器
     *
     * @param target
     */
    @Api
    @GetMapping(value = "/target/insert")
    public void insert(@RequestBody Target target) {
        targetService.insert(target);
    }

    /**
     * 根据主键id删除传感器
     *
     * @param targetId
     * @return
     */
    @Api
    @DeleteMapping(value = "/target/delete/{targetId}")
    public Boolean deleteById(@PathVariable("targetId") String targetId) {
        if (StringUtils.isBlank(targetId)) {
            throw ExceptionUtils.api("id can not be null");
        }
        targetService.deleteById(targetId);
        return true;
    }

    /**
     * 修改传感器
     *
     * @param target
     */
    @Api
    @PutMapping(value = "/target/update")
    public void update(@RequestBody Target target) {
        targetService.update(target);
    }

    /**
     * 获取作战资源池传感器列表信息
     *
     * @return
     */
    @Api
    @GetMapping(value = "/list/target")
    public List<Target> listTarget() {

        return targetService.getAll();
    }

    /**
     * 模糊查询作战资源池传感器列表信息
     *
     * @param condition
     * @return
     */
    @Api
    @PostMapping(value = "/page/target")
    public PageResult<Target> pageTarget(@RequestBody QueryModel<TargetCondition> condition) {

        return targetService.page(condition);
    }

    /**
     * 查找指定平台下所有武器
     *
     * @param platformCode
     * @return
     */
    @Api
    @GetMapping(value = "/target/queryByPlat/{platformCode}")
    public List<Target> getByPlatformCode(@PathVariable("platformCode") String platformCode) {
        return targetService.getByPlatformCode(platformCode);
    }

    /**
     * 批量主键删除
     *
     * @param ids
     * @return
     */
    @Api
    @DeleteMapping(value = "/target/delete/batch")
    public int batchDelete(@RequestBody List<String> ids) {
        return targetService.deleteByIds(ids);
    }


}
