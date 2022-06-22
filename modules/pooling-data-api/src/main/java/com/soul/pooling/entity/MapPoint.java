package com.soul.pooling.entity;


import com.egova.model.BaseEntity;
import com.egova.model.annotation.Display;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;


/**
* @Description: 
* @Author: nemo
* @Date: 2022/6/22
*/
@Data
@Entity
@Table(name = "fregata_map_point")
@Display("地图设置")
@EqualsAndHashCode(callSuper = true)
public class MapPoint extends BaseEntity{

    public static final String NAME = "fregata:map-point";

    @Id
    @Display("主键")
    @Column(name = "id")
    private String id;

    @Display("页面编码")
    @Column(name = "pageCode")
    private String pageCode;

    @Display("名称")
    @Column(name = "name")
    private String name;

    @Display("经度")
    @Column(name = "lon")
    private String lon;

    @Display("纬度")
    @Column(name = "lat")
    private String lat;

    @Display("海拔")
    @Column(name = "alt")
    private String alt;

    @Display("航向角")
    @Column(name = "heading")
    private String heading;

    @Display("横滚角")
    @Column(name = "roll")
    private String roll;

    @Display("俯仰角")
    @Column(name = "pitch")
    private String pitch;

    @Display("上升角")
    @Column(name = "zoom")
    private String zoom;

    @Display("是否废弃")
    @Column(name = "disabled")
    private Boolean disabled;

    @Display("默认点")
    @Column(name = "beDefault")
    private Boolean beDefault;


    @Display("创建时间")
    @Column(name = "createTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;


    @Display("修改时间")
    @Column(name = "modifyTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp modifyTime;

}
