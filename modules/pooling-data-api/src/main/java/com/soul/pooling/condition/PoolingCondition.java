package com.soul.pooling.condition;

import com.egova.model.annotation.Display;
import com.flagwind.persistent.annotation.Condition;
import com.flagwind.persistent.annotation.ConditionOperator;
import com.flagwind.persistent.model.ClauseOperator;
import lombok.Data;

/**
 * @author Administrator
 * @date 2022/7/21 11:09
 */
@Data
@Condition
@Display("平台状态")
public class PoolingCondition {
    @Display("platformId")
    @ConditionOperator(name = "platformId", operator = ClauseOperator.Equal)
    private String platformId;

    @Display("编号")
    @ConditionOperator(name = "code", operator = ClauseOperator.Equal)
    private String code;

    @Display("名称")
    @ConditionOperator(name = "name", operator = ClauseOperator.Like)
    private String name;

    @Display("类型")
    @ConditionOperator(name = "type", operator = ClauseOperator.Equal)
    private String type;

    @Display("状态")
    @ConditionOperator(name = "initStatus", operator = ClauseOperator.Equal)
    private Boolean initStatus;

    @Display("kind")
    @ConditionOperator(name = "kind", operator = ClauseOperator.Equal)
    private String kind;

    @Display("状态")
    @ConditionOperator(name = "activeStatus", operator = ClauseOperator.Equal)
    private Boolean activeStatus;


}
