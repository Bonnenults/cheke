package com.autozi.cheke.mobile.facade;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.article.entity.ArticleCountInfo;
import com.autozi.cheke.article.type.ArticleRedisConstants;
import com.autozi.cheke.article.type.ArticleTypeEnum;
import com.autozi.cheke.course.entity.Course;
import com.autozi.cheke.course.entity.CourseCountInfo;
import com.autozi.cheke.course.entity.CourseUserRelation;
import com.autozi.cheke.course.type.CourseStuStatus;
import com.autozi.cheke.service.article.IArticleCountInfoService;
import com.autozi.cheke.service.article.IArticleService;
import com.autozi.cheke.service.course.ICourseService;
import com.autozi.cheke.service.course.ICourseUserRelationService;
import com.autozi.cheke.service.user.IChekeCollectionService;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.exception.B2bException;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.util2.CommonUtils;
import com.autozi.common.utils.util2.DateUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mingxin.li on 2018/5/18 18:02.
 */

@Component
public class CourseApiFacade {

    @Autowired
    private ICourseService courseService;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ICourseUserRelationService courseUserRelationService;

    @Autowired
    private ArticleRedisApiFacade articleRedisApiFacade ;

    @Autowired
    private IArticleCountInfoService articleCountInfoService;

    @Autowired
    private ArticleApiFacade articleApiFacade;

    @Autowired
    private CourseRedisApiFacade courseRedisApiFacade;

    @Autowired
    private IChekeCollectionService chekeCollectionService;


    public JSONArray findCourseByPage(Page<Map<String, Object>> page, Map<String, Object> filters,User user) throws Exception {
        JSONArray array = new JSONArray();
        Page<Course> _page = new Page<>();
        CommonUtils.pageConversion(page, _page);
        _page = courseService.findCoursePage(_page, filters);

        for (Course course : _page.getResult()) {
            array.add(convertCourse(course,user));
        }
        CommonUtils.pageConversion(_page, page);
        return array;
    }

