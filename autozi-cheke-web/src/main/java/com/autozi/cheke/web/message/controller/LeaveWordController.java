package com.autozi.cheke.web.message.controller;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.article.entity.ArticleCountInfo;
import com.autozi.cheke.article.entity.ArticleLeaveWord;
import com.autozi.cheke.article.type.ArticleChannel;
import com.autozi.cheke.article.type.ArticleTypeEnum;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.article.facade.ArticleAdminFacade;
import com.autozi.cheke.web.article.facade.ArticleFacade;
import com.autozi.cheke.web.message.facade.LeaveWordAdminFacade;
import com.autozi.cheke.web.message.facade.LeaveWordFacade;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.util1.HtmlUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by wang-lei on 2017/12/11.
 */
@Controller
@RequestMapping("/leaveWord/yxpt/")
public class LeaveWordController extends BaseController {

    @Autowired
    private LeaveWordFacade leaveWordFacade;

    @Autowired
    private LeaveWordAdminFacade leaveWordAdminFacade;

    @Autowired
    private ArticleAdminFacade articleAdminFacade;


    @RequestMapping("/list/listLeaveWord.action")
    public String listLeaveWord(Model uiModel, HttpServletRequest request,String ajax) {
        Map<String,Object> filter = buildFilter(request,uiModel);
        Page<ArticleLeaveWord> page = buildPage(request);

        Page<ArticleLeaveWord> vPage = leaveWordFacade.findLeaveWordPage(page,filter);

        this.pageInfoByMap(uiModel,vPage,filter);
        uiModel.addAttribute("page",vPage);
        uiModel.addAttribute("articleId",filter.get("articleId"));

        if(ajax != null) {
            return "/message/leaveword/listLeaveWord_ajax";
        }
        return "/message/leaveword/listLeaveWord.html";
    }




    @RequestMapping("/list/listLeaveWordDetail.action")
    public String listLeaveWordDetail(Model uiModel, HttpServletRequest request,String ajax) {
        Map<String,Object> filter = buildFilter(request,uiModel);
        Page<ArticleLeaveWord> page = buildPage(request);

        //车客PartyId
        filter.put("chekePartyId",getCurrentUserPartyId());
        Page<ArticleLeaveWord> vPage = leaveWordAdminFacade.findLeaveWordPage(page,filter);

        this.pageInfoByMap(uiModel,vPage,filter);
        uiModel.addAttribute("page",vPage);
        uiModel.addAttribute("articleChannelMap", ArticleChannel.getChannelMap());

        if(ajax != null) {
            return "/message/leaveword/listLeaveWordDetail_ajax";
        }
        return "/message/leaveword/listLeaveWordDetail.html";
    }

    /**
     * 预览页面
     * @param id
     * @param uiModel
     * @return
     * @throws Exception
     */
    @RequestMapping("/list/getArticleDetail.action")
    public String getArticleDetail(@RequestParam(required = true)Long id,Model uiModel) throws Exception{

        Article article = articleAdminFacade.getByArticleId(id);
        ArticleCountInfo countInfo = articleAdminFacade.getCountInfoByArticleId(id);

        try {
            if (StringUtils.isNotBlank(article.getBody())) {
                article.setBody(HtmlUtils.readHtml( article.getBody()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //统计信息
        Map<String,Object> countMap = articleAdminFacade.countArticleClick(article,countInfo);
        uiModel.addAttribute("allClickNum",countMap.get("allClickNum"));
        uiModel.addAttribute("allUsedMoney",countMap.get("allUsedMoney"));
        uiModel.addAttribute("refundMoney",countMap.get("refundMoney"));


        uiModel.addAttribute("typeName", ArticleTypeEnum.getNameByType(article.getType()));
        uiModel.addAttribute("art",article);
        uiModel.addAttribute("countInfo",countInfo);
        uiModel.addAttribute("channelName",ArticleChannel.getChannelName(article.getChannelType()));

        return "/message/leaveword/previewArticleDetail.html";
    }



}
