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
    ATTACK(20, "打击任务");

    private int value;

    private String text;

    CommandType(int value, String text) {

        this.value = value;

        this.text = text;

    }


}
