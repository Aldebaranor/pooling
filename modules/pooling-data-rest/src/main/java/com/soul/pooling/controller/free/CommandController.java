package com.soul.pooling.controller.free;

import com.egova.web.annotation.Api;
import com.soul.pooling.entity.enums.CommandType;
import com.soul.pooling.model.Command;
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
    public List<KillingChain> getKillChain(@RequestBody Command command) {
        CommandType type = commandService.getCommandType(command);
        if (type != CommandType.SEARCH) {
            return commandService.getKillChain(command);
        } else {
            return commandService.getSearch(command);
        }
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
    public KillingChain getTargetResource(@RequestBody Command command) {
        return commandService.getTargetResources(command);

//        return commandService.getTargetResource(command);
    }


}
