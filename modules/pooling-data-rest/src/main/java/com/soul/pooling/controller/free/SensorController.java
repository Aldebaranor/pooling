package com.soul.pooling.controller.free;

import com.egova.exception.ExceptionUtils;
import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.egova.web.annotation.Api;
import com.flagwind.commons.StringUtils;
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
     * 新增传感器
     * @param sensor
     */
    @Api
    @GetMapping(value = "/sensor/insert")
    public void insert(@RequestBody Sensor sensor){
        sensorService.insert(sensor);
    }

    /**
     * 根据主键id删除传感器
     * @param sensorId
     * @return
     */
    @Api
    @DeleteMapping(value = "/sensor/delete/{sensorId}")
    public Boolean deleteById(@PathVariable("sensorId") String sensorId){
        if(StringUtils.isBlank(sensorId)){
            throw ExceptionUtils.api("id can not be null");
        }
        sensorService.deleteById(sensorId);
        return true;
    }

    /**
     * 修改传感器
     * @param sensor
     */
    @Api
    @PutMapping(value = "/sensor/update")
    public void update(@RequestBody Sensor sensor){
        sensorService.update(sensor);
    }

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

    /**
     * 查找指定平台下所有武器
     * @param platformCode
     * @return
     */
    @Api
    @GetMapping(value = "/sensor/queryByPlat/{platformCode}")
    public List<Sensor> getByPlatformCode(@PathVariable("platformCode") String platformCode){
        return sensorService.getByPlatformCode(platformCode);
    }

    /**
     * 批量主键删除
     * @param ids
     * @return
     */
    @Api
    @DeleteMapping(value = "/sensor/delete/batch")
    public int batchDelete(@RequestBody List<String> ids){
        return sensorService.deleteByIds(ids);
    }


}
