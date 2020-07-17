package com.autozi.cheke.web.course.controller;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.course.entity.Course;
import com.autozi.cheke.course.type.CourseTypeEnum;
import com.autozi.cheke.party.type.IPartyType;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.article.facade.ArticleAdminFacade;
import com.autozi.cheke.web.article.facade.ArticleRedisFacade;
import com.autozi.cheke.web.article.vo.ArticleVo;
import com.autozi.cheke.web.course.facade.CourseAdminFacade;
import com.autozi.cheke.web.course.vo.CourseVo;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *
 *@author mingxin li
 *@data 2018/5/21
 *
 */
@Controller
@RequestMapping("/course/admin/")
public class CourseAdminController extends BaseController{


    @Autowired
    private CourseAdminFacade courseAdminFacade;

    @Autowired
    private ArticleAdminFacade articleAdminFacade;

    @Autowired
    private ArticleRedisFacade articleRedisFacade;


    @RequestMapping("/list/listCourse.action")
    public String listCourse(Model uiModel,HttpServletRequest request,String ajax) throws Exception{
        Map<String,Object> filter = buildFilter(request,uiModel);
        Page<Course> page = buildPage(request);

        //第一次进入默认是待发布
        if(filter.get("status") == null) {
            filter.put("status",0);
            uiModel.addAttribute("status",filter.get("status") == null ? "0" : filter.get("status").toString());
        }else if(Integer.parseInt(filter.get("status").toString()) == -100){
            uiModel.addAttribute("status",filter.get("status") == null ? "-100" : filter.get("status").toString());
            filter.remove("status");
        }else{
            uiModel.addAttribute("status",filter.get("status") == null ? "-100" : filter.get("status").toString());
        }

/*        if(Integer.valueOf(filter.get("status").toString()).intValue() == 0) {
            filter.remove("status");
            filter.put("waitAndRefuseStatus",true);
        }*/

        filter.put("createUserId",getCurrentUserId());

        Page<CourseVo> voPage = courseAdminFacade.findCoursePage(page,filter);

        this.pageInfoByMap(uiModel,voPage,filter);
        uiModel.addAttribute("page",voPage);
        uiModel.addAttribute("courseTypeMap", CourseTypeEnum.getTypeMap());

        if(ajax != null) {
            return "/admin/course/listCourse_ajax";
        }
        return "/admin/course/listCourse.html";
    }

    @RequestMapping("/create/createCourse.action")
    public String createCourse(@RequestParam(required = false) Long id, Model uiModel) throws Exception{
        Course course = new Course();
        if(id != null) {
            course = courseAdminFacade.getByCourseId(id);
            System.out.println("***********************"+course.getTag());
            System.out.println("***********************"+course.getCourseName());
            uiModel.addAttribute("typeName", CourseTypeEnum.getNameByType(course.getType()));
        }
        uiModel.addAttribute("co",course);
        Map<String,String> typeMap = CourseTypeEnum.getTypeMap();
        typeMap.remove("0");
        uiModel.addAttribute("courseTypeMap", typeMap);

        return "/admin/course/createOrUpdateCourse.html";
    }

