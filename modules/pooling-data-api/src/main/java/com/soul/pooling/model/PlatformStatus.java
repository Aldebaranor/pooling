package com.soul.pooling.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 仿真节点状态$
 * @Author: nemo
 * @Date: 2022/6/20 6:13 PM
 */
@Data
public class PlatformStatus implements Serializable {

    private static final long serialVersionUID = 5770184557706043686L;

    private String platformId;

    private String name;

    private String code;

    private String type;

    private Boolean initStatus;

    private Boolean activeStatus;


}
