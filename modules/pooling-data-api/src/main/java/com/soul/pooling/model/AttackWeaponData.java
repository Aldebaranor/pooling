package com.soul.pooling.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 参与杀伤链的武器$
 * @Author: nemo
 * @Date: 2022/5/23 6:25 PM
 */
@Data
public class AttackWeaponData implements Serializable {

    private static final long serialVersionUID = -3124229567647844568L;
    //武器装备名称
    private String name;
    //武器装备弹药发射数量
    private Integer num;


}
