package com.autozi.cheke.user.type;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by mingxin.li on 2018/06/30.
 */
public enum MedalRuleStatus {
   ALL(-100,"全部"), WAITING(0,"待发布"),PUBLISH(1,"已上线"),OFFLINE(2,"已下线");


    public static Map<String,String> getStatusMap() {
        Map<String,String> statusMap = new TreeMap<>();
        for(MedalRuleStatus status : MedalRuleStatus.values()) {
            statusMap.put(status.getType().toString(),status.getName());
        }
        return statusMap;
    }


    public static String getNameByType(Integer type) {
        for(MedalRuleStatus status : MedalRuleStatus.values()) {
            if(type.intValue() == status.getType().intValue()) {
                return status.getName();
            }
        }
        return "";
    }


    private Integer type;
    private String name;

    MedalRuleStatus(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
