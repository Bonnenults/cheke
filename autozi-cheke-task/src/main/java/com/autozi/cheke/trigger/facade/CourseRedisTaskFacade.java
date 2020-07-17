package com.autozi.cheke.trigger.facade;

import com.autozi.cheke.course.type.CourseRedisConstants;
import com.autozi.common.cache.jedis.proxy.RedisProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by mingxin.li on 2018/5/24 17:06.
 */
@Component
public class CourseRedisTaskFacade {

    @Autowired
    private RedisProxy redisProxy;

    /**
     * 获取统计任务countinfo的key
     * @return
     */
    public Set<String> getTaskCourseCountInfo() {
        return redisProxy.getObjectClsValue(CourseRedisConstants.COURSE_TASK_COUNT_INFO);
    }


    /**
     * 获取
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getTaskValue(String key) {
        T object = (T) redisProxy.getObjectClsValue(key);
        return object;
    }


    /**
     * 同步统计数据完成后，保存CourseCountInfo数据到redis
     * @return
     */
    public void saveTaskCourseCountInfo(Set<String> taskKeys) {
        redisProxy.saveObjValue(CourseRedisConstants.COURSE_TASK_COUNT_INFO,taskKeys);
    }

    /**
     * 获取统计任务share的key
     * @return
     */
    public Set<String> getTaskCourseShare() {
        return redisProxy.getObjectClsValue(CourseRedisConstants.COURSE_TASK_SHARE);
    }

    /**
     * 同步分享数据完成后，保存ArticleShare数据到redis
     * @return
     */
    public void saveTaskCourseShare(Set<String> taskKeys) {
        redisProxy.saveObjValue(CourseRedisConstants.COURSE_TASK_SHARE,taskKeys);
    }



}
