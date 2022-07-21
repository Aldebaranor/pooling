package com.soul.pooling.entity;


import com.egova.model.BaseEntity;
import com.egova.model.annotation.Display;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
* @Description: 
* @Author: nemo
* @Date: 2022/6/22
*/
@Data
@Entity
@Table(name = "pooling_sensor")
@Display("传感器")
@EqualsAndHashCode(callSuper = true)
public class Sensor extends BaseEntity{

    public static final String NAME = "pooling:sensor";

    @Id
    @Display("主键")
    @Column(name = "id")
    private String id;

    @Display("平台编号")
    @Column(name = "platformCode")
    private String platformCode;

    @Display("名称")
    @Column(name = "name")
    private String name;

    @Display("类型")
    @Column(name = "type")
    private String type;

    @Display("对空探测能力（公里）")
    @Column(name = "detectionAir")
    private float detectionAir;

    @Display("对海探测能力（公里）")
    @Column(name = "detectionSea")
    private float detectionSea;

    @Display("对水下探测能力（公里）")
    @Column(name = "detectionUnderSea")
    private float detectionUnderSea;

    @Display("状态")
    @Column(name = "status")
    private int status;



}
