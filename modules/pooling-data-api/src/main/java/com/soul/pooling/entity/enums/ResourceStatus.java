package com.soul.pooling.entity.enums;

import com.egova.associative.Associative;
import com.egova.associative.CodeTypeProvider;
import com.egova.model.PropertyDescriptor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @Description: 仿真引擎发出的事件枚举$
 * @Author: nemo
 * @Date: 2022/5/24 2:24 PM
 */
@Getter
@RequiredArgsConstructor
@Associative(
        name = "_${name}",
        providerClass = CodeTypeProvider.class
)
public enum ResourceStatus implements PropertyDescriptor {

    AVAILABLE("0", "可用"),
    SATURATION("1", "饱和"),
    FAULT("2", "故障");

    private final String value;
    private final String text;


}

