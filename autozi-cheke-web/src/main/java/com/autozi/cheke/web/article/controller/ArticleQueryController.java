package com.autozi.cheke.web.article.controller;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.article.type.ArticleChannel;
import com.autozi.cheke.article.type.ArticleStatus;
import com.autozi.cheke.article.type.ArticleTypeEnum;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.article.facade.ArticleFacade;
import com.autozi.cheke.web.article.vo.ArticleVo;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by wang-lei on 2017/12/12.
 */
@Controller
@RequestMapping("/article/yxpt/")
public class ArticleQueryController extends BaseController{

    @Autowired
    private ArticleFacade articleFacade;


    @RequestMapping("/query/queryArticle.action")
    public String queryArticle(Model uiModel, HttpServletRequest request, String ajax) throws Exception{
        Map<String,Object> filter = buildFilter(request,uiModel);
        Page<Article> page = buildPage(request);
        filter.put("createUserId",getCurrentUserId()); //只能看自己的

        Page<ArticleVo> voPage = articleFacade.findQueryArticlePage(page,filter);

        this.pageInfoByMap(uiModel,voPage,filter);
        uiModel.addAttribute("page",voPage);
        uiModel.addAttribute("articleTypeMap", ArticleTypeEnum.getTypeMap());
        uiModel.addAttribute("articleChannelMap", ArticleChannel.getChannelMap());

        Map<String,String> statusMap =  ArticleStatus.getStatusMap();
        statusMap.remove("-3");

        uiModel.addAttribute("articleStatusMap", statusMap);

        uiModel.addAttribute("totalUser",articleFacade.countArticleByUserId(getCurrentUserId()));

        if(ajax != null) {
            return "/article/query/queryArticle_ajax";
        }
        return "/article/query/queryArticle.html";
    }



}
