package com.soul.pooling.controller.free;

import com.egova.exception.ExceptionUtils;
import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.egova.web.annotation.Api;
import com.flagwind.commons.StringUtils;
import com.soul.pooling.condition.FindCondition;
import com.soul.pooling.entity.Find;
import com.soul.pooling.service.FindService;
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
public class FindController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FindService findService;

    /**
     * 新增传感器
     *
     * @param find
     */
    @Api
    @GetMapping(value = "/find/insert")
    public void insert(@RequestBody Find find) {
        findService.insert(find);
    }

    /**
     * 根据主键id删除传感器
     *
     * @param findId
     * @return
     */
    @Api
    @DeleteMapping(value = "/find/delete/{findId}")
    public Boolean deleteById(@PathVariable("findId") String findId) {
        if (StringUtils.isBlank(findId)) {
            throw ExceptionUtils.api("id can not be null");
        }
        findService.deleteById(findId);
        return true;
    }

    /**
     * 修改传感器
     *
     * @param find
     */
    @Api
    @PutMapping(value = "/find/update")
    public void update(@RequestBody Find find) {
        findService.update(find);
    }

    /**
     * 获取作战资源池传感器列表信息
     *
     * @return
     */
    @Api
    @GetMapping(value = "/list/find")
    public List<Find> listFind() {

        return findService.getAll();
    }

    /**
     * 模糊查询作战资源池传感器列表信息
     *
     * @param condition
     * @return
     */
    @Api
    @PostMapping(value = "/page/find")
    public PageResult<Find> pageFind(@RequestBody QueryModel<FindCondition> condition) {

        return findService.page(condition);
    }

    /**
     * 查找指定平台下所有武器
     *
     * @param platformCode
     * @return
     */
    @Api
    @GetMapping(value = "/find/queryByPlat/{platformCode}")
    public List<Find> getByPlatformCode(@PathVariable("platformCode") String platformCode) {
        return findService.getByPlatformCode(platformCode);
    }

    /**
     * 批量主键删除
     *
     * @param ids
     * @return
     */
    @Api
    @DeleteMapping(value = "/find/delete/batch")
    public int batchDelete(@RequestBody List<String> ids) {
        return findService.deleteByIds(ids);
    }


}
