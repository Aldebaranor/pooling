package com.soul.pooling.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @Description: 运动特征数据
 * @Author: nemo
 * @Date: 2022/3/23
 */
@Data
public class Polygon implements Serializable {

    private static final long serialVersionUID = 286970925568931455L;
    private String id;
    private String name;
    private List<Point> points;

}
