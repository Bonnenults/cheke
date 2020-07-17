package com.autozi.cheke.mobile.facade;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.article.type.ArticleChannel;
import com.autozi.cheke.course.entity.Course;
import com.autozi.cheke.course.entity.CourseCountInfo;
import com.autozi.cheke.course.entity.CourseUserRelation;
import com.autozi.cheke.course.type.CourseStuStatus;
import com.autozi.cheke.service.article.IArticleService;
import com.autozi.cheke.service.course.ICourseService;
import com.autozi.cheke.service.course.ICourseUserRelationService;
import com.autozi.cheke.service.user.IChekeCollectionService;
import com.autozi.cheke.user.entity.ChekeCollection;
import com.autozi.cheke.user.entity.User;
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
 * @Auther: Administrator
 * @Date: 2018/6/27 15:57
 * @Description:
 */
@Component
public class CollectionApiFacade {
    @Autowired
    private IChekeCollectionService chekeCollectionService;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private ArticleApiFacade articleApiFacade;

    @Autowired
    private CourseApiFacade courseApiFacade;

    @Autowired
    private CourseRedisApiFacade courseRedisApiFacade;

    @Autowired
    private ICourseUserRelationService courseUserRelationService;

    /**
     * 收藏
     * @param collectionId
     * @param userId
     * @param isCourse
     * @return
     */
    public JSONObject goCollection(Long collectionId, Long userId, Integer isCourse) {
        chekeCollectionService.goCollection(collectionId, userId,isCourse);
        JSONObject obj = new JSONObject();
        boolean isCollection = chekeCollectionService.isCollected(collectionId, userId);
        obj.put("isCollection",isCollection);
        return obj;
    }

    public JSONArray findCollectionByPage(Page<Map<String, Object>> page, Map<String, Object> filters ,User user) throws Exception{
        JSONArray array = new JSONArray();
        Page<ChekeCollection> _page = new Page<>();
        CommonUtils.pageConversion(page, _page);
        _page = chekeCollectionService.findCollectionPage(_page,filters);
        for(ChekeCollection chekeCollection : _page.getResult()){
            //加入格式化逻辑
            if(chekeCollection.getIsCourse() == 0){
                Article article = articleService.getArticleById(chekeCollection.getCollectionId());
                array.add(convertArticle(article));
            }else{
                Course course = courseService.getCourseById(chekeCollection.getCollectionId());
                array.add(convertCourse(course,user));
            }
        }
        CommonUtils.pageConversion(_page, page);
        return array;

    }

    private JSONObject convertArticle(Article article) {
        JSONObject data = new JSONObject();
        JSONArray imgArry = new JSONArray();
        if(article == null){
            return null;
        }
        //是否课程大类,0：否
        data.put("isCourse", 0);
        //文章Id
        data.put("articleId", article.getId()==null?"":article.getId());
        //课程Id
        data.put("ID", article.getCourseId()==null?"":article.getCourseId());
        //标题
        data.put("title", article.getTitle()==null?"":article.getTitle());

        //缩略图
        if(article.getImage()!=null && !"".equals(article.getImage())){
            imgArry.add(article.getImage());
        }
        if(article.getImage2()!=null && !"".equals(article.getImage2())){
            imgArry.add(article.getImage2());
        }
        if(article.getImage3()!=null && !"".equals(article.getImage3())){
            imgArry.add(article.getImage3());
        }
        data.put("image", imgArry);

        //视频URL
        data.put("video_url", article.getVideo_url()==null?"":article.getVideo_url());

        //是否推广
        data.put("aIsTask", article.getaIsTask()==null?"":article.getaIsTask());
        //频道类别
        data.put("channelType", article.getChannelType()==null?"":article.getChannelType());
        //展示类别
        data.put("displayType", article.getDisplayType()==null?"":article.getDisplayType());
        //频道类别（CN）
        data.put("channelTypeCN", article.getChannelType()==null?"": ArticleChannel.getChannelName(article.getChannelType()));
        //发布到推客的时间
        String publishTimeCN = "";
        Long publishTime = article.getPublishTime().getTime();
        Long nowTime = System.currentTimeMillis();
        boolean isToday = false;
        try {
            isToday = article.getPublishTime().after(DateUtils.getTodayBegin());
            System.out.println("===========isToday==============="+isToday);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(nowTime - publishTime<60*60*1000){//1小时以内
            publishTimeCN = "刚刚";
        }else if(isToday){
            publishTimeCN = new SimpleDateFormat("HH:mm").format(article.getPublishTime());
        }else{
            publishTimeCN = new SimpleDateFormat("MM月dd日").format(article.getPublishTime());
        }
        data.put("publishTime", article.getPublishTime()==null?"":new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(article.getPublishTime()));
        data.put("publishTimeCN", publishTimeCN);
        //发布人，若为广告则显示来源
        if( !article.getChannelType().equals(ArticleChannel.AD.getType())){
            Map<String, Object> cheKeHao = articleApiFacade.getCheKeHao(article.getCreateUserId());
            data.put("chekeUserName", cheKeHao.get("partyName"));
        }else{
            data.put("chekeUserName", article.getSource() == null?"" : article.getSource());
        }

        return data;
    }

    private JSONObject convertCourse(Course course,User user) {
        JSONObject data = new JSONObject();
        JSONArray imgArry = new JSONArray();
        if(course == null){
            return null;
        }
        //是否课程大类,1：是
        data.put("isCourse", 1);
        //课程Id
        data.put("courseId", course.getId()==null?"":course.getId());
        //课程名
        data.put("courseName", course.getCourseName()==null?"":course.getCourseName());

        //课程来源
        data.put("source", course.getSource()==null?"":course.getSource());

        //课程状态
        data.put("status", course.getStatus()==null?"":course.getStatus());
        //课程学习状态
        Integer stuStatus = courseApiFacade.getCourseStuStatus(course,user);
        data.put("stuStatus", stuStatus==null ? CourseStuStatus.WAITING.getType() : stuStatus);
        //缩略图
        if(course.getImage()!=null && !"".equals(course.getImage())){
            imgArry.add(course.getImage());
        }
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
}
