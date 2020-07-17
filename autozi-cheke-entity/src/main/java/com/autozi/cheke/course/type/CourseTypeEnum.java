package com.autozi.cheke.course.type;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wang-lei on 2017/11/22.
 * 资讯类别
 */
public enum CourseTypeEnum {

    ALL(0,"全部"),JDWX(1,"机电维修"),CSGZ(2,"车身构造"),GZYL(3,"构造原理"),CSNS(4,"车身内饰"),CSWG(5,"车身外观"),
    KZGN(6,"控制功能"),QCZS(7,"汽车装饰"),QCMR(8,"汽车美容"),QCBJ(8,"汽车钣金"),QCPQ(8,"汽车喷漆");


    public static Map<String,String> getTypeMap() {
        Map<String,String> map = new TreeMap<>();
        for(CourseTypeEnum type : CourseTypeEnum.values()) {
            map.put(type.getType().toString(),type.getName());
        }
        return map;
    }


    public static String getNameByType(Integer type) {
        for(CourseTypeEnum ct : CourseTypeEnum.values()) {
            if(ct.getType().intValue() == type.intValue()) {
                return ct.getName();
            }
        }
        return "";
    }

    private String name;
    private Integer type;


    CourseTypeEnum(Integer type, String name) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
