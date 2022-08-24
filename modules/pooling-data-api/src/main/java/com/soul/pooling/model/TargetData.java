package com.soul.pooling.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 杀伤链数据$
 * @Author: nemo
 * @Date: 2022/5/23 6:25 PM
 */
@Data
public class TargetData implements Serializable {


    private static final long serialVersionUID = -5367064915578563512L;
    //目标ID
    private String instId;
    //目标类型
    private String type;
    //当前目标位置
    private Point point;


}
