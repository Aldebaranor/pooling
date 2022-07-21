package com.soul.pooling.controller.free;

import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.egova.web.annotation.Api;
import com.soul.pooling.condition.WeaponCondition;
import com.soul.pooling.entity.Sensor;
import com.soul.pooling.entity.Weapon;
import com.soul.pooling.service.WeaponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2022/7/21 10:46
 */
@Slf4j
@RestController
@RequestMapping("/free/pooling/resource")
@RequiredArgsConstructor
public class WeaponController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeaponService weaponService;

    /**
     * 获取作战资源池武器列表信息
     * @return
     */
    @Api
    @GetMapping(value = "/list/weapon")
    public List<Weapon> listWeapon(){

        return weaponService.getAll();
    }

    /**
     * 模糊查询作战资源池武器列表信息
     * @param condition
     * @return
     */
    @Api
    @PostMapping(value = "/page/weapon")
    public PageResult<Weapon> pageWeapon(@RequestBody WeaponCondition condition){

        return weaponService.page(new QueryModel<>(condition));
    }
}
