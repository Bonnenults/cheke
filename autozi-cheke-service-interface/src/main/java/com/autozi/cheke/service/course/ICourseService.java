package com.autozi.cheke.service.course;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.course.entity.Course;
import com.autozi.common.core.page.Page;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/18.
 */

public interface ICourseService {

    Page<Article> findCourseListPage(Page<Article> page, Map<String, Object> filter) throws Exception;

    Course getCourseById(Long id);
    int addCourse(Course course);
    int updateCourse(Course course);
    Page<Course> findCoursePage(Page<Course> page, Map<String,Object> filter) throws Exception;

}
