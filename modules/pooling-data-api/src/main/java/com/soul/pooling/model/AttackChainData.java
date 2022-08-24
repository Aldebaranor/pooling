package com.soul.pooling.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 杀伤链数据$
 * @Author: nemo
 * @Date: 2022/5/23 6:25 PM
 */
@Data
public class AttackChainData implements Serializable {


    private static final long serialVersionUID = -5272452824579991465L;
    //执行打击的兵力ID
    private String instId;
    //参与杀伤链的传感器
    private List<String> sensor;
    //参与杀伤链的武器
    private List<AttackWeaponData> weapon;


}
