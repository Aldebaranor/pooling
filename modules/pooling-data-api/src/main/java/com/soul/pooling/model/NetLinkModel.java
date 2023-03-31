package com.soul.pooling.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Song
 * @Date 2023/3/29 11:13
 */
@Data
public class NetLinkModel implements Serializable {
    private static final long serialVersionUID = -1632785053875274547L;
    private String id;
    private String type;
    private Double transRate;
    private Double bandWidth;
    private Integer minTimeDelay;
    private Integer maxTimeDelay;
}
