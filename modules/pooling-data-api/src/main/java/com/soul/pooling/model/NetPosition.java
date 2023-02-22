package com.soul.pooling.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Song
 * @Date 2023/2/13 17:40
 * 陆工大态势接口
 */
@Data
public class NetPosition implements Serializable {
    private static final long serialVersionUID = 1714174072222644787L;
    private String id;
    //空中0，水下1，水面2
    private Integer kind;
    private Double lon;
    private Double lat;
    private Double alt;
}

