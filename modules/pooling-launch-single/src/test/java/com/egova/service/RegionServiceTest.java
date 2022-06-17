package com.egova.service;

import com.egova.BootstrapTest;
import com.egova.entity.Region;
import com.egova.generic.service.RegionService;
import com.egova.json.utils.JsonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 奔波儿灞
 * @since 1.0.0
 */
public class RegionServiceTest extends BootstrapTest {

    @Autowired
    private RegionService regionService;

    @Test
    public void insert() {
        Region region = JsonUtils.deserialize("{\"code\":\"110000\",\"name\":\"北京\",\"grade\":\"Province\"}", Region.class);
        String result = regionService.insert(region);
        System.out.println(result);
    }

    @Test
    public void root() {
        List<Region> result = regionService.root();
        System.out.println(result);
    }

}
