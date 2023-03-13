package com.soul.pooling.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class NetStatusData implements Serializable {

    private static final long serialVersionUID = -6507899650065193957L;
    int mgmt_head;

    int mgmt_len;

    int mgmt_type;

    int mgmt_keep;

    int mgmt_index;

    double times;

    Short mgmt_150[];

    Short mgmt_150x150[][];
}
