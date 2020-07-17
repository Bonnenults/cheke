package com.autozi.cheke.web.course.facade;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.article.entity.ArticleCountInfo;
import com.autozi.cheke.article.type.ArticleRedisConstants;
import com.autozi.cheke.article.type.ArticleStatus;
import com.autozi.cheke.course.entity.Course;
import com.autozi.cheke.course.entity.CourseCountInfo;
import com.autozi.cheke.course.entity.CourseShare;
import com.autozi.cheke.course.type.CourseRedisConstants;
import com.autozi.cheke.service.course.ICourseCountInfoService;
import com.autozi.common.cache.jedis.proxy.RedisProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 *@author mingxin li
 *@data 2018/5/28
 *
 */

@Component
public class CourseRedisFacade {

    @Autowired
    private ICourseCountInfoService courseCountInfoService;

    @Autowired
    private RedisProxy redisProxy;

    /**
     * 课程上线后，统计信息放到redis中
     * @param course
     * */
    public void auditPassCouse(Course course) {
        CourseCountInfo courseCountInfo = courseCountInfoService.getCountInfoByCourseId(course.getId());
        _saveCourseCountInfo(courseCountInfo);
        _saveCourse(course);
    }

    public void updateCouse(Course course) {
        CourseCountInfo courseCountInfo = courseCountInfoService.getCountInfoByCourseId(course.getId());
        _saveCourseCountInfo(courseCountInfo);
        _saveCourse(course);
    }

    /**
     * 下线后更新文章的状态
     * @param courseId
     */
    public void offLineRedisCourse(Long courseId) {
        Course course = _getCourse(courseId);
        if(course != null) {
            course.setStatus(ArticleStatus.OFFLINE.getType());
            course.setOfflineTime(new Date());
            _saveCourse(course);
        }
    }

    /**
     * 重新上线后更新文章的状态
     * @param courseId
     */
    public void onLineRedisCourse(Long courseId) {
        Course course = _getCourse(courseId);
        if(course != null) {
            course.setStatus(ArticleStatus.PUBLISH.getType());
            course.setPublishTime(new Date());
            _saveCourse(course);
        }
    }

    /**
     * 普通分享后，把分享信息放到redis中
     */
    public void afterNormalShare(CourseShare courseShare) {
        _saveCourseShare(courseShare);
    }

    /**
     * 统计点赞、点Low、留言次数、分享到朋友圈、微信好友、QQ空间、QQ好友、微博
     * @param courseId
     */
    public void countCourseInfo(Integer type,Long courseId) {
        CourseCountInfo info = _getCourseCountInfo(courseId);

        switch(type.intValue()) {
            case 1:
                info.setWxFriendsCircle(info.getWxFriendsCircle() + 1);
                _saveCourseCountInfo(info);
                break;
            case 2:
                info.setWxFriends(info.getWxFriends() + 1);
                _saveCourseCountInfo(info);
                break;
            case 3:
                info.setQqZone(info.getQqZone() + 1);
                _saveCourseCountInfo(info);
                break;
            case 4:
                info.setQqFriends(info.getQqFriends() + 1);
                _saveCourseCountInfo(info);
                break;
            case 5:
                info.setSina(info.getSina() + 1);
                _saveCourseCountInfo(info);
                break;
            case 10:
                info.setLeaveWords(info.getLeaveWords() + 1);
                _saveCourseCountInfo(info);
                break;
            case 11:
                //是否应该增加判断，若该用户已点low，则点low数-1
                info.setLikes(info.getLikes() + 1);
                _saveCourseCountInfo(info);
                break;
            case 12:
                //是否应该增加判断，若该用户已点赞，则点赞数-1
                info.setDislikes(info.getDislikes() + 1);
                _saveCourseCountInfo(info);
                break;
            default:
                break;
        }

    }

    /**
     * 统计课程的学习人数(+1)
     * @param courseId
     */
    public void countAddStuNum(Long courseId) {
        CourseCountInfo info = _getCourseCountInfo(courseId);
        info.setStuNum(info.getStuNum() + 1);
        _saveCourseCountInfo(info);
    }

    /**
     * 统计课程的结业人数
     * @param courseId
     */
    public void countCompletedNum(Long courseId) {
        CourseCountInfo info = _getCourseCountInfo(courseId);
        info.setCompletedNum(info.getCompletedNum() + 1);
        info.setStuNum(info.getStuNum() - 1);
        _saveCourseCountInfo(info);
    }

    /**
     * 统计文章的浏览量（每进入一次页面，算是一次访问量）
     * @param courseId
     */
    public void countPageView(Long courseId) {
        CourseCountInfo info = _getCourseCountInfo(courseId);
        if(info==null){
            info = courseCountInfoService.getCountInfoByCourseId(courseId);
        }

        info.setPageView(info.getPageView() + 1);
        _saveCourseCountInfo(info);

    }

