package com.soul.pooling.condition;

import com.egova.model.annotation.Display;
import com.flagwind.persistent.annotation.Condition;
import com.flagwind.persistent.annotation.ConditionOperator;
import com.flagwind.persistent.model.ClauseOperator;
import lombok.Data;

/**
 * @author Administrator
 * @date 2022/7/21 11:24
 */
@Data
@Condition
@Display("标准查询条件")
public class ResourceCondition {

    private String id;

    private String platformCode;

    private String name;

    private String type;

}
