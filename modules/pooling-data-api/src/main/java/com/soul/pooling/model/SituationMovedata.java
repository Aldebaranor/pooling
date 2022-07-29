package com.soul.pooling.model;

import com.egova.model.annotation.Display;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Song
 * @Date 2022/7/29 14:47
 */
@Data
public class SituationMovedata implements Serializable {

    private static final long serialVersionUID = -7494104868647116439L;

    @Display("1兵力ID")
    private String id;
    @Display("2态势时间")
    private Long time;
    private PlatformMoveData move;
    private PlatformMoveData moveDetect;

    @Override
    public String toString() {
        return String.format("%s@%s@%s@%s", id, time,
                move == null ? "" : move.toString(),
                moveDetect == null ? "" : moveDetect.toString());
    }

}
