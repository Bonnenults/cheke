package com.autozi.cheke.web.article.controller;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.article.entity.ArticleCountInfo;
import com.autozi.cheke.article.type.ArticleChannel;
import com.autozi.cheke.article.type.ArticleStatus;
import com.autozi.cheke.article.type.ArticleTypeEnum;
import com.autozi.cheke.party.type.IPartyType;
import com.autozi.cheke.service.settle.IAccountService;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.article.facade.ArticleAdminFacade;
import com.autozi.cheke.web.article.facade.ArticleFacade;
import com.autozi.cheke.web.article.vo.ArticleVo;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mingxin.li on 2018/5/11.
 */

@Controller
@RequestMapping("/article/ad")
public class AdAdminController extends BaseController {

    @Autowired
    private ArticleAdminFacade articleAdminFacade;

    @Autowired
    private ArticleFacade articleFacade;

    @Autowired
    private IAccountService accountService;


    /**
     *
     * 列出所有广告
     *@author mingxin li
     *@data 2018/5/11
     *
     */
    @RequestMapping("/list/listAd.action")
    public String listArticle(Model uiModel, HttpServletRequest request, String ajax) throws Exception{
        Map<String,Object> filter = buildFilter(request,uiModel);
        Page<Article> page = buildPage(request);

        //第一次进入默认是待发布
        if(filter.get("status") == null) {
            filter.put("status", ArticleStatus.WAITING.getType());
            uiModel.addAttribute("status",filter.get("status") == null ? "0" : filter.get("status").toString());
        }else {
            if(Integer.parseInt(filter.get("status").toString()) == -100) {
                uiModel.addAttribute("status",filter.get("status") == null ? "0" : filter.get("status").toString());

            }else {
                uiModel.addAttribute("status",filter.get("status") == null ? "0" : filter.get("status").toString());

                if(Integer.parseInt(filter.get("allFilter") == null ? "0" : filter.get("allFilter").toString()) == 0) {
                    if(Integer.parseInt(filter.get("status").toString()) == -1) {
                        //取消和拒绝的状态
                        filter.remove("status");
                        filter.put("cancelAndRefuseStatus",true);
                    }
                }
            }
        }
        filter.put("channelType", ArticleChannel.AD.getType());
        //不包含待发布的
        //filter.put("notWaitingFlag",true);
        Page<ArticleVo> voPage = articleAdminFacade.findArticlePage(page,filter);

        if(filter.get("cancelAndRefuseStatus") != null) {
            //取消和拒绝的状态
            filter.put("status","-1");
            filter.remove("cancelAndRefuseStatus");
        }

        this.pageInfoByMap(uiModel,voPage,filter);
        uiModel.addAttribute("page",voPage);
        uiModel.addAttribute("articleChannelMap", ArticleChannel.getChannelMap());
        Map<String,String> statusMap =  ArticleStatus.getStatusMap();
        statusMap.remove("-3");
        uiModel.addAttribute("articleStatusMap", statusMap);
        uiModel.addAttribute("articleTypeMap", ArticleTypeEnum.getAdTypeMap());
        uiModel.addAttribute("partyClassMap", IPartyType.partyClassMap);

        if(ajax != null) {
            return "/admin/article/ad/listArticle_ajax";
        }
        return "/admin/article/ad/listArticle.html";
    }

    /**
     * 创建或更新推广
     * @param id
     * @param uiModel
     * @return
     * @throws Exception
     */
    @RequestMapping("/create/createArticle.action")
    public String createArticle(@RequestParam(required = false) Long id, Model uiModel) throws Exception{
        Article article = new Article();
        ArticleCountInfo countInfo = new ArticleCountInfo();
        if(id != null) {
            article = articleFacade.getByArticleId(id);
            countInfo = articleFacade.getCountInfoByArticleId(id);

            uiModel.addAttribute("typeName", ArticleTypeEnum.getNameByType(article.getType()));
        }
        uiModel.addAttribute("art",article);
        uiModel.addAttribute("countInfo",countInfo);
        Map<String,String> typeMap = ArticleTypeEnum.getAdTypeMap();
        typeMap.remove("0");
        uiModel.addAttribute("articleTypeMap", typeMap);

        Map<String,Object> channel = new HashMap<>();
        channel.put("channelType", ArticleChannel.AD.getType());

        uiModel.addAttribute("articleTag",articleFacade.findArticleTagList(channel,id));

        return "/admin/article/ad/createOrUpdateArticle.html";
    }

