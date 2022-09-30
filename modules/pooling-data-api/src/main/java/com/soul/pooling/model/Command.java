package com.soul.pooling.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Song
 * @Date 2022/9/30 9:36
 */
@Data
public class Command implements Serializable {

    private static final long serialVersionUID = -8622170908219931541L;
    private Integer type;
    //目标ID
    private List<Polygon> polygons;
    //任务执行时间
    private Long limitTime;

    private List<TargetData> targets;
}
