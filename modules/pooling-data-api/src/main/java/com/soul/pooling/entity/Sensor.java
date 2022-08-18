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
public class Sensor extends BaseEntity {

    public static final String NAME = "pooling:sensor";
    private static final long serialVersionUID = 2436267303321952970L;

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

    @Display("对太空探测能力")
    @Column(name = "detectionSpace")
    private float detectionSpace;

    @Display("对空探测能力（公里）")
    @Column(name = "detectionAir")
    private float detectionAir;

    @Display("对海探测能力（公里）")
    @Column(name = "detectionSea")
    private float detectionSea;

    @Display("对陆地探测能力（公里）")
    @Column(name = "detectionLand")
    private float detectionLand;

    @Display("对水下探测能力（公里）")
    @Column(name = "detectionUnderSea")
    private float detectionUnderSea;

    @Display("状态")
    @Column(name = "status")
    private int status;

    @Display("传感器编号")
    @Column(name = "sensorCode")
    private String sensorCode;

    
    //陆
    @Display("最大作用距离_陆")
    @Column(name = "maxDetectLandRange")
    private String maxDetectLandRange;

    @Display("最大作用高度_陆")
    @Column(name = "maxDetectLandHeight")
    private String maxDetectLandHeight;

    @Display("最小作用高度_陆")
    @Column(name = "minDetectLandHeight")
    private String minDetectLandHeight;

    @Display("最小目标特性_陆")
    @Column(name = "minTargetLandFeature")
    private String minTargetLandFeature;

    @Display("情报周期_陆")
    @Column(name = "landIntelligenceCycle")
    private String landIntelligenceCycle;

    @Display("处理目标数_陆")
    @Column(name = "landHandleTargetNum")
    private String landHandleTargetNum;

    @Display("方位角_陆")
    @Column(name = "landAzimuth")
    private String landAzimuth;

    @Display("精度_陆")
    @Column(name = "landPrecision")
    private String landPrecision;


    //海
    @Display("最大作用距离_海")
    @Column(name = "maxDetectSeaRange")
    private String maxDetectSeaRange;

    @Display("最大作用高度_海")
    @Column(name = "maxDetectSeaHeight")
    private String maxDetectSeaHeight;

    @Display("最小作用高度_海")
    @Column(name = "minDetectSeaHeight")
    private String minDetectSeaHeight;

    @Display("最小目标特性_海")
    @Column(name = "minSeaTargetFeature")
    private String minSeaTargetFeature;

    @Display("情报周期_海")
    @Column(name = "seaIntelligenceCycle")
    private String seaIntelligenceCycle;

    @Display("处理目标数_海")
    @Column(name = "seaHandleTargetNum")
    private String seaHandleTargetNum;

    @Display("方位角_海")
    @Column(name = "seaAzimuth")
    private String seaAzimuth;

    @Display("精度_海")
    @Column(name = "seaPrecision")
    private String seaPrecision;

    //空
    @Display("最大作用距离_空")
    @Column(name = "maxDetectAirRange")
    private String maxDetectAirRange;

    @Display("最大作用高度_空")
    @Column(name = "maxDetectAirHeight")
    private String maxDetectAirHeight;

    @Display("最小作用高度_空")
    @Column(name = "minDetectAirHeight")
    private String minDetectAirHeight;

    @Display("最小目标特性_空")
    @Column(name = "minAirTargetFeature")
    private String minAirTargetFeature;

    @Display("情报周期_空")
    @Column(name = "AirIntelligenceCycle")
    private String AirIntelligenceCycle;

    @Display("处理目标数_空")
    @Column(name = "airHandleTargetNum")
    private String airHandleTargetNum;

    @Display("方位角_空")
    @Column(name = "airAzimuth")
    private String airAzimuth;

    @Display("精度_空")
    @Column(name = "airPrecision")
    private String airPrecision;

    //天
    @Display("最大作用距离_天")
    @Column(name = "maxDetectSpaceRange")
    private String maxDetectSpaceRange;

    @Display("最大作用高度_天")
    @Column(name = "maxDetectSpaceHeight")
    private String maxDetectSpaceHeight;

    @Display("最小作用高度_天")
    @Column(name = "minDetectSpaceHeight")
    private String minDetectSpaceHeight;

    @Display("最小目标特性_天")
    @Column(name = "minSpaceTargetFeature")
    private String minSpaceTargetFeature;

    @Display("情报周期_天")
    @Column(name = "spaceIntelligenceCycle")
    private String spaceIntelligenceCycle;

    @Display("处理目标数_天")
    @Column(name = "spaceHandleTargetNum")
    private String spaceHandleTargetNum;

    @Display("方位角_天")
    @Column(name = "spaceAzimuth")
    private String spaceAzimuth;

    @Display("精度_天")
    @Column(name = "spacePrecision")
    private String spacePrecision;

    //潜
    @Display("最大作用距离_潜")
    @Column(name = "maxDetectUnderSeaRange")
    private String maxDetectUnderSeaRange;

    @Display("最大作用深度_潜")
    @Column(name = "maxDetectUnderSeaDepth")
    private String maxDetectUnderSeaDepth;

    @Display("最小作用高度_潜")
    @Column(name = "minDetectUnderSeaDepth")
    private String minDetectUnderSeaDepth;

    @Display("最小目标特性_潜")
    @Column(name = "minUnderSeaTargetFeature")
    private String minUnderSeaTargetFeature;

    @Display("情报周期_潜")
    @Column(name = "underSeaIntelligenceCycle")
    private String underSeaIntelligenceCycle;

    @Display("处理目标数_潜")
    @Column(name = "underSeaHandleTargetNum")
    private String underSeaHandleTargetNum;

    @Display("方位角_潜")
    @Column(name = "underSeaAzimuth")
    private String underSeaAzimuth;

    @Display("精度_潜")
    @Column(name = "underSeaPrecision")
    private String underSeaPrecision;

}
