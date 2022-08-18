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
    @Column(name = "maxFireRangeLand")
    private String maxFireRangeLand;

    @Display("最小射程_陆")
    @Column(name = "minFireRangeLand")
    private String minFireRangeLand;

    @Display("最大射高_陆")
    @Column(name = "maxFireHeightLand")
    private String maxFireHeightLand;

    @Display("最小射高_陆")
    @Column(name = "minFireHeightLand")
    private String minFireHeightLand;

    @Display("命中概率_陆")
    @Column(name = "hitRateLand")
    private float hitRateLand;
    //海
    @Display("最大射程_海")
    @Column(name = "maxFireRangeSea")
    private String maxFireRangeSea;

    @Display("最小射程_海")
    @Column(name = "minFireRangeSea")
    private String minFireRangeSea;

    @Display("最大射高_海")
    @Column(name = "maxFireHeightSea")
    private String maxFireHeightSea;

    @Display("最小射高_海")
    @Column(name = "minFireHeightSea")
    private String minFireHeightSea;

    @Display("命中概率_海")
    @Column(name = "hitRateSea")
    private float hitRateSea;
    //空
    @Display("最大射程_空")
    @Column(name = "maxFireRangeAir")
    private String maxFireRangeAir;

    @Display("最小射程_空")
    @Column(name = "minFireRangeAir")
    private String minFireRangeAir;

    @Display("最大射高_空")
    @Column(name = "maxFireHeightAir")
    private String maxFireHeightAir;

    @Display("最小射高_空")
    @Column(name = "minFireHeightAir")
    private String minFireHeightAir;

    @Display("命中概率_空")
    @Column(name = "hitRateAir")
    private float hitRateAir;
    //天
    @Display("最大射程_天")
    @Column(name = "maxFireRangeSpace")
    private String maxFireRangeSpace;

    @Display("最小射程_天")
    @Column(name = "minFireRangeSpace")
    private String minFireRangeSpace;

    @Display("最大射高_天")
    @Column(name = "maxFireHeightSpace")
    private String maxFireHeightSpace;

    @Display("最小射高_天")
    @Column(name = "minFireHeightSpace")
    private String minFireHeightSpace;

    @Display("命中概率_天")
    @Column(name = "hitRateSpace")
    private float hitRateSpace;
    //潜
    @Display("最大射程_潜")
    @Column(name = "maxFireRangeUnderSea")
    private String maxFireRangeUnderSea;

    @Display("最小射程_潜")
    @Column(name = "minFireRangeUnderSea")
    private String minFireRange;

    @Display("最大深度_潜")
    @Column(name = "maxFireDepthUnderSea")
    private String maxFireDepthUnderSea;

    @Display("最小深度_潜")
    @Column(name = "minFireDepthUnderSea")
    private String minFireDepthUnderSea;

    @Display("命中概率_潜")
    @Column(name = "hitRateUnderSea")
    private float hitRateUnderSea;

}
