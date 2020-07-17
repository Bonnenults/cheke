package com.autozi.cheke.service.course;

import com.autozi.cheke.course.dao.CourseUserRelationDao;
import com.autozi.cheke.course.entity.CourseUserRelation;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by mingxin.li on 2018/5/21 14:14.
 */

@Service
public class CourseUserRelationService implements ICourseUserRelationService {

    @Autowired
    private CourseUserRelationDao courseUserRelationDao;


    @Override
    public Page<CourseUserRelation> findCoursePage(Page<CourseUserRelation> page, Map<String, Object> filter) throws Exception {
        return courseUserRelationDao.findPageForMap(page,filter);
    }

    @Override
    public List<CourseUserRelation> getCourseUserRelationByFilter(Map<String, Object> filter) {
        return courseUserRelationDao.findListForMap(filter);
    }

    @Override
    public CourseUserRelation findCourseUserRelationObj(Map<String, Object> filter){
        List<CourseUserRelation> list = courseUserRelationDao.findListForMap(filter);
        if(list!=null && list.size()!=0){
            CourseUserRelation courseUserRelation = list.get(0);
            return courseUserRelation;
        }
        return null;
    }

    @Override
    public int addCourseUserRelation(CourseUserRelation courseUserRelation){
        return courseUserRelationDao.insert(courseUserRelation);
    }

    @Override
    public void updataCourseUserRelation(CourseUserRelation courseUserRelation){
        courseUserRelation.setUpdateTime(new Date());
        courseUserRelationDao.update(courseUserRelation);
    }

    @Override
    public int countcompletedChapterNum(Long userId,Long courseId,Integer status)  {
        return courseUserRelationDao.countcompletedChapterNum(userId,courseId,status);
    }

}
