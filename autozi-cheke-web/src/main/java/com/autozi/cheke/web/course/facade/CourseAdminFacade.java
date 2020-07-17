package com.autozi.cheke.web.course.facade;

import com.autozi.cheke.article.type.ArticleStatus;
import com.autozi.cheke.course.entity.Course;
import com.autozi.cheke.course.entity.CourseCountInfo;
import com.autozi.cheke.course.entity.CourseUserRelation;
import com.autozi.cheke.course.type.CourseStatus;
import com.autozi.cheke.course.type.CourseStuStatus;
import com.autozi.cheke.course.type.CourseTypeEnum;
import com.autozi.cheke.service.course.ICourseCountInfoService;
import com.autozi.cheke.service.course.ICourseService;
import com.autozi.cheke.service.course.ICourseUserRelationService;
import com.autozi.cheke.web.course.vo.CourseVo;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.o2o.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 *
 *@author mingxin li
 *@data 2018/5/21
 *
 */
@Component
public class CourseAdminFacade {

    @Autowired
    private ICourseService courseService;

    @Autowired
    private ICourseUserRelationService courseUserRelationService;

    @Autowired
    private ICourseCountInfoService courseCountInfoService;

    @Autowired
    private CourseRedisFacade courseRedisFacade;


    public Page<CourseVo> findCoursePage(Page<Course> page, Map<String,Object> filter) throws Exception{
        return convertCourseVo(courseService.findCoursePage(page,filter));
    }

    /**
     * 新增课程
     * @param course
     * @return
     */
    public int addCourse(Course course) {
        return courseService.addCourse(course);
    }

    /**
     * 更新课程
     * @param course
     * @return
     */
    public int updateCourse(Course course) {
        return courseService.updateCourse(course);
    }


    /**
     * 拼装course
     * @param CoursePage
     * @return
     */
    private Page<CourseVo> convertCourseVo(Page<Course> CoursePage) {
        Page<CourseVo> voPage = new Page<>();
        List<CourseVo> voList = new ArrayList<>();

        for(Course course : CoursePage.getResult()) {
            CourseVo vo = new CourseVo();
            BeanUtils.copyProperties(course,vo);

            //查询浏览量、分享量
            CourseCountInfo countInfo = courseCountInfoService.getCountInfoByCourseId(vo.getId());

            if(countInfo != null) {
                vo.setStuNum(countInfo.getStuNum());
                vo.setCompletedNum(countInfo.getCompletedNum());
            }
            vo.setTypeName(CourseTypeEnum.getNameByType(course.getType()));
            vo.setStatusName(CourseStatus.getNameByType(course.getStatus()));
            voList.add(vo);
        }

        PageUtil.convertPage(CoursePage,voPage,voList);
        return voPage;
    }

    /**
     * 下线课程,同时将用户与该课程相关的学习计划下线
     * @param courseId
     * @return
     */
    public int offLineCourse(Long courseId) {
        Course course = getByCourseId(courseId);
        course.setStatus(CourseStatus.OFFLINE.getType());
        course.setOfflineTime(new Date());
        course.setUpdateTime(new Date());

        courseRedisFacade.offLineRedisCourse(courseId);

        return courseService.updateCourse(course);
    }

    /**
     * 上线课程
     * @param courseId
     * @return
     */
    public int onlineCourse(Long courseId) {
        Course course = getByCourseId(courseId);
        course.setStatus(CourseStatus.PUBLISH.getType());
        course.setPublishTime(new Date());
        course.setUpdateTime(new Date());

        courseRedisFacade.onLineRedisCourse(courseId);

        return courseService.updateCourse(course);
    }

    public Course getByCourseId(Long id) {
        return courseService.getCourseById(id);
    }



}
