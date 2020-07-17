package com.autozi.cheke.trigger.course;

import com.autozi.cheke.course.entity.CourseCountInfo;
import com.autozi.cheke.course.entity.CourseShare;
import com.autozi.cheke.service.course.ICourseCountInfoService;
import com.autozi.cheke.service.course.ICourseShareService;
import com.autozi.cheke.trigger.facade.CourseRedisTaskFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by mingxin.li on 2018/5/24 16:57.
 */
public class CourseCountInfoTrigger {

    @Autowired
    private CourseRedisTaskFacade courseRedisTaskFacade;

    @Autowired
    private ICourseCountInfoService courseCountInfoService;

    @Autowired
    private ICourseShareService courseShareService;


    public void syncCourseCountInfoData() {
        //更新count_info，此处多线程处理有问题
        Set<String> countSet = courseRedisTaskFacade.getTaskCourseCountInfo();
        if(countSet != null) {
            for(Iterator<String> it = countSet.iterator(); it.hasNext();) {
                CourseCountInfo courseCountInfo = courseRedisTaskFacade.getTaskValue(it.next());
                int i = courseCountInfoService.updateCountInfo(courseCountInfo);

                if(i >= 1) {
                    it.remove();
                    courseRedisTaskFacade.saveTaskCourseCountInfo(countSet);
                }
            }
        }
    }

    public void syncCourseShareData() {
        //更新share
        Set<String> shareSet = courseRedisTaskFacade.getTaskCourseShare();
        if(shareSet != null) {
            for(Iterator<String> it = shareSet.iterator(); it.hasNext();) {
                CourseShare share = courseRedisTaskFacade.getTaskValue(it.next());
                int i = courseShareService.updateCourseShare(share);

                if(i >= 1) {
                    it.remove();
                    courseRedisTaskFacade.saveTaskCourseShare(shareSet);
                }

            }
        }

    }
}
