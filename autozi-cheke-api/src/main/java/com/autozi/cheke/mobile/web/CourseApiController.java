package com.autozi.cheke.mobile.web;

import com.autozi.cheke.article.entity.ArticleCountInfo;
import com.autozi.cheke.article.type.ArticleStatus;
import com.autozi.cheke.course.entity.Course;
import com.autozi.cheke.course.entity.CourseUserRelation;
import com.autozi.cheke.course.type.CourseStatus;
import com.autozi.cheke.course.type.CourseStuStatus;
import com.autozi.cheke.mobile.constant.MobileConstant;
import com.autozi.cheke.mobile.facade.ArticleRedisApiFacade;
import com.autozi.cheke.mobile.facade.CourseApiFacade;
import com.autozi.cheke.mobile.facade.CourseRedisApiFacade;
import com.autozi.cheke.mobile.facade.UserApiFacade;
import com.autozi.cheke.service.course.ICourseUserRelationService;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.exception.B2bException;
import com.autozi.common.core.page.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by mingxin.li on 2018/5/21 10:42.
 */

@RequestMapping("/course/tk/")
@Controller
public class CourseApiController extends BaseApiController{

    @Autowired
    private CourseApiFacade courseApiFacade;

    @Autowired
    private CourseRedisApiFacade courseRedisApiFacade;

    @Autowired
    private ICourseUserRelationService courseUserRelationService;

    @Autowired
    private ArticleRedisApiFacade articleRedisApiFacade;

    @Autowired
    private UserApiFacade userApiFacade;

    /**
     * 课程列表		courseList
     * @param request
     * @param response
     */
    @RequestMapping("index/listCourse.api")
    public void listCourse(HttpServletRequest request, HttpServletResponse response){
        JSONObject data = new JSONObject();
        try {
            //根据token获取用户
            User user = this.getUser(request);

            //封装分页
            Page<Map<String, Object>> page = new Page<>();
            String pageNo = request.getParameter("pageNo");
            if (StringUtils.isNotBlank(pageNo)) {
                page.setPageNo(Integer.parseInt(pageNo));
            } else {
                page.setPageNo(MobileConstant.PageSize._page_no);
            }
            String pageSizeStr = request.getParameter("pageSize");
            if (StringUtils.isNotBlank(pageSizeStr)) {
                page.setPageSize(Integer.parseInt(pageSizeStr));
            } else {
                page.setPageSize(MobileConstant.PageSize._page_size);
            }

            //封装查询条件
            Map<String, Object> filters = new HashMap<String, Object>();
            Map<String, Object> filter = new HashMap<String, Object>();

            String searchKeyWord = request.getParameter("searchKeyWord");
            if (StringUtils.isNotBlank(searchKeyWord)) {
                filters.put("searchKeyWord", searchKeyWord);
            }

            //课程状态：1已上线
            filters.put("status", CourseStatus.PUBLISH.getType());
            filters.put("orderBy", "co.create_time desc");
            filter.put("courseStatus", CourseStatus.PUBLISH.getType());
            filter.put("studyingIncludeRestudyFlag", true);
            filter.put("orderBy", "cur.update_time desc");

            //filters.put("channelType", ArticleChannel.COURSE.getType());


            JSONArray list = courseApiFacade.findCourseByPage(page, filters,user);
            JSONArray planList = new JSONArray();
            if (user != null && Integer.parseInt(pageNo)==1 ) {
                filter.put("userId", user.getId());
                planList = courseApiFacade.getMyPlan(page,filter);
            }

            data.put("planList", planList);
            data.put("list", list);
            data.put("pageNo", page.getPageNo());
            data.put("totalPages", page.getTotalPages());

            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
        }catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }


    /**
     * 课程详细内容列表
     * @param request
     * @param response
     * @param courseId
     */
    @RequestMapping("index/listCourseDetail.api")
    public void listDetailCourse(HttpServletRequest request, HttpServletResponse response, Long courseId){
        try {
            User user = super.getUser(request);
            if (courseId==null) {
                throw new B2bException("courseId为空");
            }
//            JSONObject data = courseApiFacade.getCourseDetail(courseId,user);
            JSONObject data = new JSONObject();

            //封装分页
            Page<Map<String, Object>> page = new Page<>();
            String pageNo = request.getParameter("pageNo");
            if (StringUtils.isNotBlank(pageNo)) {
                page.setPageNo(Integer.parseInt(pageNo));
            } else {
                page.setPageNo(MobileConstant.PageSize._page_no);
            }
            String pageSizeStr = request.getParameter("pageSize");
            if (StringUtils.isNotBlank(pageSizeStr)) {
                page.setPageSize(Integer.parseInt(pageSizeStr));
            } else {
                page.setPageSize(MobileConstant.PageSize._page_size);
            }

            //封装查询条件
            Map<String, Object> filters = new HashMap<>();

            String searchKeyWord = request.getParameter("searchKeyWord");
            if (StringUtils.isNotBlank(searchKeyWord)) {
                filters.put("searchKeyWord", searchKeyWord);
            }

            //课程ID
            filters.put("courseId",courseId);
            //文章状态：2已上线
            filters.put("status", ArticleStatus.PUBLISH.getType());
            //filters.put("orderBy", "concat(ar.A_IS_TOP,ar.PUBLISH_TIME) desc");
            filters.put("orderBy", "ar.num asc");
            JSONObject courseInfo = courseApiFacade.getCourseInfo(courseId,user);

            JSONArray list = courseApiFacade.findCourseListByPage(page, filters,user);

            data.put("courseInfo", courseInfo);
            data.put("list", list);
            data.put("pageNo", page.getPageNo());
            data.put("totalPages", page.getTotalPages());

            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
        }catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }

