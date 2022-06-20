package com.soul.pooling.controller.free;

import com.egova.exception.ExceptionUtils;
import com.egova.web.annotation.Api;
import com.flagwind.commons.StringUtils;
import com.soul.pooling.model.ForcesStatus;
import com.soul.pooling.service.StatusManagement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author: 码头工人
 * @Date: 2021/11/01/2:10 下午
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/free/pump")
@RequiredArgsConstructor
public class PumpController {

    private final RestTemplate restTemplate;

    @Autowired
    private StatusManagement management;

    @Api
    @GetMapping(value = "/start/{experiment}")
    public Boolean pumpStart(@PathVariable String experiment) {
        if (StringUtils.isBlank(experiment)) {
            throw ExceptionUtils.api("structName can not be null");
        }
        management.cleanForce();
        return true;
    }

    @Api
    @GetMapping(value = "/forces")
    public Map<String, ForcesStatus> forces() {
        return management.getAll();
    }

    @Api
    @PostMapping(value = "/init")
    public Boolean pumpInit(@RequestBody List<String> forces) {
        for (String id:forces){
            management.initForce(id);
        }
        return true;
    }
    @Api
    @PostMapping(value = "/activate")
    public Boolean pumpActivate(@RequestBody List<String> forces) {
        for (String id:forces){
            management.activeForce(id);
        }
        return true;
    }

    @Api
    @PostMapping(value = "/dis-activate")
    public Boolean pumpDisActivate( @RequestBody List<String> forces) {
        for (String id:forces){
            management.disActiveForce(id);
        }
        return true;
    }

}


