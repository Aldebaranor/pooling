package com.soul.pooling.service.impl;

import com.egova.data.service.AbstractRepositoryBase;
import com.egova.data.service.TemplateService;
import com.egova.model.PageResult;
import com.egova.model.QueryModel;
import com.soul.pooling.condition.WeaponCondition;
import com.soul.pooling.domain.WeaponRepository;
import com.soul.pooling.entity.Weapon;
import com.soul.pooling.service.WeaponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @date 2022/7/21 10:47
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeaponServiceImpl extends TemplateService<Weapon,String> implements WeaponService {

    private final WeaponRepository weaponRepository;

    @Override
    protected AbstractRepositoryBase<Weapon, String> getRepository() {
        return weaponRepository;
    }

    @Override
    public String insert(Weapon weapon){

        return super.insert(weapon);
    }

    @Override
    public void update(Weapon weapon){

        super.update(weapon);
    }

    @Override
    public PageResult<Weapon> page(QueryModel<WeaponCondition> model){
        return super.page(model.getCondition(),model.getPaging(),model.getSorts());
    }
}
