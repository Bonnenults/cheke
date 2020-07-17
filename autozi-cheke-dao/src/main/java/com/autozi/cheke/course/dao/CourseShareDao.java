package com.autozi.cheke.course.dao;

import com.autozi.cheke.course.entity.CourseShare;
import com.autozi.cheke.course.mapper.CourseShareMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import org.springframework.stereotype.Component;
/**
 *
 *@author mingxin li
 *@data 2018/5/24
 *
 */
@Component
public class CourseShareDao extends MyBatisDao<CourseShare> {
    public Integer countAllShareClickNum(Long courseId) {
        return getMapper(CourseShareMapper.class).countAllShareClickNum(courseId);
    }
}
