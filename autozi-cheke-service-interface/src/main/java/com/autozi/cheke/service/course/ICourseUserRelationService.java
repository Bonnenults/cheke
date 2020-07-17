package com.autozi.cheke.service.course;

import com.autozi.cheke.course.entity.CourseUserRelation;
import com.autozi.common.core.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by mingxin.li on 2018/5/21 14:14.
 */
public interface ICourseUserRelationService {
    Page<CourseUserRelation> findCoursePage(Page<CourseUserRelation> page, Map<String, Object> filter) throws Exception;

    List<CourseUserRelation> getCourseUserRelationByFilter(Map<String, Object> filter);

    CourseUserRelation findCourseUserRelationObj(Map<String, Object> filter);

    void updataCourseUserRelation(CourseUserRelation courseUserRelation);

    int addCourseUserRelation(CourseUserRelation courseUserRelation);

    int countcompletedChapterNum(Long userId, Long courseId, Integer status);
}
