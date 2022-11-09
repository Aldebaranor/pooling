package com.soul.pooling.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Song
 * @Date 2022/11/8 16:20
 */
@Data
public class OnOffLineData implements Serializable {

    private static final long serialVersionUID = -77994155233027500L;
    private Integer sign;

    private String mainNode;

    private List<String> Nodes;
}
