package com.soul.pooling.entity;


import com.egova.model.BaseEntity;
import com.egova.model.annotation.Display;
import com.soul.pooling.entity.enums.ResourceStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @Author: 王质松
 * @Date: 2022/8/20 16:17
 */
@Data
@Entity
@Table(name = "pooling_asses")
@Display("评估")
@EqualsAndHashCode(callSuper = true)
public class Asses extends BaseEntity {

    public static final String NAME = "pooling:asses";
    private static final long serialVersionUID = -6856640712360649905L;

    @Id
    @Display("主键")
    @Column(name = "id")
    private String id;

    @Display("传感器编号")
    @Column(name = "deviceCode")
    private String deviceCode;

    @Display("名称")
    @Column(name = "name")
    private String name;

    @Display("类型")
    @Column(name = "type")
    private String type;

    @Transient
    @Display("设备状态")
    private ResourceStatus status;

    @Display("平台编号")
    @Column(name = "platformCode")
    private String platformCode;

    @Display("平台名称")
    @Column(name = "platformName")
    private String platformName;

    //陆
    @Display("最大作用距离_陆")
    @Column(name = "maxDetectRangeLand")
    private Float maxDetectRangeLand;

    @Display("最大作用高度_陆")
    @Column(name = "maxDetectHeightLand")
    private Float maxDetectHeightLand;

    @Display("最小作用高度_陆")
    @Column(name = "minDetectHeightLand")
    private Float minDetectHeightLand;

    @Display("最小目标特性_陆")
    @Column(name = "minTargetFeatureLand")
    private Float minTargetFeatureLand;

    @Display("情报周期_陆")
    @Column(name = "intelligenceCycleLand")
    private Float intelligenceCycleLand;

    @Display("处理目标数_陆")
    @Column(name = "handleTargetNumLand")
    private Integer handleTargetNumLand;

    @Display("方位角_陆")
    @Column(name = "azimuthLand")
    private Float azimuthLand;

    @Display("精度_陆")
    @Column(name = "precisionLand")
    private Float precisionLand;


    //海
    @Display("最大作用距离_海")
    @Column(name = "maxDetectRangeSea")
    private Float maxDetectRangeSea;

    @Display("最大作用高度_海")
    @Column(name = "maxDetectHeightSea")
    private Float maxDetectHeightSea;

    @Display("最小作用高度_海")
    @Column(name = "minDetectHeightSea")
    private Float minDetectHeightSea;

    @Display("最小目标特性_海")
    @Column(name = "minTargetFeatureSea")
    private Float minTargetFeature;

    @Display("情报周期_海")
    @Column(name = "intelligenceCycleSea")
    private Float intelligenceCycleSea;

    @Display("处理目标数_海")
    @Column(name = "handleTargetNumSea")
    private Integer handleTargetNumSea;

    @Display("方位角_海")
    @Column(name = "azimuthSea")
    private Float azimuthSea;

    @Display("精度_海")
    @Column(name = "precisionSea")
    private Float precisionSea;

    //空
    @Display("最大作用距离_空")
    @Column(name = "maxDetectRangeAir")
    private Float maxDetectRangeAir;

    @Display("最大作用高度_空")
    @Column(name = "maxDetectHeightAir")
    private Float maxDetectHeightAir;

    @Display("最小作用高度_空")
    @Column(name = "minDetectHeightAir")
    private Float minDetectHeightAir;

    @Display("最小目标特性_空")
    @Column(name = "minTargetFeatureAir")
    private Float minTargetFeatureAir;

    @Display("情报周期_空")
    @Column(name = "intelligenceCycleAir")
    private Float intelligenceCycleAir;

    @Display("处理目标数_空")
    @Column(name = "handleTargetNumAir")
    private Integer handleTargetNumAir;

    @Display("方位角_空")
    @Column(name = "azimuthAir")
    private Float azimuthAir;

    @Display("精度_空")
    @Column(name = "precisionAir")
    private Float precisionAir;

    //天
    @Display("最大作用距离_天")
    @Column(name = "maxDetectRangeSpace")
    private Float maxDetectRangeSpace;

    @Display("最大作用高度_天")
    @Column(name = "maxDetectHeightSpace")
    private Float maxDetectHeightSpace;

    @Display("最小作用高度_天")
    @Column(name = "minDetectHeightSpace")
    private Float minDetectHeightSpace;

    @Display("最小目标特性_天")
    @Column(name = "minTargetFeatureSpace")
    private Float minTargetFeatureSpace;

    @Display("情报周期_天")
    @Column(name = "intelligenceCycleSpace")
    private Float intelligenceCycleSpace;

    @Display("处理目标数_天")
    @Column(name = "handleTargetNumSpace")
    private Integer handleTargetNumSpace;

    @Display("方位角_天")
    @Column(name = "azimuthSpace")
    private Float azimuthSpace;

    @Display("精度_天")
    @Column(name = "precisionSpace")
    private Float precisionSpace;

    //潜
    @Display("最大作用距离_潜")
    @Column(name = "maxDetectRangeUnderSea")
    private Float maxDetectRangeUnderSea;

    @Display("最大作用深度_潜")
    @Column(name = "maxDetectDepthUnderSea")
    private Float maxDetectDepthUnderSea;

    @Display("最小作用深度_潜")
    @Column(name = "minDetectDepthUnderSea")
    private Float minDetectDepthUnderSea;

    @Display("最小目标特性_潜")
    @Column(name = "minTargetFeatureUnderSea")
    private Float minTargetFeatureUnderSea;

    @Display("情报周期_潜")
    @Column(name = "intelligenceCycleUnderSea")
    private Float intelligenceCycleUnderSea;

    @Display("处理目标数_潜")
    @Column(name = "handleTargetNumUnderSea")
    private Integer handleTargetNumUnderSea;

    @Display("方位角_潜")
    @Column(name = "azimuthUnderSea")
    private Float azimuthUnderSea;

    @Display("精度_潜")
    @Column(name = "precisionUnderSea")
    private Float precisionUnderSea;


}
