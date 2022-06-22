package com.soul.pooling.condition;

import com.egova.model.annotation.Display;
import com.flagwind.persistent.annotation.Condition;
import com.flagwind.persistent.annotation.ConditionOperator;
import com.flagwind.persistent.model.ClauseOperator;
import lombok.Data;


/**
* @Description: 
* @Author: nemo
* @Date: 2022/6/22
*/
@Data
@Condition
@Display("地图设置")
public class MapPointCondition {
    @Display("id")
    @ConditionOperator
            (name = "id", operator = ClauseOperator.Equal)
    private String id;

    @Display("是否废弃")
    @ConditionOperator(name = "disabled", operator = ClauseOperator.Equal)
    private Boolean disabled;
}
