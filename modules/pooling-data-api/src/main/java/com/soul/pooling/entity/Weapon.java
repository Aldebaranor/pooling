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
@Table(name = "polling_weapon")
@Display("武器")
@EqualsAndHashCode(callSuper = true)
public class Weapon extends BaseEntity{

    public static final String NAME = "polling:weapon";

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

    @Display("数量")
    @Column(name = "number")
    private String number;


    @Display("对空作战能力（公里）")
    @Column(name = "fireAir")
    private float fireAir;

    @Display("对海作战能力（公里）")
    @Column(name = "fireSea")
    private float fireSea;

    @Display("对水下作战能力（公里）")
    @Column(name = "fireUnderSea")
    private float fireUnderSea;

    @Display("命中概率")
    @Column(name = "hitRate")
    private float hitRate;

    @Display("状态")
    @Column(name = "status")
    private int status;


    

}
