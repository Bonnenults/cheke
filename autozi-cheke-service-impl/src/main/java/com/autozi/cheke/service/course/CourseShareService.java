package com.autozi.cheke.service.course;


import com.autozi.cheke.article.entity.ArticleShare;
import com.autozi.cheke.course.dao.CourseShareDao;
import com.autozi.cheke.course.entity.CourseShare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 *
 *@author mingxin li
 *@data 2018/5/24
 *
 */
@Service
public class CourseShareService implements ICourseShareService{

    @Autowired
    private CourseShareDao courseShareDao;

    @Override
    public int addCourseShare(CourseShare courseShare) {
        return courseShareDao.insert(courseShare);
    }

    @Override
    public int updateCourseShare(CourseShare courseShare) {
        return courseShareDao.update(courseShare);
    }

    @Override
    public CourseShare getCourseShareById(Long id) {
        return courseShareDao.get(id);
    }

    @Override
    public List<CourseShare> getCourseShareList(Map<String, Object> filter) {
        return courseShareDao.findListForMap(filter);
    }

    @Override
    public CourseShare getCourseShareByFilter(Map<String, Object> filter) {
        CourseShare courseShare = null;
        List<CourseShare> list = courseShareDao.findListForMap(filter);
        if(list != null && list.size() > 0) {
            courseShare = list.get(0);
        }
        return courseShare;
    }

    @Override
    public Integer countAllShareClickNum(Long courseId) {
        return courseShareDao.countAllShareClickNum(courseId);
    }

	@Override
	public Long addCourseShareAndUpdataCountInfo(CourseShare courseShare) {
		courseShareDao.insert(courseShare);
        return courseShare.getId();
	}


}
