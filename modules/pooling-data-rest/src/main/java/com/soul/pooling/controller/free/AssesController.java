package com.soul.pooling.controller.free;

import com.egova.exception.ExceptionUtils;
import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.egova.web.annotation.Api;
import com.flagwind.commons.StringUtils;
import com.soul.pooling.condition.AssesCondition;
import com.soul.pooling.entity.Asses;
import com.soul.pooling.service.AssesService;
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
public class AssesController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AssesService assesService;

    /**
     * 新增传感器
     *
     * @param asses
     */
    @Api
    @GetMapping(value = "/asses/insert")
    public void insert(@RequestBody Asses asses) {
        assesService.insert(asses);
    }

    /**
     * 根据主键id删除传感器
     *
     * @param assesId
     * @return
     */
    @Api
    @DeleteMapping(value = "/asses/delete/{assesId}")
    public Boolean deleteById(@PathVariable("assesId") String assesId) {
        if (StringUtils.isBlank(assesId)) {
            throw ExceptionUtils.api("id can not be null");
        }
        assesService.deleteById(assesId);
        return true;
    }

    /**
     * 修改传感器
     *
     * @param asses
     */
    @Api
    @PutMapping(value = "/sensor/update")
    public void update(@RequestBody Asses asses) {
        assesService.update(asses);
    }

    /**
     * 获取作战资源池传感器列表信息
     *
     * @return
     */
    @Api
    @GetMapping(value = "/list/asses")
    public List<Asses> listAsses() {

        return assesService.getAll();
    }

    /**
     * 模糊查询作战资源池传感器列表信息
     *
     * @param condition
     * @return
     */
    @Api
    @PostMapping(value = "/page/asses")
    public PageResult<Asses> pageAsses(@RequestBody AssesCondition condition) {

        return assesService.page(new QueryModel<>(condition));
    }

    /**
     * 查找指定平台下所有武器
     *
     * @param platformCode
     * @return
     */
    @Api
    @GetMapping(value = "/asses/queryByPlat/{platformCode}")
    public List<Asses> getByPlatformCode(@PathVariable("platformCode") String platformCode) {
        return assesService.getByPlatformCode(platformCode);
    }

    /**
     * 批量主键删除
     *
     * @param ids
     * @return
     */
    @Api
    @DeleteMapping(value = "/asses/delete/batch")
    public int batchDelete(@RequestBody List<String> ids) {
        return assesService.deleteByIds(ids);
    }


}
