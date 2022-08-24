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
@Table(name = "pooling_target")
@Display("指示")
@EqualsAndHashCode(callSuper = true)
public class Target extends BaseEntity {

    public static final String NAME = "pooling:target";
    private static final long serialVersionUID = 7542539473878274597L;

    @Id
    @Display("主键")
    @Column(name = "id")
    private String id;

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


}
