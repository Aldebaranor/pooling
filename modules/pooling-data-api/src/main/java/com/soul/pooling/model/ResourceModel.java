package com.soul.pooling.model;

import com.egova.model.BaseEntity;
import com.soul.pooling.entity.enums.ResourceStatus;
import lombok.Data;

import javax.persistence.Entity;

/**
 * @Description: 资源服务$
 * @Author: nemo
 * @Date: 2022/8/23 3:27 PM
 */
@Data
@Entity
public class ResourceModel extends BaseEntity {

    private static final long serialVersionUID = 7458456359568580658L;
    private String id;

    private String name;

    private String deviceCode;

    private String type;

    private String platformCode;

    private String platformName;

    private ResourceStatus status;

    private Integer num;

    private String deviceTpye;


}
