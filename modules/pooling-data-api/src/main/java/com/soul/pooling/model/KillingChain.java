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

    private List<ResourceModel> find;

    private List<ResourceModel> fix;

    private List<ResourceModel> Track;

    private List<ResourceModel> Target;

    private List<ResourceModel> Engage;

    private List<ResourceModel> Asses;




}
