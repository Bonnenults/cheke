package com.autozi.cheke.course.mapper;

import com.autozi.cheke.course.entity.CourseCountInfo;
import com.autozi.common.dal.mapper.BaseMapper;

/**
 * Created by mingxin.li on 2018/5/21 10:32.
 */
public interface CourseCountInfoMapper extends BaseMapper<CourseCountInfo>{
    Integer updateNul(CourseCountInfo courseCountInfo);
}
