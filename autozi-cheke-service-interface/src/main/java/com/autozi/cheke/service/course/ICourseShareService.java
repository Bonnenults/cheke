package com.autozi.cheke.service.course;

import com.autozi.cheke.course.entity.CourseShare;

import java.util.List;
import java.util.Map;

/**
 *
 *@author mingxin li
 *@data 2018/5/24
 *
 */
public interface ICourseShareService {

    int addCourseShare(CourseShare courseShare);

    int updateCourseShare(CourseShare courseShare);

    CourseShare getCourseShareById(Long id);

    List<CourseShare> getCourseShareList(Map<String, Object> filter);

    CourseShare getCourseShareByFilter(Map<String, Object> filter);

    Integer countAllShareClickNum(Long courseId);
    
    Long addCourseShareAndUpdataCountInfo(CourseShare courseShare);
}