    /**
     * 统计文章的分享量（每转发一次，算是一次分享）
     * @param courseId
     */
    public void countShareAmount(Long courseId) {
        CourseCountInfo info = _getCourseCountInfo(courseId);
        info.setShareAmount(info.getShareAmount() + 1);
        info.setTwitter(info.getTwitter() + 1);
        _saveCourseCountInfo(info);
    }


    /**
     * 统计用户，普通的分享点击次数
     * @param userId
     * @param courseId
     */
    public void countCourseShare(Long userId, Long courseId,Integer type) {
        CourseShare share = _getCourseShare(userId,courseId,type);
        CourseCountInfo countInfo = _getCourseCountInfo(courseId);
        if(share != null) {
            share.setClickNum(share.getClickNum() + 1);
            _saveCourseShare(share);
        }
        countInfo.setPageView(countInfo.getPageView() + 1);
        _saveCourseCountInfo(countInfo);
    }


    /**
     * 获取缓存中course
     * @param courseId
     * @return
     */
    public Course getCourse(Long courseId) {
        return _getCourse(courseId);
    }

    /**
     * 获取缓存中countinfo
     * @param courseId
     * @return
     */
    public CourseCountInfo getCountInfo(Long courseId) {
        return _getCourseCountInfo(courseId);
    }

    /**
     * 获取缓存中的CourseShare
     * @param userId
     * @param courseId
     * @return
     */
    public CourseShare getShare(Long userId, Long courseId,Integer type) {
        return _getCourseShare(userId,courseId,type);
    }


    //==================================================================================================================

    private CourseCountInfo _getCourseCountInfo(Long courseId) {
        return  redisProxy.getObjectClsValue(CourseRedisConstants.COURSE_COUNT_INFO + courseId);
    }

    private Course _getCourse(Long courseId) {
        return redisProxy.getObjectClsValue(CourseRedisConstants.COURSE_COUNT_COURSE + courseId);
    }

    private CourseShare _getCourseShare(Long userId, Long courseId, Integer type) {
        return redisProxy.getObjectClsValue(CourseRedisConstants.COURSE_SHARE + userId +":"+ courseId + ":" + type);
    }


    private void _saveCourse(Course course){
        redisProxy.saveObjValue(CourseRedisConstants.COURSE_COUNT_COURSE + course.getId(),course);
    }

    private void _saveCourseCountInfo(CourseCountInfo courseCountInfo) {
        redisProxy.saveObjValue(CourseRedisConstants.COURSE_COUNT_INFO + courseCountInfo.getCourseId(),courseCountInfo);

        //添加到任务
        if(redisProxy.getObjectClsValue(CourseRedisConstants.COURSE_TASK_COUNT_INFO) == null) {
            Set<String> taskSet = new HashSet<>();
            taskSet.add(CourseRedisConstants.COURSE_COUNT_INFO + courseCountInfo.getCourseId());
            redisProxy.saveObjValue(CourseRedisConstants.COURSE_TASK_COUNT_INFO,taskSet);
        }else {
            Set<String> taskSet = redisProxy.getObjectClsValue(CourseRedisConstants.COURSE_TASK_COUNT_INFO);
            taskSet.add(CourseRedisConstants.COURSE_COUNT_INFO + courseCountInfo.getCourseId());
            redisProxy.saveObjValue(CourseRedisConstants.COURSE_TASK_COUNT_INFO,taskSet);
        }

    }

    private void _saveCourseShare(CourseShare courseShare) {
        redisProxy.saveObjValue(CourseRedisConstants.COURSE_SHARE + courseShare.getUserId() +":"+courseShare.getCourseId() +":"+courseShare.getType(),courseShare);
        //添加到任务
        if(redisProxy.getObjectClsValue(CourseRedisConstants.COURSE_TASK_SHARE) == null) {
            Set<String> taskSet = new HashSet<>();
            taskSet.add(CourseRedisConstants.COURSE_SHARE + courseShare.getUserId() +":"+courseShare.getCourseId() +":"+courseShare.getType());
            redisProxy.saveObjValue(CourseRedisConstants.COURSE_TASK_SHARE,taskSet);
        }else {
            Set<String> taskSet = redisProxy.getObjectClsValue(CourseRedisConstants.COURSE_TASK_SHARE);
            taskSet.add(CourseRedisConstants.COURSE_SHARE + courseShare.getUserId() +":"+courseShare.getCourseId() +":"+courseShare.getType());
            redisProxy.saveObjValue(CourseRedisConstants.COURSE_TASK_SHARE,taskSet);
        }
    }



}
