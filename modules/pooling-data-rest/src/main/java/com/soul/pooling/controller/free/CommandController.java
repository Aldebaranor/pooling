package com.soul.pooling.controller.free;

import com.egova.web.annotation.Api;
import com.soul.pooling.model.CommandAttack;
import com.soul.pooling.model.CommandSearch;
import com.soul.pooling.model.KillingChain;
import com.soul.pooling.model.ResourceModel;
import com.soul.pooling.service.CommandService;
import com.soul.pooling.service.PoolingManagement;
import com.soul.pooling.service.PoolingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Song
 * @Date 2022/8/23 14:43
 */
@Slf4j
@RestController
@RequestMapping("/free/pooling/command")
@RequiredArgsConstructor
public class CommandController {

    @Autowired
    private PoolingManagement management;

    @Autowired
    private PoolingService poolingService;

    @Autowired
    private CommandService commandService;

    @Api
    @PostMapping("/mission")
    public List<KillingChain> getKillChain(@RequestBody CommandAttack command) {
        return commandService.getKillChain(command);
    }

    @Api
    @PostMapping("/mission-search")
    public List<KillingChain> getKillChain(@RequestBody CommandSearch command) {
//        List<KillingChain> list = new ArrayList<>();
//        if (CollectionUtils.isEmpty(command.getPolygons())) {
//            return list;
//        }
//        for (Polygon polygon : command.getPolygons()) {
//            KillingChain killingChain = new KillingChain();
//            killingChain.setTargetName(polygon.getName());
//            killingChain.setPolygon(polygon);
//            List<Find> finds = new ArrayList<>();
//            if (StringUtils.equals(polygon.getName(), "area_4")) {
//                finds.add(management.getFindById("132"));
//                finds.add(management.getFindById("95"));
//            }
//            if (StringUtils.equals(polygon.getName(), "area_1")) {
//                finds.add(management.getFindById("135"));
//                finds.add(management.getFindById("136"));
//                finds.add(management.getFindById("139"));
//                finds.add(management.getFindById("96"));
//                finds.add(management.getFindById("100"));
//                finds.add(management.getFindById("99"));
//                finds.add(management.getFindById("97"));
//                finds.add(management.getFindById("98"));
//            }
//            List<ResourceModel> find = poolingService.findToList(finds);
//            killingChain.setFind(find);
//            list.add(killingChain);
//
//
//        }
//        return list;
        return commandService.getSearch(command);
    }

    @Api
    @GetMapping(value = "/resource/get")
    public KillingChain getTest() {
        KillingChain killingChain = new KillingChain();
        List<ResourceModel> find = poolingService.findToList(management.getFindPool(null));
        List<ResourceModel> fix = poolingService.fixToList(management.getFixPool(null));
        List<ResourceModel> track = poolingService.trackToList(management.getTrackPool(null));
        List<ResourceModel> target = poolingService.targetToList(management.getTargetPool(null));
        List<ResourceModel> engage = poolingService.engageToList(management.getEngagePool(null));
        List<ResourceModel> asses = poolingService.assesToList(management.getAssesPool(null));

        killingChain.setFind(find);
        killingChain.setFix(fix);
        killingChain.setTrack(track);
        killingChain.setTarget(target);
        killingChain.setEngage(engage);
        killingChain.setAsses(asses);

        return killingChain;
    }

    /**
     * 资源筛选,根据目标过滤资源池
     *
     * @param command
     * @return
     */
    @Api
    @PostMapping("/resource")
    public KillingChain getTargetResource(@RequestBody CommandAttack command) {

        return commandService.getTargetResource(command);
    }


}
