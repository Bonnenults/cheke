package com.autozi.cheke.mobile.web;

import com.autozi.cheke.mobile.constant.MobileConstant;
import com.autozi.cheke.mobile.facade.CommentApiFacade;
import com.autozi.cheke.mobile.util.SensitiveWordUtil.WordFilter;
import com.autozi.cheke.service.user.IUserService;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.exception.B2bException;
import com.autozi.common.core.page.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: Administrator
 * @Date: 2018/6/23 09:57
 * @Description:
 */
@RequestMapping("/comment/tk/")
@Controller
public class CommentApiController extends BaseApiController {

    @Autowired
    private CommentApiFacade commentApiFacade;
    @Autowired
    private IUserService userService;

    /**
     * 存储评论内容
     * 若评论对象为文章则articleId不允许为空，若文章为课程章节则同时需要传入courseId
     * 若评论对象为课程则courseId不允许为空
     * 若评论对象为评论则commentId不允许为空
     * @param request
     * @param response
     */
    @RequestMapping("comment.api")
    public void comment(HttpServletRequest request, HttpServletResponse response){
        try{
            User user = this.getUser(request);
            if(user == null){
                this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
                return;
            }
            String article_id = request.getParameter("articleId");
            String comment_id = request.getParameter("commentId");
            String course_id = request.getParameter("courseId");
            String content = request.getParameter("content");

            if(StringUtils.isBlank(article_id)
                    && StringUtils.isBlank(comment_id)
                    && StringUtils.isBlank(course_id)){
                throw new B2bException("必要信息不能为空");
            }
            if(StringUtils.isBlank(content)){
                throw new B2bException("评论内容不能为空");
            }

            WordFilter wordFilter =new WordFilter();
            long nano = System.nanoTime();
            System.out.println("解析问题： " + content);
            System.out.println("解析字数 : " + content.length());
            System.out.println("是否包含敏感词： " + WordFilter.isContains(content));
            nano = (System.nanoTime() - nano);
            System.out.println("解析时间 : " + nano + "ns");
            System.out.println("解析时间 : " + nano / 1000000 + "ms");
            System.out.println();

            nano = System.nanoTime();
            content = wordFilter.doFilter(content);
            nano = (System.nanoTime() - nano);
            System.out.println("解析时间 : " + nano + "ns");
            System.out.println("解析时间 : " + nano / 1000000 + "ms");
            System.out.println(content);

            Long replyId = null;
            Long courseId = null;
            Integer isSub = 0;//是否为子评论  0：否 1：是
            //文章ID不为空，说明被评论对象为文章
            if(StringUtils.isNotBlank(article_id)){
                replyId = Long.valueOf(article_id);
                //文章ID不为空，课程ID不为空，说明被评论对象为课程章节
                if(StringUtils.isNotBlank(course_id)){
                    courseId = Long.valueOf(course_id);
                }
            }
            //commentId不为空，说明被评论对象为评论
            if(StringUtils.isNotBlank(comment_id)){
                replyId = Long.valueOf(comment_id);
                isSub = 1;
            }
            //文章ID为空，课程ID不为空，说明被评论对象为课程
            if(StringUtils.isBlank(article_id) && StringUtils.isNotBlank(course_id)){
                replyId = Long.valueOf(course_id);
            }
            Long id = commentApiFacade.saveComment(replyId,user.getId(),courseId,content,isSub);

            JSONObject data = new JSONObject();
            data = commentApiFacade.addChekeCommentCallBack(id,user);
            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
        }catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }

    }


    /**
     * 评论列表
     * @param request
     * @param response
     */
    @RequestMapping("listComment.api")
    public void listComment(HttpServletRequest request, HttpServletResponse response){
        JSONObject data = new JSONObject();
        try {
            User user = this.getUser(request);

            //封装分页
            Page<Map<String, Object>> page = new Page<Map<String, Object>>();
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

            String article_id = request.getParameter("articleId");
            String comment_id = request.getParameter("commentId");
            String course_id = request.getParameter("courseId");
            if(StringUtils.isBlank(article_id)
                    && StringUtils.isBlank(comment_id)
                    && StringUtils.isBlank(course_id)){
                throw new B2bException("必要信息不能为空");
            }

            if(StringUtils.isNotBlank(article_id)){
                filters.put("replyId", Long.valueOf(article_id));
            }
            if(StringUtils.isNotBlank(comment_id)){
                filters.put("replyId", Long.valueOf(comment_id));
            }
            if(StringUtils.isNotBlank(course_id)){
                filters.put("replyId", Long.valueOf(course_id));
            }

            JSONArray list = commentApiFacade.findCommentByPage(page,filters,user);
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

    @RequestMapping("deleteComment.api")
    public void deleteComment(HttpServletRequest request, HttpServletResponse response){
        try {
            User user = this.getUser(request);
            if (user == null) {
                this.response(request, response, MobileConstant.Error._tokenIsExpire, MobileConstant.Error._tokenIsExpire_msg);
                return;
            }
            String comment_id = request.getParameter("commentId");
            if (StringUtils.isBlank(comment_id)) {
                throw new B2bException("必要信息不能为空");
            }
            commentApiFacade.deleteComment(Long.valueOf(comment_id),user);
            this.response(request, response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg);
        }catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }
}
