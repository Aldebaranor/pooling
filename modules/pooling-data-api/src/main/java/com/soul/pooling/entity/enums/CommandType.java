package com.soul.pooling.entity.enums;

import lombok.Getter;

/**
 * @Description: 仿真引擎发出的事件枚举$
 * @Author: nemo
 * @Date: 2022/5/24 2:24 PM
 */
@Getter
public enum CommandType {

    SEARCH(10, "搜索任务"),
    ATTACK(20, "打击任务"),
    ATTACK_AIR(21, "对空打击任务"),
    ATTACK_SEA(22, "对海打击任务"),
    ATTACK_LAND(23, "对陆打击任务"),
    ATTACK_UNDERSEA(24, "对潜打击任务");

    private int value;

    private String text;

    CommandType(int value, String text) {

        this.value = value;

        this.text = text;

    }


}
