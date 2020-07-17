package com.autozi.cheke.service.course;

import com.autozi.cheke.course.entity.CourseCountInfo;
import com.autozi.cheke.course.type.CourseClickType;

/**
 *
 *@author mingxin li
 *@data 2018/5/21
 *
 */
public interface ICourseCountInfoService {

    int addCountInfo(CourseCountInfo courseCountInfo);

    CourseCountInfo getCountInfoByCourseId(Long courseId);

    int updateCountInfo(CourseCountInfo courseCountInfo);

    int updateCountInfoNul(CourseCountInfo courseCountInfo);

    int updateNum(CourseClickType clickType);

/*    Double countTotalCostForParty(Map<String, Object> map);

    public Double countCostForParty(Map<String, Object> map);*/
    
}
