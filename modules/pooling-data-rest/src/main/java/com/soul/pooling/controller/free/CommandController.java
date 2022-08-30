package com.soul.pooling.controller.free;

import com.egova.web.annotation.Api;
import com.flagwind.commons.StringUtils;
import com.soul.pooling.entity.*;
import com.soul.pooling.entity.enums.CommandType;
import com.soul.pooling.model.*;
import com.soul.pooling.service.CommandService;
import com.soul.pooling.service.PoolingManagement;
import com.soul.pooling.service.PoolingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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


    /**
     * 智能调度
     *
     * @param command
     * @return <目标,打击连></>
     * //0确定那个方面战，对空按照下面的逻辑
     * //1.根据每个目标当前位置A与 航向，航速外推200s得到B
     * //2.筛选出所有武器到AB的最小距离小于武器射程
     * //3.按武器所在位置距离A的距离排序，排序得到前两个武器
     * //4.在前两个中根据先余弹量后距离排序选出一个的武器进行打击，根据命中概率计算需要几发弹
     * //5.选择这个武器所属平台的传感器开机作为跟踪资源
     * //6.选择离A点最近的find资源
     * //7.选择离A点最近的fix track资源作为传感器+执行打击武器的track资源
     * //8。选择离B点最近的asses
     */
    @Api
    @PostMapping("/mission")
    public List<KillingChain> getKillChain(@RequestBody CommandAttack command) {
        if (command.getType().equals(CommandType.ATTACK_AIR.getValue())) {
            return commandService.getAir(command);
        }
        List<KillingChain> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(command.getTargets())) {
            return list;
        }
        for (TargetData target : command.getTargets()) {
            KillingChain killingChain = new KillingChain();
            killingChain.setTargetId(target.getInstId());
            killingChain.setTargetName(target.getName());

            List<Find> finds = new ArrayList<>();
            finds.add(management.getFindById("62"));
            List<Fix> fixes = new ArrayList<>();
            fixes.add(management.getFixById("62"));
            List<Track> tracks = new ArrayList<>();
            tracks.add(management.getTrackById("62"));
            tracks.add(management.getTrackById("26"));
            List<Engage> engages = new ArrayList<>();
            Engage weapon = management.getEngageById("27");
            weapon.setNumber("2");
            engages.add(weapon);
            List<Asses> assesList = new ArrayList<>();
            assesList.add(management.getAssesById("62"));
            assesList.add(management.getAssesById("26"));

            List<ResourceModel> find = poolingService.findToList(finds);
            List<ResourceModel> fix = poolingService.fixToList(fixes);
            List<ResourceModel> track = poolingService.trackToList(tracks);
            List<ResourceModel> engage = poolingService.engageToList(engages);
            List<ResourceModel> asses = poolingService.assesToList(assesList);

            killingChain.setFind(find);
            killingChain.setFix(fix);
            killingChain.setTrack(track);
            killingChain.setEngage(engage);
            killingChain.setAsses(asses);

            list.add(killingChain);
        }


        return list;
    }

    @Api
    @PostMapping("/mission-search")
    public List<KillingChain> getKillChain(@RequestBody CommandSearch command) {
        List<KillingChain> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(command.getPolygons())) {
            return list;
        }
        for (Polygon polygon : command.getPolygons()) {
            KillingChain killingChain = new KillingChain();
            killingChain.setTargetName(polygon.getName());
            killingChain.setPolygon(polygon);
            List<Find> finds = new ArrayList<>();
            if(StringUtils.equals(polygon.getName(),"area_4")){
                finds.add(management.getFindById("132"));
                finds.add(management.getFindById("95"));
            }
            if(StringUtils.equals(polygon.getName(),"area_1")){
                finds.add(management.getFindById("135"));
                finds.add(management.getFindById("136"));
                finds.add(management.getFindById("139"));
                finds.add(management.getFindById("96"));
                finds.add(management.getFindById("100"));
                finds.add(management.getFindById("97"));
                finds.add(management.getFindById("98"));
            }
            List<ResourceModel> find = poolingService.findToList(finds);
            killingChain.setFind(find);
            list.add(killingChain);


        }
        return list;

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
