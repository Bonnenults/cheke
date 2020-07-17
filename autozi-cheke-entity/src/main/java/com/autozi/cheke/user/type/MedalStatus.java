package com.autozi.cheke.user.type;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by mingxin.li on 2018/06/20.
 */
public enum MedalStatus {
   ALL(-100,"全部"), WAITING(0,"待发布"),PUBLISH(1,"已上线"),OFFLINE(2,"已下线"),WAITOBTAIN(3,"待领取"),OBTAIN(4,"已领取"),ADORN(5,"佩戴中");


    public static Map<String,String> getStatusMap() {
        Map<String,String> statusMap = new TreeMap<>();
        for(MedalStatus status : MedalStatus.values()) {
            statusMap.put(status.getType().toString(),status.getName());
        }
        return statusMap;
    }


    public static String getNameByType(Integer type) {
        for(MedalStatus status : MedalStatus.values()) {
            if(type.intValue() == status.getType().intValue()) {
                return status.getName();
            }
        }
        return "";
    }


    private Integer type;
    private String name;

    MedalStatus(Integer type, String name) {
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
