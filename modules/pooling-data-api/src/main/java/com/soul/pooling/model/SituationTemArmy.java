package com.soul.pooling.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Song
 * @Date 2022/8/3 10:28
 */
@Data
public class SituationTemArmy implements Serializable {

    private static final long serialVersionUID = -644948658707379697L;
    private String id;
    private String name;
    private String entity;
    private Integer iff;
    private String code;

}
