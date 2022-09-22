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
public class CommandSearch implements Serializable {

    private static final long serialVersionUID = 1705953663540673315L;
    //命令类型
    private Integer type;
    //目标ID
    private List<Polygon> polygons;
    //任务执行时间
    private Long limitTime;


}
