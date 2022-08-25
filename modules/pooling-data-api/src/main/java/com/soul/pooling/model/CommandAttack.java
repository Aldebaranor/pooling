package com.soul.pooling.model;


import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @Description:命令
 * @Author: nemo
 * @Date: 2022/7/1
 */
@Data
public class CommandAttack implements Serializable {

    private static final long serialVersionUID = -4814402135783312522L;
    //命令类型
    private Integer type;
    //目标ID
    private List<TargetData> targets;

}
