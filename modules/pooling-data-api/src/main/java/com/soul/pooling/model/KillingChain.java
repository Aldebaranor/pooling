package com.soul.pooling.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 杀伤链实体$
 * @Author: nemo
 * @Date: 2022/8/24 9:54 AM
 */
@Data
public class KillingChain implements Serializable {

    private static final long serialVersionUID = 2553371875733781000L;

    private String targetId;

    private List<ResourceModel> find;

    private List<ResourceModel> fix;

    private List<ResourceModel> track;

    private List<ResourceModel> target;

    private List<ResourceModel> engage;

    private List<ResourceModel> asses;




}