    /**
     * 相关课程列表
     * @param request
     * @param response
     * @param courseId
     */
    @RequestMapping("index/listRelativeChapter.api")
    public void listRelativeChapter(HttpServletRequest request, HttpServletResponse response, Long courseId,Long articleId){
        try {
            User user = super.getUser(request);
            if (courseId==null) {
                throw new B2bException("courseId为空");
            }
            if (articleId==null) {
                throw new B2bException("articleId为空");
            }

            JSONObject data = new JSONObject();

            //封装分页
            Page<Map<String, Object>> page = new Page<>();
            String pageNo = request.getParameter("pageNo");
            if (StringUtils.isNotBlank(pageNo)) {
                page.setPageNo(Integer.parseInt(pageNo));
            } else {
                page.setPageNo(MobileConstant.PageSize._page_no);
            }
            String pageSizeStr = request.getParameter("pageSize");
            if (StringUtils.isNotBlank(pageSizeStr)) {
                page.setPageSize(Integer.parseInt(pageSizeStr));
            } else {
                page.setPageSize(MobileConstant.PageSize._page_size);
            }

            //封装查询条件
            Map<String, Object> filters = new HashMap<>();

            String searchKeyWord = request.getParameter("searchKeyWord");
            if (StringUtils.isNotBlank(searchKeyWord)) {
                filters.put("searchKeyWord", searchKeyWord);
            }

            //课程ID
            filters.put("courseId",courseId);
            //需要排除的文章ID
            filters.put("removeChapterId",articleId);
            //文章状态：2已上线
            filters.put("status", ArticleStatus.PUBLISH.getType());
            filters.put("orderBy", "ar.num asc");
            JSONObject courseInfo = courseApiFacade.getCourseInfo(courseId,user);
            JSONArray list = courseApiFacade.findCourseListByPage(page, filters,user);

            data.put("courseInfo",courseInfo);
            data.put("list", list);
            data.put("pageNo", page.getPageNo());
            data.put("totalPages", page.getTotalPages());

            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
        }catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }

