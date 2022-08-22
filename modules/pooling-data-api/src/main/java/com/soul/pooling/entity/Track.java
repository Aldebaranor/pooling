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
 * @Author: 王质松
 * @Date: 2022/8/20 16:18
 */

@Data
@Entity
@Table(name = "pooling_track")
@Display("跟踪")
@EqualsAndHashCode(callSuper = true)
public class Track extends BaseEntity {

    public static final String NAME = "pooling:track";
    private static final long serialVersionUID = 7229247478750143372L;


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
    @Column(name = "maxDetectRangeLand")
    private String maxDetectRangeLand;

    @Display("最大作用高度_陆")
    @Column(name = "maxDetectHeightLand")
    private String maxDetectHeightLand;

    @Display("最小作用高度_陆")
    @Column(name = "minDetectHeightLand")
    private String minDetectHeightLand;

    @Display("最小目标特性_陆")
    @Column(name = "minTargetFeatureLand")
    private String minTargetFeatureLand;

    @Display("情报周期_陆")
    @Column(name = "intelligenceCycleLand")
    private String intelligenceCycleLand;

    @Display("处理目标数_陆")
    @Column(name = "handleTargetNumLand")
    private String handleTargetNumLand;

    @Display("方位角_陆")
    @Column(name = "azimuthLand")
    private String azimuthLand;

    @Display("精度_陆")
    @Column(name = "precisionLand")
    private String precisionLand;


    //海
    @Display("最大作用距离_海")
    @Column(name = "maxDetectRangeSea")
    private String maxDetectRangeSea;

    @Display("最大作用高度_海")
    @Column(name = "maxDetectHeightSea")
    private String maxDetectHeightSea;

    @Display("最小作用高度_海")
    @Column(name = "minDetectHeightSea")
    private String minDetectHeightSea;

    @Display("最小目标特性_海")
    @Column(name = "minTargetFeatureSea")
    private String minTargetFeature;

    @Display("情报周期_海")
    @Column(name = "intelligenceCycleSea")
    private String intelligenceCycleSea;

    @Display("处理目标数_海")
    @Column(name = "handleTargetNumSea")
    private String handleTargetNumSea;

    @Display("方位角_海")
    @Column(name = "azimuthSea")
    private String azimuthSea;

    @Display("精度_海")
    @Column(name = "precisionSea")
    private String precisionSea;

    //空
    @Display("最大作用距离_空")
    @Column(name = "maxDetectRangeAir")
    private String maxDetectRangeAir;

    @Display("最大作用高度_空")
    @Column(name = "maxDetectHeightAir")
    private String maxDetectHeightAir;

    @Display("最小作用高度_空")
    @Column(name = "minDetectHeightAir")
    private String minDetectHeightAir;

    @Display("最小目标特性_空")
    @Column(name = "minTargetFeatureAir")
    private String minTargetFeatureAir;

    @Display("情报周期_空")
    @Column(name = "intelligenceCycleAir")
    private String intelligenceCycleAir;

    @Display("处理目标数_空")
    @Column(name = "handleTargetNumAir")
    private String handleTargetNumAir;

    @Display("方位角_空")
    @Column(name = "azimuthAir")
    private String azimuthAir;

    @Display("精度_空")
    @Column(name = "precisionAir")
    private String precisionAir;

    //天
    @Display("最大作用距离_天")
    @Column(name = "maxDetectRangeSpace")
    private String maxDetectRangeSpace;

    @Display("最大作用高度_天")
    @Column(name = "maxDetectHeightSpace")
    private String maxDetectHeightSpace;

    @Display("最小作用高度_天")
    @Column(name = "minDetectHeightSpace")
    private String minDetectHeightSpace;

    @Display("最小目标特性_天")
    @Column(name = "minTargetFeatureSpace")
    private String minTargetFeatureSpace;

    @Display("情报周期_天")
    @Column(name = "intelligenceCycleSpace")
    private String intelligenceCycleSpace;

    @Display("处理目标数_天")
    @Column(name = "handleTargetNumSpace")
    private String handleTargetNumSpace;

    @Display("方位角_天")
    @Column(name = "azimuthSpace")
    private String azimuthSpace;

    @Display("精度_天")
    @Column(name = "precisionSpace")
    private String precisionSpace;

    //潜
    @Display("最大作用距离_潜")
    @Column(name = "maxDetectRangeUnderSea")
    private String maxDetectRangeUnderSea;

    @Display("最大作用深度_潜")
    @Column(name = "maxDetectDepthUnderSea")
    private String maxDetectDepthUnderSea;

    @Display("最小作用深度_潜")
    @Column(name = "minDetectDepthUnderSea")
    private String minDetectDepthUnderSea;

    @Display("最小目标特性_潜")
    @Column(name = "minTargetFeatureUnderSea")
    private String minTargetFeatureUnderSea;

    @Display("情报周期_潜")
    @Column(name = "intelligenceCycleUnderSea")
    private String intelligenceCycleUnderSea;

    @Display("处理目标数_潜")
    @Column(name = "handleTargetNumUnderSea")
    private String handleTargetNumUnderSea;

    @Display("方位角_潜")
    @Column(name = "azimuthUnderSea")
    private String azimuthUnderSea;

    @Display("精度_潜")
    @Column(name = "precisionUnderSea")
    private String precisionUnderSea;

}
