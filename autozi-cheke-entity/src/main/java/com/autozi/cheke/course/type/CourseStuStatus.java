package com.autozi.cheke.course.type;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by mingxin.li on 2018/5/21 10:57.
 */
public enum CourseStuStatus {
    ALL(-100,"全部"), WAITING(0,"未开始"),STUDYING(1,"学习中"),COMPLETE(2,"已结业"),RESTUDY(3,"再次学习"),OFFLINE(-1,"已下线");

    public static Map<String,String> getStuStatusMap() {
        Map<String,String> stuStatusMap = new TreeMap<>();
        for(CourseStuStatus status : CourseStuStatus.values()) {
            stuStatusMap.put(status.getType().toString(),status.getName());
        }
        return stuStatusMap;
    }


    public static String getNameByType(Integer type) {
        for(CourseStuStatus status : CourseStuStatus.values()) {
            if(type.intValue() == status.getType().intValue()) {
                return status.getName();
            }
        }
        return "";
    }


    private Integer type;
    private String name;

    CourseStuStatus(Integer type, String name) {
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
