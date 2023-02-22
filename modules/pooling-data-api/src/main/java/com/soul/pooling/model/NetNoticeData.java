package com.soul.pooling.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Song
 * @Date 2023/2/21 16:16
 */
@Data
public class NetNoticeData implements Serializable {
    private static final long serialVersionUID = 4754957246013791534L;
    //    0上线，1下线
    private Integer sign;
    
    private List<NetPosition> nodesInfo;
}