    /**
     * 修改或更新文章操作
     * @param articleVo
     * @param response
     */
    @RequestMapping("/create/createOrUpdateArticle.action")
    public void createOrUpdateArticle(ArticleVo articleVo,HttpServletResponse response) throws Exception{
        //新增广告
        if(articleVo.getId() == null) {
            articleVo.setCreatePartyId(getCurrentUserPartyId());
            articleVo.setCreateUserId(getCurrentUserId());

            Integer type = articleVo.getType();
            Integer channelType = articleVo.getChannelType();

            if(channelType.equals(ArticleChannel.VIDEO.getType())
                    ||channelType.equals(ArticleChannel.COURSE.getType())
                    ||type.equals(ArticleTypeEnum.VIDEO.getType())){

                articleVo.setDisplayType(4);//视频展示
            }else if(channelType.equals(ArticleChannel.AD.getType())&&type.equals(ArticleTypeEnum.MULTIGAR.getType())){
                articleVo.setDisplayType(3);//多图展示
            }else if(channelType.equals(ArticleChannel.AD.getType())&&type.equals(ArticleTypeEnum.SINGLE.getType())){
                articleVo.setDisplayType(2);//单图大图展示
            }else{
                articleVo.setDisplayType(1);//单图小图展示
            }
            articleVo.setaIsAd(1);
            articleVo.setaIsTop(0);
            int num = articleFacade.addArticle(articleVo);
            if(num > 0) {
                responseJson(response,"ok","新增成功");
            }else {
                responseJson(response,"err","新增失败");
            }

        }else {
            //修改广告
            articleVo.setUpdatePartyId(getCurrentUserPartyId());
            articleVo.setUpdateUserId(getCurrentUserId());
            int num = articleFacade.updateArticle(articleVo);
            if(num > 0) {
                responseJson(response,"ok","修改成功");
            }else {
                responseJson(response,"err","修改失败");
            }

        }
    }

    /**
     * 发布广告
     */
    @RequestMapping("/update/publishAd.action")
    public void publishAd(Long id, HttpServletResponse response) throws Exception{

        if(!articleFacade.checkPublish5Num(ArticleChannel.AD.getType(),getCurrentUserId(),id)) {
            responseJson(response,"err","每天只能发布5篇广告");
            return;
        }

        if(articleFacade.checkPublishDate(id)) {
            responseJson(response,"err","截止日期应大于发布时期");
            return;
        }

        ArticleVo articleVo = articleFacade.getByArticleVoId(id);
        if(articleVo.getaIsTask().intValue() == 1) {
            if(!accountService.isFull(getCurrentUserId(),articleVo.getAllCost())) {
                responseJson(response,"err","账户余额不足，发布失败");
                return;
            }
        }

        //int num = articleFacade.publishArticle(id);
        int num = articleAdminFacade.updateAuditArticle(id,1,"");

        if(num > 0) {
            responseJson(response,"ok","发布成功");
        }else {
            responseJson(response,"err","发布失败");
        }

    }

    /**
     * 资讯详情，预览页面
     * @param id
     * @param uiModel
     * @return
     * @throws Exception
     */
    @RequestMapping("/list/getAdDetail.action")
    public String getAdDetail(@RequestParam(required = true)Long id,Model uiModel) throws Exception{

        Article article = articleFacade.getByArticleId(id);
        ArticleCountInfo countInfo = articleFacade.getCountInfoByArticleId(id);

        //统计信息
        Map<String,Object> countMap = articleFacade.countArticleClick(article,countInfo);
        uiModel.addAttribute("allClickNum",countMap.get("allClickNum"));
        uiModel.addAttribute("allUsedMoney",countMap.get("allUsedMoney"));
        uiModel.addAttribute("refundMoney",countMap.get("refundMoney"));

        uiModel.addAttribute("typeName", ArticleTypeEnum.getNameByType(article.getType()));
        uiModel.addAttribute("art",article);
        uiModel.addAttribute("countInfo",countInfo);

        return "/admin/article/ad/previewArticleDetail.html";
    }



}
