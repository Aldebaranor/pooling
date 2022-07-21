package com.soul.pooling.controller.free;

import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.egova.web.annotation.Api;
import com.soul.pooling.condition.SensorCondition;
import com.soul.pooling.entity.Sensor;
import com.soul.pooling.service.SensorService;
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
public class SensorController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SensorService sensorService;

    /**
     * 获取作战资源池传感器列表信息
     * @return
     */
    @Api
    @GetMapping(value = "/list/sensor")
    public List <Sensor> listSensor(){

        return sensorService.getAll();
    }

    /**
     * 模糊查询作战资源池传感器列表信息
     * @param condition
     * @return
     */
    @Api
    @PostMapping(value = "/page/sensor")
    public PageResult<Sensor> pageSensor(@RequestBody SensorCondition condition){

        return sensorService.page(new QueryModel<>(condition));
    }


}
