package com.soul.pooling.controller.free;

import com.egova.web.annotation.Api;
import com.soul.pooling.entity.*;
import com.soul.pooling.entity.enums.CommandType;
import com.soul.pooling.model.*;
import com.soul.pooling.service.EngageService;
import com.soul.pooling.service.FindService;
import com.soul.pooling.service.PoolingManagement;
import com.soul.pooling.service.PoolingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private FindService findService;

    @Autowired
    private EngageService engageService;

    @Autowired
    private PoolingService poolingService;


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
        //TODO 没有目标返回空
        //TODO修改数据库数据
        KillingChain killingChain = new KillingChain();
        List<Find> finds = new ArrayList<>();
        finds.add(management.getFindById("125"));
        finds.add(management.getFindById("72"));
        List<Fix> fixes = new ArrayList<>();
        fixes.add(management.getFixById("125"));
        fixes.add(management.getFixById("72"));
        List<Track> tracks = new ArrayList<>();
        tracks.add(management.getTrackById("125"));
        tracks.add(management.getTrackById("72"));
        List<Engage> engages = new ArrayList<>();
        engages.add(management.getEngageById("83"));

        List<Asses> asseses = new ArrayList<>();
        asseses.add(management.getAssesById("125"));
        asseses.add(management.getAssesById("72"));

        List<ResourceModel> find = poolingService.findToList(finds);
        List<ResourceModel> fix = poolingService.fixToList(fixes);
        List<ResourceModel> track = poolingService.trackToList(tracks);
        List<ResourceModel> engage = poolingService.engageToList(engages);
        List<ResourceModel> asses = poolingService.assesToList(asseses);

        killingChain.setFind(find);
        killingChain.setFix(fix);
        killingChain.setTrack(track);
        killingChain.setEngage(engage);
        killingChain.setTarget(asses);

        KillingChain killingChain1 = new KillingChain();

        List<Find> finds1 = new ArrayList<>();
        finds1.add(management.getFindById("131"));
        finds1.add(management.getFindById("75"));
        List<Fix> fixes1 = new ArrayList<>();
        fixes1.add(management.getFixById("131"));
        fixes1.add(management.getFixById("75"));
        List<Track> tracks1 = new ArrayList<>();
        tracks1.add(management.getTrackById("131"));
        tracks1.add(management.getTrackById("75"));
        List<Engage> engages1 = new ArrayList<>();
        engages1.add(management.getEngageById("87"));

        List<Asses> asseses1 = new ArrayList<>();
        asseses1.add(management.getAssesById("131"));
        asseses1.add(management.getAssesById("75"));

        List<ResourceModel> find1 = poolingService.findToList(finds1);
        List<ResourceModel> fix1 = poolingService.fixToList(fixes1);
        List<ResourceModel> track1 = poolingService.trackToList(tracks1);
        List<ResourceModel> engage1 = poolingService.engageToList(engages1);
        List<ResourceModel> asses1 = poolingService.assesToList(asseses1);

        killingChain1.setFind(find1);
        killingChain1.setFix(fix1);
        killingChain1.setTrack(track1);
        killingChain1.setEngage(engage1);
        killingChain1.setTarget(asses1);

        List<KillingChain> list = new ArrayList<>();
        list.add(killingChain);
        list.add(killingChain1);
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
        killingChain.setTarget(asses);

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
        CommandType type = CommandType.ATTACK;
        if (command.getType() == CommandType.ATTACK_AIR.getValue()) {
            type = CommandType.ATTACK_AIR;
        } else if (command.getType() == CommandType.ATTACK_SEA.getValue()) {
            type = CommandType.ATTACK_SEA;
        } else if (command.getType() == CommandType.ATTACK_LAND.getValue()) {
            type = CommandType.ATTACK_LAND;
        } else if (command.getType() == CommandType.ATTACK_UNDERSEA.getValue()) {
            type = CommandType.ATTACK_UNDERSEA;
        } else if (command.getType() == 25) {
            return null;
        } else {
            log.info("commandType 错误，取值不在21，22，23，24，25");
            return null;
        }
        //TODO反水雷

        KillingChain killingChain = new KillingChain();
        List<ResourceModel> find = poolingService.findToList(management.getFindPool(type));
        List<ResourceModel> fix = poolingService.fixToList(management.getFixPool(type));
        List<ResourceModel> track = poolingService.trackToList(management.getTrackPool(type));
        List<ResourceModel> target = poolingService.targetToList(management.getTargetPool(type));
        List<ResourceModel> engage = poolingService.engageToList(management.getEngagePool(type));
        List<ResourceModel> asses = poolingService.assesToList(management.getAssesPool(type));

        killingChain.setFind(find);
        killingChain.setFix(fix);
        killingChain.setTrack(track);
        killingChain.setTarget(target);
        killingChain.setEngage(engage);
        killingChain.setAsses(asses);

        //0确定那个方面战，对空按照下面的逻辑
        //1.根据每个目标当前位置A与 航向，航速外推200s得到B
        //2.筛选所有到AB最小距离小于其探测半径的 发现，定位，跟踪资源
        //2.筛选所有到AB最小距离小于其火力半径半径的武器资源
        //3.筛选所有到AB最小距离小于其探测半径的评估资源

        for (TargetData targetData : command.getTargets()) {
            Point start = new Point();
            Point end = new Point();
            start.setLon(targetData.getMoveDetect().getLon());
            start.setLat(targetData.getMoveDetect().getLat());

            Double heading = targetData.getMoveDetect().getHeading();
            Double speed = targetData.getMoveDetect().getSpeed();

        }

        return killingChain;
    }


}
