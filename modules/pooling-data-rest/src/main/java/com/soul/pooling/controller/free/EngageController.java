package com.soul.pooling.controller.free;

import com.egova.exception.ExceptionUtils;
import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.egova.web.annotation.Api;
import com.flagwind.commons.StringUtils;
import com.soul.pooling.condition.EngageCondition;
import com.soul.pooling.entity.Engage;
import com.soul.pooling.service.EngageService;
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
public class EngageController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EngageService engageService;

    /**
     * 新增武器
     *
     * @param engage
     */
    @Api
    @GetMapping(value = "/engage/insert")
    public void insert(@RequestBody Engage engage) {
        engageService.insert(engage);
    }

    /**
     * 根据主键id删除武器
     *
     * @return
     */
    @Api
    @DeleteMapping(value = "/engage/delete/{engageId}")
    public Boolean deleteById(@PathVariable("engageId") String engageId) {
        if (StringUtils.isBlank(engageId)) {
            throw ExceptionUtils.api("id can not be null");
        }
        engageService.deleteById(engageId);
        return true;
    }

    /**
     * 新增武器
     *
     * @param engage
     */
    @Api
    @PutMapping(value = "/engage/update")
    public void update(@RequestBody Engage engage) {
        engageService.update(engage);
    }

    /**
     * 获取作战资源池武器列表信息
     *
     * @return
     */
    @Api
    @GetMapping(value = "/list/engage")
    public List<Engage> listengage() {

        return engageService.getAll();
    }

    /**
     * 模糊查询作战资源池武器列表信息
     *
     * @param condition
     * @return
     */
    @Api
    @PostMapping(value = "/page/engage")
    public PageResult<Engage> pageengage(@RequestBody EngageCondition condition) {

        return engageService.page(new QueryModel<>(condition));
    }

    /**
     * 查找指定平台下所有武器
     *
     * @param platformCode
     * @return
     */
    @Api
    @GetMapping(value = "/engage/queryByPlat/{platformCode}")
    public List<Engage> getByPlatformCode(@PathVariable("platformCode") String platformCode) {
        return engageService.getByPlatformCode(platformCode);
    }

    /**
     * 批量主键删除
     *
     * @param ids
     * @return
     */
    @Api
    @DeleteMapping(value = "/engage/delete/batch")
    public int batchDelete(@RequestBody List<String> ids) {
        return engageService.deleteByIds(ids);
    }
}
