package com.autozi.cheke.mobile.web;

import com.autozi.cheke.mobile.constant.MobileConstant;
import com.autozi.cheke.mobile.facade.CollectionApiFacade;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.exception.B2bException;
import com.autozi.common.core.page.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/6/27 15:55
 * @Description:
 */
@Controller
@RequestMapping("/collection/tk/")
public class CollectionApiController extends BaseApiController {

    @Autowired
    private CollectionApiFacade collectionApiFacade;

    /**
     * 收藏功能(强制登录)  ，收藏文章/课程
     * @param request
     * @param response
     */
    @RequestMapping("goCollection.api")
    public void goCollection(HttpServletRequest request, HttpServletResponse response){
        try {
            User user = super.getUser(request);
            //是否登录
            if(user==null){
                this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
                return;
            }
            String articleId = request.getParameter("articleId");
            String courseId = request.getParameter("courseId");
            Integer isCourse = 0;
            Long collectionId = null;
            if(StringUtils.isBlank(articleId) && StringUtils.isBlank(courseId)){
                this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
                return;
            }
            if(StringUtils.isNotBlank(articleId) && StringUtils.isBlank(courseId)){
                collectionId = Long.valueOf(articleId);
            }
            if(StringUtils.isNotBlank(courseId) && StringUtils.isBlank(articleId)){
                collectionId = Long.valueOf(courseId);
                isCourse = 1;
            }
            JSONObject obj = collectionApiFacade.goCollection(collectionId,user.getId(),isCourse);
            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,obj.toString());
        }catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }

    //收藏(强制登录)  收藏列表
    @RequestMapping("collectionList.api")
    public void collectionList(HttpServletRequest request, HttpServletResponse response){
        JSONObject data = new JSONObject();
        try {
            User user = super.getUser(request);
            //是否登录
            if(user==null){
                this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
                return;
            }
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
            filters.put("userId", user.getId());
            JSONArray list = collectionApiFacade.findCollectionByPage(page, filters ,user);

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

}
