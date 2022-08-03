package com.soul.pooling.config;

 /**
 * @Description:
 * @Author: nemo
 * @Date: 2022/6/22
 */
public class Constants {

    public static String FORCE_HEAR= "pooling:force:init:";
    public static String OPERATE_FORCE_URL = "/csp/operate/force";

     /**
      * 心跳，用来记录前端发送的请求，内容为订单号
      */
     public static String SITUATION_HEARTBEAT = "situation:heartbeat";
     public static String SCENARIO_ORDER_INFO = "scenario:order:info";
     public static String SCENARIO_ORDER = "scenario:order";
     public static String SCENARIO_EVENT = "scenario:event";
     public static String SCENARIO_TIME = "scenario:%s:time";
     public static String SCENARIO_FORCES = "scenario:%s:forces";
     public static String SCENARIO_MOVE = "scenario:%s:move";
     public static String SCENARIO_MOVE_DECT = "scenario:%s:move-dect";

     public static String SCENARIO_RADAR = "scenario:%s:radar";
     public static String SCENARIO_ROUTE = "scenario:%s:route";
     public static String SCENARIO_AREA = "scenario:%s:area";
     public static String SCENARIO_LINK = "scenario:%s:link";
     public static String SCENARIO_INFO = "scenario:info";



 }