    private JSONObject convertCourse(Course course,User user) {
        JSONObject data = new JSONObject();
        JSONArray imgArry = new JSONArray();
        if(course == null){
            return null;
        }
        //文章Id
        data.put("courseId", course.getId()==null?"":course.getId());
        //课程名
        data.put("courseName", course.getCourseName()==null?"":course.getCourseName());

        //课程来源
        data.put("source", course.getSource()==null?"":course.getSource());
        //课程类别
        data.put("type", course.getType()==null?"":course.getType());
        //课程类型
        data.put("typeCN", course.getType()==null?"": ArticleTypeEnum.getNameByType(course.getType()));
        //课程状态
        data.put("status", course.getStatus()==null?"":course.getStatus());
        //课程学习状态
        Integer stuStatus = getCourseStuStatus(course,user);
        data.put("stuStatus", stuStatus==null ? CourseStuStatus.WAITING.getType() : stuStatus);

        if(course.getImage()!=null && !"".equals(course.getImage())){
            imgArry.add(course.getImage());
        }
        //缩略图
        data.put("image", imgArry);

        CourseCountInfo courseCountInfo = courseRedisApiFacade.getCountInfo(course.getId());

        //课程学习人数
        data.put("stuNum", courseCountInfo==null ? 0 : courseCountInfo.getStuNum());
        //发布到推客的时间
        String CreateTimeCN = "";
        Long CreateTime = course.getCreateTime().getTime();
        Long nowTime = System.currentTimeMillis();
        boolean isToday = false;
        try {
            isToday = course.getCreateTime().after(DateUtils.getTodayBegin());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(nowTime - CreateTime<60*60*1000){//1小时以内
            CreateTimeCN = "刚刚";
        }else if(isToday){
            CreateTimeCN = new SimpleDateFormat("HH:mm").format(course.getCreateTime());
        }else{
            CreateTimeCN = new SimpleDateFormat("MM月dd日").format(course.getCreateTime());
        }
        data.put("CreateTime", course.getCreateTime()==null?"":new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(course.getCreateTime()));
        data.put("CreateTimeCN", CreateTimeCN);

        return data;
    }

    public JSONArray getMyPlan(Page<Map<String, Object>> page,Map<String, Object> filters) throws Exception {
        JSONArray array = new JSONArray();
        Page<CourseUserRelation> _page = new Page<>();
        CommonUtils.pageConversion(page, _page);
        _page = courseUserRelationService.findCoursePage(_page,filters);

        for (CourseUserRelation CourseUserRelation : _page.getResult()) {
            array.add(convertPlan(CourseUserRelation));
        }
        CommonUtils.pageConversion(_page, page);

        return array;
    }

    private JSONObject convertPlan(CourseUserRelation courseUserRelation) {
        JSONObject data = new JSONObject();
        if(courseUserRelation == null){
            return null;
        }
        Article article= articleService.getArticleById(courseUserRelation.getArticleId());

        //课程ID
        data.put("CourseId", courseUserRelation.getCourseId()==null?"":courseUserRelation.getCourseId());
        if(article!=null){
            //文章Id
            data.put("ArticleId", courseUserRelation.getArticleId()==null?"":courseUserRelation.getArticleId());
            data.put("ArticleName", article.getTitle()==null?"":article.getTitle());
            data.put("num", article.getNum()==null?"":article.getNum());
        }

        //课程状态
        data.put("status", courseUserRelation.getStatus()==null?"0":courseUserRelation.getStatus());
        //课程学习进度
        data.put("currentProgress", courseUserRelation.getCurrentProgress()==null?"":courseUserRelation.getCurrentProgress());

        if((CourseStuStatus.COMPLETE.getType()).equals(courseUserRelation.getStatus())
                ||(CourseStuStatus.RESTUDY.getType()).equals(courseUserRelation.getStatus())){
            data.put("ranking", courseUserRelation.getRanking()==null?"":courseUserRelation.getRanking());
        }

        return data;
    }

    /**
     * 获取课程详情
     * @param page
     * @param filters
     * @return
     * @throws Exception
     */
    public JSONArray findCourseListByPage(Page<Map<String, Object>> page, Map<String, Object> filters,User user) throws Exception {
        JSONArray array = new JSONArray();
        Page<Article> _page = new Page<>();
        CommonUtils.pageConversion(page, _page);
        _page = courseService.findCourseListPage(_page, filters);

        for (Article article : _page.getResult()) {
            array.add(convertCourseList(article,user));
        }
        CommonUtils.pageConversion(_page, page);
        return array;
    }

    private JSONObject convertCourseList(Article article,User user) {
        JSONObject data = new JSONObject();
        JSONArray imgArry = new JSONArray();
        if(article == null){
            return null;
        }
        //课程章节号
        data.put("num", article.getNum()==null?"":article.getNum());
        //文章Id
        data.put("articleId", article.getId()==null?"":article.getId());
        //标题
        data.put("title", article.getTitle()==null?"":article.getTitle());
        //课程来源
        data.put("source", article.getSource()==null?"":article.getSource());
        //课程类别
        data.put("type", article.getType()==null?"":article.getType());
        //课程状态
        data.put("status", article.getStatus()==null?"":article.getStatus());
        //课程学习状态
        CourseUserRelation courseUserRelation = null;
        if(user != null){
            courseUserRelation = getCouseStuInfoByUserId(user.getId(),article.getId());
        }

        if(courseUserRelation!=null){
            data.put("stuStatus", courseUserRelation.getStatus()==null?"":courseUserRelation.getStatus());
        }else{
            data.put("stuStatus", CourseStuStatus.WAITING.getType());
        }

        //课程类型
        data.put("typeCN", article.getType()==null?"": ArticleTypeEnum.getNameByType(article.getType()));

        if(article.getImage()!=null && !"".equals(article.getImage())){
            imgArry.add(article.getImage());
        }
        //缩略图
        data.put("image", imgArry);

        //发布到推客的时间
        String publishTimeCN = "";
        Long publishTime = article.getPublishTime().getTime();
        Long nowTime = System.currentTimeMillis();
        if(nowTime - publishTime<60*60*1000){//1小时以内
            publishTimeCN = "刚刚";
        }else if(nowTime - publishTime < 60*60*1000*24){
            publishTimeCN = new SimpleDateFormat("HH:mm").format(article.getPublishTime());
        }else{
            publishTimeCN = new SimpleDateFormat("MM月dd日").format(article.getPublishTime());
        }
        data.put("publishTime", article.getPublishTime()==null?"":new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(article.getPublishTime()));
        data.put("publishTimeCN", publishTimeCN);
        //发布人
        Map<String, Object> cheKeHao = articleApiFacade.getCheKeHao(article.getCreateUserId());
        data.put("chekeUserName", cheKeHao.get("partyName"));

        //增加阅读量
        ArticleCountInfo countInfo = articleRedisApiFacade.getCountInfo(article.getId());
        if(countInfo==null){
            countInfo = articleCountInfoService.getCountInfoByArticleId(article.getId());
            articleRedisApiFacade.saveArticleCountInfo(countInfo);
        }
        data.put("pageView",countInfo == null ? 0 : countInfo.getPageView());
        //已学成人数
        data.put("completedNum",countInfo == null ? 0 : countInfo.getCompletedNum());

        return data;
    }

    /**
     * 获得课程信息
     * @param courseId
     * @return
     */
    public JSONObject getCourseInfo(Long courseId,User user){
        JSONObject data = new JSONObject();
        if(courseId==null){
            throw new B2bException("CourseId为空");
        }

        Course course = courseRedisApiFacade.getCourse(courseId);

        if(course == null){
            course = courseService.getCourseById(courseId);
            courseRedisApiFacade.auditPassCouse(course);
        }

        CourseCountInfo courseCountInfo = courseRedisApiFacade.getCountInfo(course.getId());

        courseRedisApiFacade.countPageView(courseId);

        buildCourseDetail(courseId,user,course,courseCountInfo,data);

        return data;
    }

    /**
     * 获得课程学习情况
     * @param userId
     * @return
     */
    public CourseUserRelation getCouseStuInfoByUserId(Long userId,Long articleId){
        JSONObject data = new JSONObject();
        if(userId==null){
            throw new B2bException("userId为空");
        }
        Map<String, Object> filters = new HashMap<>();

        filters.put("userId", userId);
        filters.put("articleId", articleId);

        List<CourseUserRelation> list = courseUserRelationService.getCourseUserRelationByFilter(filters);

        if(list!=null && list.size()!=0){
            return list.get(0);
        }

        return null;
    }

    public JSONObject buildCourseDetail(Long courseId, User user, Course course, CourseCountInfo courseCountInfo,JSONObject data) {

        data.put("courseName", course == null ? "" : course.getCourseName());
        data.put("courseSource", course == null ? "" : course.getSource());
        data.put("courseIntro", course == null ? "" : course.getIntro());
        data.put("courseStuNum", courseCountInfo == null ? 0 : courseCountInfo.getStuNum());
        data.put("completedNum",courseCountInfo == null ? 0 : courseCountInfo.getCompletedNum());
        data.put("courseChapterNum", course == null ? "" : course.getChapterNum());
        //统计已学完的章节
        int completedChapterNum = 0;
        if(user!=null){
            completedChapterNum = courseUserRelationService.countcompletedChapterNum(user.getId(),courseId,CourseStuStatus.COMPLETE.getType());
        }

        data.put("completedChapterNum", completedChapterNum);

        //是否已收藏
        //是否收藏
        Integer isCollection = 0;
        if(user!=null){
            Boolean collectionFlag = chekeCollectionService.isCollected(courseId, user.getId());
            if(collectionFlag){
                isCollection = 1;
            }
        }
        data.put("isCollection", isCollection);
        return data;
    }

    public void updateObj(Course cours){
        courseRedisApiFacade.updateCouse(cours);
    }

    public void deleteCourse(Long coursId){
        courseRedisApiFacade.deleteCourse(coursId);
    }

    public boolean checkCourseCompletedStudy(User user, Long courseId ){
        if(user != null){
            Course course = courseRedisApiFacade.getCourse(courseId);

            if(course==null){
                course = courseService.getCourseById(courseId);
                courseRedisApiFacade.auditPassCouse(course);
            }
            int chapterNum = course.getChapterNum();
            int completedChapterNum = courseUserRelationService.countcompletedChapterNum(user.getId(),courseId,CourseStuStatus.COMPLETE.getType());
            if(completedChapterNum+1 >= chapterNum){
                return true;
            }
        }
        return false;
    }

    public int getCourseStuStatus(Course course,User user){
        List<CourseUserRelation> list = new ArrayList<>();
        Integer stuStatus = null;
        if(user != null){
            Map<String, Object> filters = new HashMap<>();
            filters.put("userId", user.getId());
            filters.put("courseId", course.getId());
            list = courseUserRelationService.getCourseUserRelationByFilter(filters);
            boolean courseCompletedFlag = checkCourseCompletedStudy(user,course.getId());
            if(list.size() > 0){
                if (courseCompletedFlag) {
                    stuStatus = CourseStuStatus.COMPLETE.getType();
                }else{
                   stuStatus = CourseStuStatus.STUDYING.getType();
                }
            }else {
                stuStatus = CourseStuStatus.WAITING.getType();
            }
        } else {
            stuStatus = CourseStuStatus.WAITING.getType();
        }
        return stuStatus;
    }
}
