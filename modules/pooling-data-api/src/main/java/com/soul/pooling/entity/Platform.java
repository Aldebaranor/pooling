package com.soul.pooling.entity;


import com.egova.model.BaseEntity;
import com.egova.model.annotation.Display;
import com.soul.pooling.model.PlatformMoveData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;


/**
 * @Description:
 * @Author: nemo
 * @Date: 2022/6/22
 */
@Data
@Entity
@Table(name = "pooling_platform")
@Display("平台")
@EqualsAndHashCode(callSuper = true)
public class Platform extends BaseEntity {

    public static final String NAME = "pooling:platform";
    private static final long serialVersionUID = 5661780821831581581L;

    @Id
    @Display("主键")
    @Column(name = "id")
    private String id;

    @Display("编号")
    @Column(name = "code")
    private String code;

    @Display("名称")
    @Column(name = "name")
    private String name;

    @Display("类型")
    @Column(name = "type")
    private String type;

    @Display("续航（公里）")
    @Column(name = "battery")
    private String battery;

    @Display("最大航速")
    @Column(name = "maxSpeed")
    private float speed;

    @Display("状态")
    @Column(name = "status")
    private int status;

    @Display("是否实装")
    @Column(name = "beRealEquipment")
    private Boolean beRealEquipment;

    @Display("是否扫雷")
    @Column(name = "beMineSweep")
    private Boolean beMineSweep;

    @Transient
    @Display("位置信息")
    private PlatformMoveData platformMoveData;

    @Transient
    @JoinColumn(name = "code")
    @OneToMany(targetEntity = Sensor.class, mappedBy = "platformCode")
    private List<Sensor> sensors;

    @Transient
    @JoinColumn(name = "code")
    @OneToMany(targetEntity = Weapon.class, mappedBy = "platformCode")
    private List<Weapon> weapons;

    //2F2TEA

    @Transient
    @JoinColumn(name = "code")
    @OneToMany(targetEntity = Find.class, mappedBy = "platformCode")
    private List<Find> finds;

    @Transient
    @JoinColumn(name = "code")
    @OneToMany(targetEntity = Fix.class, mappedBy = "platformCode")
    private List<Fix> fixes;

    @Transient
    @JoinColumn(name = "code")
    @OneToMany(targetEntity = Track.class, mappedBy = "platformCode")
    private List<Track> tracks;

    @Transient
    @JoinColumn(name = "code")
    @OneToMany(targetEntity = Target.class, mappedBy = "platformCode")
    private List<Target> targets;

    @Transient
    @JoinColumn(name = "code")
    @OneToMany(targetEntity = Engage.class, mappedBy = "platformCode")
    private List<Engage> engages;

    @Transient
    @JoinColumn(name = "code")
    @OneToMany(targetEntity = Asses.class, mappedBy = "platformCode")
    private List<Asses> asses;


}
