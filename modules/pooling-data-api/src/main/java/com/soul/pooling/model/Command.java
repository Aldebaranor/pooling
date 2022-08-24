package com.soul.pooling.model;


import com.soul.pooling.entity.enums.CommandType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @Description:命令
 * @Author: nemo
 * @Date: 2022/7/1
 */
@Data
public class Command implements Serializable {


    private static final long serialVersionUID = -4814402135783312522L;
    //命令唯一编号
    private Long id;
    //命令下发时间
    private Long time;
    //命令类型
    private CommandType type;
    //执行命令的主体
    private List<AttackChainData> instIds;
    //目标ID
    private List<TargetData> targets;
    //空间区域：巡逻区域或者航线，待打击的目标区域等
    private List<ScenarioGeometryData> spaceConstraints;
    //时间约束：多长时间内完成，单位s，用于集群算法做规划
    private Long timeConstraint;

}
