package com.autozi.cheke.web.message.controller;

import com.autozi.cheke.user.entity.FeedBack;
import com.autozi.cheke.user.entity.SearchFeedBack;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.message.facade.FeedBackAdminFacade;
import com.autozi.cheke.web.user.facade.FeedBackFacade;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/6/28 16:36
 * @Description:
 */
@Controller
@RequestMapping("/feedback/admin/")
public class FeedBackController extends BaseController{
    @Autowired
    private FeedBackAdminFacade feedBackAdminFacade;

    @RequestMapping("list/listFeedback.action")
    public String listFeedback(Model uiModel, HttpServletRequest request, String ajax){
        Map<String,Object> filter = buildFilter(request,uiModel);
        Page<FeedBack> page = buildPage(request);

        Page<FeedBack> vPage = feedBackAdminFacade.findFeedBackPage(page,filter);

        this.pageInfoByMap(uiModel,vPage,filter);
        uiModel.addAttribute("page",vPage);
        if(ajax != null) {
            return "/admin/message/feedback/listFeedback_ajax";
        }
        return "/admin/message/feedback/listFeedback.html";
    }


    @RequestMapping("list/listFeedbackForSearch.action")
    public String listFeedbackForSearch(Model uiModel, HttpServletRequest request, String ajax){
        Map<String,Object> filter = buildFilter(request,uiModel);
        Page<SearchFeedBack> page = buildPage(request);

        Page<SearchFeedBack> vPage = feedBackAdminFacade.findFeedBackForSearchPage(page,filter);

        this.pageInfoByMap(uiModel,vPage,filter);
        uiModel.addAttribute("page",vPage);
        if(ajax != null) {
            return "/admin/message/feedback/listSearchFeedback_ajax";
        }
        return "/admin/message/feedback/listSearchFeedback.html";
    }
}
