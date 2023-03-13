package com.soul.pooling.model;

import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * @Description: 仿真节点状态$
 * @Author: nemo
 * @Date: 2022/6/20 6:13 PM
 */
@Data
@Entity
public class PlatformStatus implements Serializable {

    private static final long serialVersionUID = 5770184557706043686L;

    private String platformId;

    private String name;

    private String code;

    private String type;

    private Boolean initStatus;

    private Boolean activeStatus;

    private PlatformMoveData moveData;

    private Boolean beRealEquipment;

    private Boolean beMineSweep;

    private Float speed;

    private Integer kind;

    private Long lastOnlineTime;
    
    private Long lastOperationUsedTime;

}
