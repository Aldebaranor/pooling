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
public class ScenarioCommand implements Serializable {


    private static final long serialVersionUID = -7016465026472614488L;
    //命令唯一编号
    private Long id;

    private Long code;
    //命令类型
    private CommandType type;
    //执行命令的主体
    private List<AttackChainData> instIds;
    //目标ID
    private String targetId;
    //空间区域：巡逻区域或者航线，待打击的目标区域等
    private ScenarioGeometryData spaceConstraints;
    //任务开始时间 默认为0
    private Long startTime;
    private Long endTime;

}
