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
@Display("平台")
public class PlatformCondition {
    @Display("id")
    @ConditionOperator(name = "id", operator = ClauseOperator.Equal)
    private String id;

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
    @ConditionOperator(name = "status", operator = ClauseOperator.Equal)
    private String status;

    @Display("是否实装")
    @ConditionOperator(name = "beRealEquipment", operator = ClauseOperator.Equal)
    private Boolean beRealEquipment;

    @Display("是否扫雷")
    @ConditionOperator(name = "beMineSweep", operator = ClauseOperator.Equal)
    private Boolean beMineSweep;

}
