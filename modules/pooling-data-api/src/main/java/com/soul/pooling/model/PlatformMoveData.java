package com.soul.pooling.model;

import com.egova.model.annotation.Display;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Administrator
 * @date 2022/7/27 14:17
 */
@Data
public class PlatformMoveData implements Serializable {

    private static final long serialVersionUID = -4830039640212206808L;

    @Display("3经度")
    private Double lon;
    @Display("4纬度")
    private Double lat;
    @Display("5高度")
    private Double alt;
    @Display("6航向角")
    private Double heading;
    @Display("7横滚角")
    private Double roll;
    @Display("8俯仰角")
    private Double pitch;
    @Display("9速度")
    private Double speed;

    @Display("10生命值")
    private Double life;

    @Display("11更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp updateTime;

    @Override
    public String toString() {
        return String.format("%s_%s_%s_%s_%s_%s_%s_%s_%", lon, lat, alt, heading, roll, pitch, speed, life, updateTime);
    }

}
