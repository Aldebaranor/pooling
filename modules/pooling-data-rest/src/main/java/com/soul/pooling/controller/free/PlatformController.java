package com.soul.pooling.controller.free;

import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.egova.web.annotation.Api;
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
}
