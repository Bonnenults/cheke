package com.autozi.cheke.course.mapper;

import com.autozi.cheke.course.entity.CourseUserRelation;
import com.autozi.common.dal.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by mingxin.li on 2018/5/21 13:46.
 */
public interface CourseUserRelationMapper extends BaseMapper<CourseUserRelation> {
    int countcompletedChapterNum( @Param("userId") Long userId, @Param("courseId") Long courseId,@Param("status")Integer status);
}
