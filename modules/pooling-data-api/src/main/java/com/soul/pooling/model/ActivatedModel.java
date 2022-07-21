package com.soul.pooling.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 仿真节点状态$
 * @Author: nemo
 * @Date: 2022/6/20 6:13 PM
 */
@Data
public class ActivatedModel implements Serializable {

    private static final long serialVersionUID = 9035961674754003032L;

    private String id; //兵力编号

    private String type;//1.入云 0 注销



}
