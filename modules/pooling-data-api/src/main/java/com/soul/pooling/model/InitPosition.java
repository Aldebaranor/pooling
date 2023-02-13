package com.soul.pooling.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Song
 * @Date 2023/2/13 17:40
 */
@Data
public class InitPosition implements Serializable {
    private static final long serialVersionUID = 1714174072222644787L;
    private String id;
    private Double lon;
    private Double lat;
    private Double alt;
}
