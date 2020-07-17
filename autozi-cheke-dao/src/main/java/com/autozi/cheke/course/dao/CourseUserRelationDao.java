package com.autozi.cheke.course.dao;

import com.autozi.cheke.course.entity.CourseUserRelation;
import com.autozi.cheke.course.mapper.CourseUserRelationMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import org.springframework.stereotype.Component;

/**
 * Created by mingxin.li on 2018/5/21 10:33.
 */
@Component
public class CourseUserRelationDao extends MyBatisDao<CourseUserRelation> {
    public int countcompletedChapterNum(Long userId,Long courseId,Integer status) {
        return getMapper(CourseUserRelationMapper.class).countcompletedChapterNum(userId,courseId,status);
    }
}
