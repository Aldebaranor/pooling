package com.soul.pooling.controller.free;

import com.egova.exception.ExceptionUtils;
import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.egova.web.annotation.Api;
import com.flagwind.commons.StringUtils;
import com.soul.pooling.condition.PlatformCondition;
import com.soul.pooling.entity.Platform;
import com.soul.pooling.service.PlatformService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


/**
 * @author Administrator
 * @date 2022/7/21 10:45
 */
@Slf4j
@RestController
@RequestMapping("/free/pooling/resource")
@RequiredArgsConstructor
public class PlatformController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PlatformService platformService;

    /**
     * 新增平台
     * @param platform
     */
    @Api
    @GetMapping(value = "/platform/insert")
    public void insert(@RequestBody Platform platform){
        platformService.insert(platform);
    }

    /**
     * 根据主键id删除平台
     * @param platformId
     * @return
     */
    @Api
    @DeleteMapping(value = "/platform/delete/{platformId}")
    public Boolean deleteById(@PathVariable("platformId") String platformId){
        if(StringUtils.isBlank(platformId)){
            throw ExceptionUtils.api("id can not be null");
        }
        platformService.deleteById(platformId);
        return true;
    }

    /**
     * 修改平台
     * @param platform
     */
    @Api
    @PutMapping(value = "/platform/update")
    public void update(@RequestBody Platform platform){
        platformService.update(platform);
    }

    /**
     * 获取作战资源池平台列表信息
     * @return
     */
    @Api
    @GetMapping(value = "/list/platform")
    public List<Platform> listPlatform(){

        return platformService.getAll();
    }

    /**
     * 模糊查询作战资源池平台列表信息
     * @param condition
     * @return
     */
    @Api
    @PostMapping(value = "/page/platform")
    public PageResult<Platform> pagePlatform(@RequestBody PlatformCondition condition){

        return platformService.page(new QueryModel<>(condition));

    }

    /**
     * 批量主键删除
     * @param ids
     * @return
     */
    @Api
    @DeleteMapping(value = "/platform/delete/batch")
    public int batchDelete(@RequestBody List<String> ids){
        return platformService.deleteByIds(ids);
    }

    /**
     * 根据id查询
     * @return
     */
    @Api
    @PostMapping(value = "/platform/{id}")
    public Platform getById(@PathVariable("id") String id){
        return platformService.seekById(id);
    }
}
