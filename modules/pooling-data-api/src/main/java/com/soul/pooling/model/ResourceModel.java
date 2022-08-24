package com.soul.pooling.model;

import com.soul.pooling.entity.enums.ResourceStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 资源服务$
 * @Author: nemo
 * @Date: 2022/8/23 3:27 PM
 */
@Data
public class ResourceModel implements Serializable {

    private static final long serialVersionUID = -3457635165508631282L;

    private String id;

    private String name;

    private String deviceCode;

    private String type;

    private String platformCode;

    private String platformName;

    private ResourceStatus status;


}
