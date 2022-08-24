package com.soul.pooling.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 点线面资源
 * @Author: nemo
 * @Date: 2022/5/23 6:25 PM
 */
@Data
public class ScenarioGeometryData implements Serializable {

    private static final long serialVersionUID = 589110833763053616L;
    //0-航线 1-区域 2.圆
    private Integer type;

    private List<Point> points;

    //如果是圆，这里存放圆的半径
    private Double radius;


}
