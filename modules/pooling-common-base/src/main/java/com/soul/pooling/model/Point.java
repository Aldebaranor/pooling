package com.soul.pooling.model;

import com.egova.model.annotation.Display;
import lombok.Data;

import java.io.Serializable;


/**
 * @Description: 运动特征数据
 * @Author: nemo
 * @Date: 2022/3/23
 */
@Data
public class Point implements Serializable {

    private static final long serialVersionUID = -1033052226113628930L;
    @Display("经度")
    private Double lon;
    @Display("纬度")
    private Double lat;
    @Display("高度")
    private Double alt;

}
