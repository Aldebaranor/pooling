package com.soul.pooling.entity;


import com.egova.model.BaseEntity;
import com.egova.model.annotation.Display;
import com.soul.pooling.entity.enums.ResourceStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


/**
 * @Description:
 * @Author: nemo
 * @Date: 2022/6/22
 */
@Data
@Entity
@Table(name = "pooling_engage")
@Display("交战")
@EqualsAndHashCode(callSuper = true)
public class Engage extends BaseEntity {

    public static final String NAME = "pooling:engage";
    private static final long serialVersionUID = -5807693761892818825L;

    @Id
    @Display("主键")
    @Column(name = "id")
    private String id;

    @Display("武器编号")
    @Column(name = "deviceCode")
    private String deviceCode;

    @Display("设备类型")
    @Column(name = "deviceType")
    private String deviceType;

    @Display("名称")
    @Column(name = "name")
    private String name;

    @Display("类型")
    @Column(name = "type")
    private String type;

    @Transient
    @Display("设备状态")
    private ResourceStatus status;

    @Display("平台名称")
    @Column(name = "platformName")
    private String platformName;

    @Display("平台编号")
    @Column(name = "platformCode")
    private String platformCode;

    @Display("数量")
    @Column(name = "number")
    private Integer number;

    @Display("命中概率")
    @Column(name = "hitRate")
    private Float hitRate;

    //2022_08_18新增字段

    @Display("制导方式")
    @Column(name = "guidanceType")
    private String guidanceType;

    @Display("系统反应时间")
    @Column(name = "reactionTime")
    private String reactionTime;

    @Display("最大飞行速度")
    @Column(name = "maxSpeed")
    private Float maxSpeed;

    @Display("齐射最小时间")
    @Column(name = "salvoMinTime")
    private String salvoMinTime;


    //陆
    @Display("最大射程_陆")
    @Column(name = "maxFireRangeLand")
    private Float maxFireRangeLand;

    @Display("最小射程_陆")
    @Column(name = "minFireRangeLand")
    private Float minFireRangeLand;

    @Display("最大射高_陆")
    @Column(name = "maxFireHeightLand")
    private Float maxFireHeightLand;

    @Display("最小射高_陆")
    @Column(name = "minFireHeightLand")
    private Float minFireHeightLand;

    //海
    @Display("最大射程_海")
    @Column(name = "maxFireRangeSea")
    private Float maxFireRangeSea;

    @Display("最小射程_海")
    @Column(name = "minFireRangeSea")
    private Float minFireRangeSea;

    @Display("最大射高_海")
    @Column(name = "maxFireHeightSea")
    private Float maxFireHeightSea;

    @Display("最小射高_海")
    @Column(name = "minFireHeightSea")
    private Float minFireHeightSea;

    //空
    @Display("最大射程_空")
    @Column(name = "maxFireRangeAir")
    private Float maxFireRangeAir;

    @Display("最小射程_空")
    @Column(name = "minFireRangeAir")
    private Float minFireRangeAir;

    @Display("最大射高_空")
    @Column(name = "maxFireHeightAir")
    private Float maxFireHeightAir;

    @Display("最小射高_空")
    @Column(name = "minFireHeightAir")
    private Float minFireHeightAir;

    //天
    @Display("最大射程_天")
    @Column(name = "maxFireRangeSpace")
    private Float maxFireRangeSpace;

    @Display("最小射程_天")
    @Column(name = "minFireRangeSpace")
    private Float minFireRangeSpace;

    @Display("最大射高_天")
    @Column(name = "maxFireHeightSpace")
    private Float maxFireHeightSpace;

    @Display("最小射高_天")
    @Column(name = "minFireHeightSpace")
    private Float minFireHeightSpace;

    //潜
    @Display("最大射程_潜")
    @Column(name = "maxFireRangeUnderSea")
    private Float maxFireRangeUnderSea;

    @Display("最小射程_潜")
    @Column(name = "minFireRangeUnderSea")
    private Float minFireRange;

    @Display("最大深度_潜")
    @Column(name = "maxFireDepthUnderSea")
    private Float maxFireDepthUnderSea;

    @Display("最小深度_潜")
    @Column(name = "minFireDepthUnderSea")
    private Float minFireDepthUnderSea;

    @Transient
    @Display("距目标距离")
    private Double distance;
}