    /**
     * 保存课程章节学习状态以及进度
     *  @param request
     *  @param response
     */
    @RequestMapping("saveCourseProgress.api")
    public void saveCourseProgress(HttpServletRequest request, HttpServletResponse response, Long articleId){
        try{
            this.checkToken(request, response);
            User user = super.getUser(request);
            if (articleId == null) {
                throw new B2bException("articleId为空");
            }
            Map<String, Object> filters = new HashMap<>();
            filters.put("userId",user.getId());
            filters.put("articleId",articleId);
            CourseUserRelation courseUserRelation = courseUserRelationService.findCourseUserRelationObj(filters);

            if(courseUserRelation == null){
                return ;
            }
            Integer oldStatus = courseUserRelation.getStatus();
            Integer newStatus = Integer.valueOf(request.getParameter("status"));

            String currentProgress = request.getParameter("currentProgress")==null ? "0" : request.getParameter("currentProgress");
            Boolean completeStudyFlag = false;//章节学习完成标志
            if (newStatus > oldStatus) {

                //当前学习状态:待学习
                if(Objects.equals(oldStatus, CourseStuStatus.WAITING.getType())){
                    //变为学习状态由待学习到学习中
                    if( Objects.equals(newStatus,CourseStuStatus.STUDYING.getType())){
                        //增加章节正在学习人数
                        articleRedisApiFacade.countStuNum(courseUserRelation.getArticleId());
                    }

                    //学习状态由待学习变为已完成
                    if(Objects.equals(newStatus,CourseStuStatus.COMPLETE.getType())){
                        completeStudyFlag = true;
                        //已学成的人数+1
                        articleRedisApiFacade.countCompletedNum(courseUserRelation.getArticleId());
                        //排名
                        ArticleCountInfo info = articleRedisApiFacade.getCountInfo(courseUserRelation.getArticleId());
                        courseUserRelation.setRanking(info.getCompletedNum());
                        //统计课程学成人数
                        boolean courseCompletedFlag = courseApiFacade.checkCourseCompletedStudy(user,courseUserRelation.getCourseId());
                        if(courseCompletedFlag){
                            courseRedisApiFacade.countCompletedNum(courseUserRelation.getCourseId());
                        }
                    }

                }
                //学习状态由学习中变为已完成
                if(Objects.equals(oldStatus, CourseStuStatus.STUDYING.getType())
                        && Objects.equals(Integer.valueOf(newStatus),CourseStuStatus.COMPLETE.getType())){
                    completeStudyFlag = true;
                    //已学成的人数+1
                    articleRedisApiFacade.countCompletedNum(courseUserRelation.getArticleId());

                    //排名
                    ArticleCountInfo info = articleRedisApiFacade.getCountInfo(courseUserRelation.getArticleId());
                    courseUserRelation.setRanking(info.getCompletedNum());

                    //统计课程学成人数
                    boolean courseCompletedFlag = courseApiFacade.checkCourseCompletedStudy(user,courseUserRelation.getCourseId());
                    if(courseCompletedFlag){
                        courseRedisApiFacade.countCompletedNum(courseUserRelation.getCourseId());
                    }
                }

                //设置学习状态
                courseUserRelation.setStatus(newStatus);
            } else {
                //已学成,再次学习
                if(oldStatus == 2 && newStatus == 1){
                    //设置学习状态为RESTUDY
                    courseUserRelation.setStatus(CourseStuStatus.RESTUDY.getType());
                }
                //已学成,再次学成
                if(oldStatus == 3 && newStatus == 2){
                    //设置学习状态为COMPLETE
                    courseUserRelation.setStatus(CourseStuStatus.COMPLETE.getType());
                }
            }
            //设置进度
            courseUserRelation.setCurrentProgress(currentProgress);

            courseUserRelationService.updataCourseUserRelation(courseUserRelation);

            //统计个人学习章节数
            userApiFacade.studyCount(user,completeStudyFlag);

            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg);
        } catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }

    /**
     * 我的课程
     */
    @RequestMapping("/user/listMyCourse.api")
    public void listMyCourse(HttpServletRequest request,HttpServletResponse response){
        JSONObject data = new JSONObject();
        try {
            this.checkToken(request, response);
            //根据token获取用户
            User user = this.getUser(request);
            if(user==null){
                this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
                return;
            }
            String status = request.getParameter("status")==null?"" : request.getParameter("status");
            //封装分页
            Page<Map<String, Object>> page = new Page<>();
            String pageNo = request.getParameter("pageNo");
            if (StringUtils.isNotBlank(pageNo)) {
                page.setPageNo(Integer.parseInt(pageNo));
            } else {
                page.setPageNo(MobileConstant.PageSize._page_no);
            }
            String pageSizeStr = request.getParameter("pageSize");
            if (StringUtils.isNotBlank(pageSizeStr)) {
                page.setPageSize(Integer.parseInt(pageSizeStr));
            } else {
                page.setPageSize(MobileConstant.PageSize._page_size);
            }

            //封装查询条件
            Map<String, Object> filters = new HashMap<String, Object>();

            //课程学习状态 WAITING(0,"未开始"),STUDYING(1,"学习中"),COMPLETE(2,"已结业"),DELETE(-1,"已删除");
            if(Integer.valueOf(status).equals(CourseStuStatus.COMPLETE.getType())) {
                filters.put("completedIncludeRestudyFlag",true);
            }else if(Integer.valueOf(status).equals(CourseStuStatus.STUDYING.getType())) {
                filters.put("studyingIncludeRestudyFlag",true);
            } else {
                filters.put("status",Integer.valueOf(status));
            }

            filters.put("userId", user.getId());
            //课程（大类）状态为上线
            filters.put("courseStatus", CourseStatus.PUBLISH.getType());
            filters.put("orderBy", " cur.update_time desc");

            JSONArray list = courseApiFacade.getMyPlan(page,filters);

            data.put("list", list);
            data.put("pageNo", page.getPageNo());
            data.put("totalPages", page.getTotalPages());

            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
        } catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }

    @RequestMapping("updateRedisCourse.action")
    public void updateRedisCourse(Long courseId,int num){

        Course course = courseRedisApiFacade.getCourse(courseId);
        course.setChapterNum(num);
        courseApiFacade.updateObj(course);

    }

    @RequestMapping("deleteRedisCourse.action")
    public void deleteRedisCourse(Long courseId){
        courseApiFacade.deleteCourse(courseId);

    }



}
