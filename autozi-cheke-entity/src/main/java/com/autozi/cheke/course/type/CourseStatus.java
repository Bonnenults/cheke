package com.autozi.cheke.course.type;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wang-lei on 2017/11/24.
 */
public enum CourseStatus {
   ALL(-100,"全部"), WAITING(0,"待发布"),PUBLISH(1,"已上线"),OFFLINE(2,"已下线"),DELETE(-1,"已删除");


    public static Map<String,String> getStatusMap() {
        Map<String,String> statusMap = new TreeMap<>();
        for(CourseStatus status : CourseStatus.values()) {
            statusMap.put(status.getType().toString(),status.getName());
        }
        return statusMap;
    }


    public static String getNameByType(Integer type) {
        for(CourseStatus status : CourseStatus.values()) {
            if(type.intValue() == status.getType().intValue()) {
                return status.getName();
            }
        }
        return "";
    }


    private Integer type;
    private String name;

    CourseStatus(Integer type, String name) {
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
