//package com.soul.pooling.controller.unity;
//
//import com.egova.exception.ExceptionUtils;
//import com.egova.web.annotation.Api;
//import com.flagwind.commons.StringUtils;
//import com.soul.pooling.service.DeviceService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
///**
// * 设备信息接口
// *
// * @author 刘龙海
// * @data 2022/3/18 11:17
// */
//
//@Slf4j
//@RestController
//@RequestMapping("/unity/device")
//@RequiredArgsConstructor
//public class DeviceController {
//    private final DeviceService deviceService;
//
//    /**
//     * 主键获取
//     *
//     * @param id 主键
//     * @return Alarm
//     */
//    @Api
//    @GetMapping("/{id}")
//    public Device getById(@PathVariable String id) {
//        return deviceService.getById(id);
//    }
//
//    @Api
//    @PostMapping
//    public String insert(@RequestBody Device device){
//        if(StringUtils.isBlank(device.getId())){
//            throw ExceptionUtils.api("The id of device is null");
//        }
//        return deviceService.insert(device);
//    }
//
//    @Api
//    @PutMapping
//    public void update(@RequestBody Device device){
//        if(StringUtils.isBlank(device.getId())){
//            throw ExceptionUtils.api("The id of device is null");
//        }
//        deviceService.update(device);
//    }
//
//    @Api
//    @DeleteMapping("/{id}")
//    public int deleteById(@PathVariable String id) {
//        return deviceService.deleteById(id);
//    }
//}
