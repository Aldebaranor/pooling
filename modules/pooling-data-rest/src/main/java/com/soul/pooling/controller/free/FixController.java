package com.soul.pooling.controller.free;

import com.egova.exception.ExceptionUtils;
import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.egova.web.annotation.Api;
import com.flagwind.commons.StringUtils;
import com.soul.pooling.condition.FixCondition;
import com.soul.pooling.entity.Fix;
import com.soul.pooling.service.FixService;
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
public class FixController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FixService fixService;

    /**
     * 新增传感器
     *
     * @param fix
     */
    @Api
    @GetMapping(value = "/fix/insert")
    public void insert(@RequestBody Fix fix) {
        fixService.insert(fix);
    }

    /**
     * 根据主键id删除传感器
     *
     * @param fixId
     * @return
     */
    @Api
    @DeleteMapping(value = "/fix/delete/{fixId}")
    public Boolean deleteById(@PathVariable("fixId") String fixId) {
        if (StringUtils.isBlank(fixId)) {
            throw ExceptionUtils.api("id can not be null");
        }
        fixService.deleteById(fixId);
        return true;
    }

    /**
     * 修改传感器
     *
     * @param fix
     */
    @Api
    @PutMapping(value = "/fix/update")
    public void update(@RequestBody Fix fix) {
        fixService.update(fix);
    }

    /**
     * 获取作战资源池传感器列表信息
     *
     * @return
     */
    @Api
    @GetMapping(value = "/list/fix")
    public List<Fix> listFix() {

        return fixService.getAll();
    }

    /**
     * 模糊查询作战资源池传感器列表信息
     *
     * @param condition
     * @return
     */
    @Api
    @PostMapping(value = "/page/fix")
    public PageResult<Fix> pageFix(@RequestBody QueryModel<FixCondition> condition) {

        return fixService.page(condition);
    }

    /**
     * 查找指定平台下所有武器
     *
     * @param platformCode
     * @return
     */
    @Api
    @GetMapping(value = "/fix/queryByPlat/{platformCode}")
    public List<Fix> getByPlatformCode(@PathVariable("platformCode") String platformCode) {
        return fixService.getByPlatformCode(platformCode);
    }

    /**
     * 批量主键删除
     *
     * @param ids
     * @return
     */
    @Api
    @DeleteMapping(value = "/fix/delete/batch")
    public int batchDelete(@RequestBody List<String> ids) {
        return fixService.deleteByIds(ids);
    }


}
