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
@Table(name = "pooling_weapon")
@Display("武器")
@EqualsAndHashCode(callSuper = true)
public class Weapon extends BaseEntity {

    public static final String NAME = "pooling:weapon";
    private static final long serialVersionUID = 1771810429769970902L;

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

    @Display("对太空作战能力")
    @Column(name = "fireSpace")
    private float fireSpace;

    @Display("对空作战能力（公里）")
    @Column(name = "fireAir")
    private float fireAir;

    @Display("对海作战能力（公里）")
    @Column(name = "fireSea")
    private float fireSea;

    @Display("对陆作战能力（公里）")
    @Column(name = "fireLand")
    private float fireLand;

    @Display("对水下作战能力（公里）")
    @Column(name = "fireUnderSea")
    private float fireUnderSea;

    @Display("命中概率")
    @Column(name = "hitRate")
    private float hitRate;

    @Display("状态")
    @Column(name = "status")
    private int status;

    @Display("武器编号")
    @Column(name = "weaponCode")
    private String weaponCode;

    //2022_08_18新增字段

    @Display("制导方式")
    @Column(name = "guidanceType")
    private String guidanceType;

    @Display("系统反应时间")
    @Column(name = "reactionTime")
    private String reactionTime;

    @Display("最大飞行速度")
    @Column(name = "maxSpeed")
    private String maxSpeed;

    @Display("齐射最小时间")
    @Column(name = "salvoMinTime")
    private String salvoMinTime;

    
    //陆
    @Display("最大射程_陆")
    @Column(name = "maxFireLandRange")
    private String maxFireLandRange;

    @Display("最小射程_陆")
    @Column(name = "minFireLandRange")
    private String minFireLandRange;

    @Display("最大射高_陆")
    @Column(name = "maxFireLandHeight")
    private String maxFireLandHeight;

    @Display("最小射高_陆")
    @Column(name = "minFireLandHeight")
    private String minFireLandHeight;

    @Display("命中概率_陆")
    @Column(name = "landHitRate")
    private float landHitRate;
    //海
    @Display("最大射程_海")
    @Column(name = "maxFireSeaRange")
    private String maxFireSeaRange;

    @Display("最小射程_海")
    @Column(name = "minFireSeaRange")
    private String minFireSeaRange;

    @Display("最大射高_海")
    @Column(name = "maxFireSeaHeight")
    private String maxFireSeaHeight;

    @Display("最小射高_海")
    @Column(name = "minFireSeaHeight")
    private String minFireSeaHeight;

    @Display("命中概率_海")
    @Column(name = "seaHitRate")
    private float seaHitRate;
    //空
    @Display("最大射程_空")
    @Column(name = "maxFireAirRange")
    private String maxFireAirRange;

    @Display("最小射程_空")
    @Column(name = "minFireAirRange")
    private String minFireAirRange;

    @Display("最大射高_空")
    @Column(name = "maxFireAirHeight")
    private String maxFireAirHeight;

    @Display("最小射高_空")
    @Column(name = "minFireAirHeight")
    private String minFireAirHeight;

    @Display("命中概率_空")
    @Column(name = "airHitRate")
    private float airHitRate;
    //天
    @Display("最大射程_天")
    @Column(name = "maxFireSpaceRange")
    private String maxFireSpaceRange;

    @Display("最小射程_天")
    @Column(name = "minFireSpaceRange")
    private String minFireSpaceRange;

    @Display("最大射高_天")
    @Column(name = "maxFireSpaceHeight")
    private String maxFireSpaceHeight;

    @Display("最小射高_天")
    @Column(name = "minFireSpaceHeight")
    private String minFireSpaceHeight;

    @Display("命中概率_天")
    @Column(name = "spaceHitRate")
    private float spaceHitRate;
    //潜
    @Display("最大射程_潜")
    @Column(name = "maxFireUnderSeaRange")
    private String maxFireUnderSeaRange;

    @Display("最小射程_潜")
    @Column(name = "minFireUnderSeaRange")
    private String minFireUnderSeaRange;

    @Display("最大深度_潜")
    @Column(name = "maxFireUnderSeaDepth")
    private String maxFireUnderSeaDepth;

    @Display("最小深度_潜")
    @Column(name = "minFireUnderSeaDepth")
    private String minFireUnderSeaDepth;

    @Display("命中概率_潜")
    @Column(name = "underSeaHitRate")
    private float underSeaHitRate;

}
