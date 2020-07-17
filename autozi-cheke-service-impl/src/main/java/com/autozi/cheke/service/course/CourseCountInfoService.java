package com.autozi.cheke.service.course;

import com.autozi.cheke.course.dao.CourseCountInfoDao;
import com.autozi.cheke.course.entity.CourseCountInfo;
import com.autozi.cheke.course.type.CourseClickType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mingxin.li on 2018/5/22 16:39.
 */
@Service
public class CourseCountInfoService implements ICourseCountInfoService {

    @Autowired
    private CourseCountInfoDao courseCountInfoDao;

    @Override
    public int addCountInfo(CourseCountInfo courseCountInfo) {
        return courseCountInfoDao.insert(courseCountInfo);
    }

    @Override
    public CourseCountInfo getCountInfoByCourseId(Long courseId) {
        Map<String,Object> filter = new HashMap<>();
        filter.put("courseId",courseId);
        CourseCountInfo courseCountInfo = null;
        List<CourseCountInfo> list = courseCountInfoDao.findListForMap(filter);
        if(list != null && list.size() > 0) {
            courseCountInfo = list.get(0);
            return courseCountInfo;
        }
        return null;
    }

    @Override
    public int updateCountInfo(CourseCountInfo courseCountInfo) {
        return courseCountInfoDao.update(courseCountInfo);
    }

    @Override
    public int updateCountInfoNul(CourseCountInfo courseCountInfo) {
        return courseCountInfoDao.update(courseCountInfo);
    }

    @Override
    public int updateNum(CourseClickType clickType) {
        return 0;
    }

/*    @Override
    public Double countTotalCostForParty(Map<String, Object> map) {
        return courseCountInfoDao.;
    }*/

/*    @Override
    public Double countCostForParty(Map<String, Object> map) {
        return null;
    }*/
}
