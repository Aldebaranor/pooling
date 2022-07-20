package com.soul.pooling.entity;


import com.egova.model.BaseEntity;
import com.egova.model.annotation.Display;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
* @Description: 
* @Author: nemo
* @Date: 2022/6/22
*/
@Data
@Entity
@Table(name = "polling_platform")
@Display("平台")
@EqualsAndHashCode(callSuper = true)
public class Platform extends BaseEntity{

    public static final String NAME = "polling:platform";

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


    @Transient
    private List<Sensor> sensors;

    @Transient
    private List<Weapon> weapons;

}
