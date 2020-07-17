package com.autozi.cheke.web.message.controller;

import com.autozi.cheke.article.entity.ArticleLeaveWord;
import com.autozi.cheke.article.type.ArticleChannel;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.message.facade.LeaveWordAdminFacade;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by wang-lei on 2017/12/11.
 */
@Controller
@RequestMapping("/leaveWord/admin/")
public class LeaveWordAdminController extends BaseController {

    @Autowired
    private LeaveWordAdminFacade leaveWordAdminFacade;


    @RequestMapping("/list/listLeaveWord.action")
    public String listLeaveWord(Model uiModel, HttpServletRequest request,String ajax) {
        Map<String,Object> filter = buildFilter(request,uiModel);
        Page<ArticleLeaveWord> page = buildPage(request);

        //TODO filter取消车客PartyId

        Page<ArticleLeaveWord> vPage = leaveWordAdminFacade.findLeaveWordPage(page,filter);

        this.pageInfoByMap(uiModel,vPage,filter);
        uiModel.addAttribute("page",vPage);
        uiModel.addAttribute("articleChannelMap", ArticleChannel.getChannelMap());

        if(ajax != null) {
            return "/admin/message/leaveword/listLeaveWord_ajax";
        }
        return "/admin/message/leaveword/listLeaveWord.html";
    }



}
