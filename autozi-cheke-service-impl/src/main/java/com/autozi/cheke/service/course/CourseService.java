package com.autozi.cheke.service.course;

import com.autozi.cheke.article.dao.ArticleDao;
import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.course.dao.CourseDao;
import com.autozi.cheke.course.dao.CourseUserRelationDao;
import com.autozi.cheke.course.entity.Course;
import com.autozi.cheke.course.entity.CourseCountInfo;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by mingxin.li on 2018/5/21 11:30.
 */

@Service
public class CourseService implements ICourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CourseUserRelationDao courseUserRelationDao;

    @Autowired
    private  CourseCountInfoService courseCountInfoService;


    @Override
    public Page<Course> findCoursePage(Page<Course> page, Map<String, Object> filter) throws Exception {
        return courseDao.findPageForMap(page,filter);
    }

    @Override
    public Page<Article> findCourseListPage(Page<Article> page, Map<String, Object> filter) throws Exception {
        return articleDao.findPageForMap(page,filter);
    }

    @Override
    public Course getCourseById(Long id) {
        return courseDao.get(id);
    }

    @Override
    public int addCourse(Course course){

        int num = courseDao.insert(course);
        Long courseId = course.getId();
        //初始化统计表
        initCountInfo(courseId);

        return num;

    }

    @Override
    public int updateCourse(Course course) {
        return courseDao.update(course);
    }


    /**
     * 初始化统计表
     *
     * @param courseId
     * @return
     */
    private int initCountInfo(Long courseId) {
        CourseCountInfo cc = new CourseCountInfo();
        cc.setCourseId(courseId);

        cc.setTwitter(0);
        cc.setLeaveWords(0);
        cc.setLikes(0);
        cc.setDislikes(0);
        cc.setStuNum(0);
        cc.setCompletedNum(0);
        cc.setPageView(0);
        cc.setShareAmount(0);
        cc.setWxFriends(0);
        cc.setWxFriendsCircle(0);
        cc.setQqFriends(0);
        cc.setQqZone(0);
        cc.setSina(0);

        return courseCountInfoService.addCountInfo(cc);
    }

}
