package com.autozi.cheke.course.dao;

import com.autozi.cheke.course.entity.CourseCountInfo;
import com.autozi.cheke.course.mapper.CourseCountInfoMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import org.springframework.stereotype.Component;

/**
 * Created by mingxin.li on 2018/5/21 10:31.
 */

@Component
public class CourseCountInfoDao extends MyBatisDao<CourseCountInfo> {
    public Integer updateNul(CourseCountInfo courseCountInfo) {
        return getMapper(CourseCountInfoMapper.class).updateNul(courseCountInfo);
    }
}
