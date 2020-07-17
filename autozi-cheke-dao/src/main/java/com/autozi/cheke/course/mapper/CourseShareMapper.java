package com.autozi.cheke.course.mapper;

import com.autozi.cheke.course.entity.CourseShare;
import com.autozi.common.dal.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 *@author mingxin li
 *@data 2018/5/24
 *
 */
public interface CourseShareMapper extends BaseMapper<CourseShare> {
    Integer countAllShareClickNum(@Param("courseId") Long courseId);
}
