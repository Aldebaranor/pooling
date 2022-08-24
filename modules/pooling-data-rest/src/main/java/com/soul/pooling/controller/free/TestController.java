package com.soul.pooling.controller.free;

import com.egova.web.annotation.Api;
import com.soul.pooling.zeromq.ZeroMqPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* @Description:
* @Author: nemo
* @Date: 2022/6/22
*/
@Slf4j
@RestController
@RequestMapping("/free/test")
@RequiredArgsConstructor
public class TestController {


    @Autowired(required = false)
    private ZeroMqPublisher zeroMqPublisher;


    @Api
    @GetMapping(value = "/1")
    public void test1() {
        for(int i=0;i<10000;i++){
            zeroMqPublisher.publish(i+"---------这里是测试：AaBb123@#$");
        }

    }



}