    @RequestMapping("/update/manageCourse.action")
    public String manageCourse(Model uiModel,HttpServletRequest request,@RequestParam(required = false) Long id,String ajax) throws Exception{
        Map<String,Object> filter = new HashMap<String, Object>();
        //Map<String,Object> filter = buildFilter(request,uiModel);
        Map<String, Object> cfilter = new HashMap<String, Object>();
        Page<Article> page = buildPage(request);
        Page<Article> cpage = buildPage(request);
        page.setPageSize(10);

        if(id != null) {
            Course course = courseAdminFacade.getByCourseId(id);
            uiModel.addAttribute("courseName",course.getCourseName());
        }
        if(filter.get("status") == null) {
            filter.put("status",2);
        }
        if(request.getParameter("title")!=null){
            filter.put("title",request.getParameter("title").toString());
        }
        filter.put("notChapterFlag",true);
        filter.put("orderBy","ar.num asc");
        //filter.put("createUserId",getCurrentUserId());

        cfilter.put("status",2);
        cfilter.put("courseId",id);

        Page<ArticleVo> articlePage = articleAdminFacade.findArticlePage(page,filter);
        Page<ArticleVo> chapterPage = articleAdminFacade.findArticlePage(cpage,cfilter);


        this.pageInfoByMap(uiModel,chapterPage,filter);
        this.pageInfoByMap(uiModel,articlePage,filter);
        uiModel.addAttribute("articlePage",articlePage);
        uiModel.addAttribute("chapterPage",chapterPage);
        uiModel.addAttribute("partyClassMap", IPartyType.partyClassMap);

        uiModel.addAttribute("courseId", id);
        uiModel.addAttribute("status",filter.get("status") == null ? "0" : filter.get("status").toString());
        if(ajax != null) {
            return "/admin/course/manageArticleOfCourse_ajax.html";
        }

        return "/admin/course/manageArticleOfCourse.html";
    }


    /**
     * 新增或修改课程
     * @param course
     * @param response
     */
    @RequestMapping("/create/createOrUpdateCourse.action")
    public void createOrUpdateCourse(Course course, HttpServletResponse response) throws Exception{
        //新增
        if(course.getId() == null) {
            course.setCreateTime(new Date());
            course.setCreateUserId(getCurrentUserId());
            course.setStatus(0);
            int num = courseAdminFacade.addCourse(course);
            if(num > 0) {
                responseJson(response,"ok","新增成功");
            }else {
                responseJson(response,"err","新增失败");
            }

        }else {
            //修改
            course.setUpdateTime(new Date());
            int num = courseAdminFacade.updateCourse(course);
            if(num > 0) {
                responseJson(response,"ok","修改成功");
            }else {
                responseJson(response,"err","修改失败");
            }

        }
    }


    /**
     * 下线操作
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping("/update/offlineCourse.action")
    public void offlineCourse(Long id,HttpServletResponse response) throws Exception{
        int num = courseAdminFacade.offLineCourse(id);
        if(num > 0) {
            responseJson(response,"ok","下线成功");
        }else {
            responseJson(response,"err","下线失败");
        }
    }

    /**
     * 上线操作
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping("/update/onlineCourse.action")
    public void onlineCourse(Long id,HttpServletResponse response) throws Exception{
        int num = courseAdminFacade.onlineCourse(id);
        if(num > 0) {
            responseJson(response,"ok","下线成功");
        }else {
            responseJson(response,"err","下线失败");
        }
    }

    /**
     * 添加课程章节操作
     * @param articleId
     * @param courseId
     * @param response
     * @throws Exception
     */
    @RequestMapping("/update/addArticleToCourse.action")
    public void addArticleToCourse(Long articleId,Long courseId,Integer num,HttpServletResponse response) throws Exception{

        int flag = articleAdminFacade.addArticleToCourse(articleId,courseId,num);
        if(flag > 0) {
            responseJson(response,"ok","成功");
        }else if(flag==-2){
            responseJson(response,"err","章节序号重复");
        }else{
            responseJson(response,"err","失败");
        }
    }

    @RequestMapping("/update/delArticleFromCourse.action")
    public void delArticleFromCourse(Long articleId,HttpServletResponse response) throws Exception{

        int flag = articleAdminFacade.delArticleFromCourse(articleId);
        Article article = articleRedisFacade.getArticle(articleId);

        if(flag > 0) {
            responseJson(response,"ok","成功");
        }else {
            responseJson(response,"err","失败");
        }
    }

    /**
     * 批量添加课程章节操作
     * @param articleIds
     * @param courseId
     * @param response
     * @throws Exception
     */
    @RequestMapping("/update/batchAddArticleToCourse.action")
    public void batchAddArticleToCourse(String articleIds,Long courseId,HttpServletResponse response) throws Exception{
        List<String> listIds = Arrays.asList(articleIds.split(","));

        articleAdminFacade.batchAddArticleToCourse(listIds,courseId);

        responseJson(response,"ok","批量添加成功");

    }


}
