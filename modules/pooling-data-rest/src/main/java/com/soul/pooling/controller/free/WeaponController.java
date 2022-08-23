package com.soul.pooling.controller.free;

import com.egova.exception.ExceptionUtils;
import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.egova.web.annotation.Api;
import com.flagwind.commons.StringUtils;
import com.soul.pooling.condition.WeaponCondition;
import com.soul.pooling.entity.Weapon;
import com.soul.pooling.service.WeaponService;
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
public class WeaponController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeaponService weaponService;

    /**
     * 新增武器
     *
     * @param weapon
     */
    @Api
    @GetMapping(value = "/weapon/insert")
    public void insert(@RequestBody Weapon weapon) {
        weaponService.insert(weapon);
    }

    /**
     * 根据主键id删除武器
     *
     * @return
     */
    @Api
    @DeleteMapping(value = "/weapon/delete/{weaponId}")
    public Boolean deleteById(@PathVariable("weaponId") String weaponId) {
        if (StringUtils.isBlank(weaponId)) {
            throw ExceptionUtils.api("id can not be null");
        }
        weaponService.deleteById(weaponId);
        return true;
    }

    /**
     * 新增武器
     *
     * @param weapon
     */
    @Api
    @PutMapping(value = "/weapon/update")
    public void update(@RequestBody Weapon weapon) {
        weaponService.update(weapon);
    }

    /**
     * 获取作战资源池武器列表信息
     *
     * @return
     */
    @Api
    @GetMapping(value = "/list/weapon")
    public List<Weapon> listWeapon() {

        return weaponService.getAll();
    }

    /**
     * 模糊查询作战资源池武器列表信息
     *
     * @param condition
     * @return
     */
    @Api
    @PostMapping(value = "/page/weapon")
    public PageResult<Weapon> pageWeapon(@RequestBody QueryModel<WeaponCondition> condition) {

        return weaponService.page(condition);
    }

    /**
     * 查找指定平台下所有武器
     *
     * @param platformCode
     * @return
     */
    @Api
    @GetMapping(value = "/weapon/queryByPlat/{platformCode}")
    public List<Weapon> getByPlatformCode(@PathVariable("platformCode") String platformCode) {
        return weaponService.getByPlatformCode(platformCode);
    }

    /**
     * 批量主键删除
     *
     * @param ids
     * @return
     */
    @Api
    @DeleteMapping(value = "/weapon/delete/batch")
    public int batchDelete(@RequestBody List<String> ids) {
        return weaponService.deleteByIds(ids);
    }
}
