package com.autozi.cheke.web.article.controller;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.article.entity.ArticleCountInfo;
import com.autozi.cheke.article.type.ArticleChannel;
import com.autozi.cheke.article.type.ArticleTypeEnum;
import com.autozi.cheke.service.settle.IAccountService;
import com.autozi.cheke.util.mvc.BaseController;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wang-lei on 2017/11/28.
 */
@Controller
@RequestMapping("/project/yxpt/")
public class ProjectController extends BaseController{

    @Autowired
    private ArticleFacade articleFacade;

    @Autowired
    private IAccountService accountService;


    @RequestMapping("/list/listArticle.action")
    public String listArticle(Model uiModel,HttpServletRequest request,String ajax) throws Exception{
        Map<String,Object> filter = buildFilter(request,uiModel);
        Page<Article> page = buildPage(request);

        //第一次进入默认是待发布
        if(filter.get("status") == null) {
            filter.put("status",0);
        }

        if(Integer.valueOf(filter.get("status").toString()).intValue() == 0) {
            filter.remove("status");
            filter.put("waitAndRefuseStatus",true);
        }

        filter.put("channelType", ArticleChannel.PROJECT.getType());
        filter.put("createUserId",getCurrentUserId());
        Page<ArticleVo> voPage = articleFacade.findArticlePage(page,filter);

        this.pageInfoByMap(uiModel,voPage,filter);
        uiModel.addAttribute("page",voPage);
        uiModel.addAttribute("articleTypeMap", ArticleTypeEnum.getTypeMap());
        uiModel.addAttribute("status",filter.get("status") == null ? "0" : filter.get("status").toString());

        if(ajax != null) {
            return "/article/project/listArticle_ajax";
        }
        return "/article/project/listArticle.html";
    }



    /**
     * 创建或更新资讯
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
        Map<String,String> typeMap = ArticleTypeEnum.getTypeMap();
        typeMap.remove("0");
        uiModel.addAttribute("articleTypeMap", typeMap);

        Map<String,Object> channel = new HashMap<>();
        channel.put("channelType", ArticleChannel.PROJECT.getType());
        uiModel.addAttribute("articleTag",articleFacade.findArticleTagList(channel,id));

        return "/article/project/createOrUpdateArticle.html";
    }


    /**
     * 资讯详情，预览页面
     * @param id
     * @param uiModel
     * @return
     * @throws Exception
     */
    @RequestMapping("/list/getArticleDetail.action")
    public String getArticleDetail(@RequestParam(required = true)Long id,Model uiModel) throws Exception{

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

        return "/article/project/previewArticleDetail.html";
    }


    /**
     * 修改或更新文章操作
     * @param articleVo
     * @param response
     */
    @RequestMapping("/create/createOrUpdateArticle.action")
    public void createOrUpdateArticle(ArticleVo articleVo,HttpServletResponse response) throws Exception{
        //新增
        if(articleVo.getId() == null) {
            articleVo.setCreatePartyId(getCurrentUserPartyId());
            articleVo.setCreateUserId(getCurrentUserId());

            int num = articleFacade.addArticle(articleVo);
            if(num > 0) {
                responseJson(response,"ok","新增成功");
            }else {
                responseJson(response,"err","新增失败");
            }

        }else {
            //修改
            articleVo.setUpdateUserId(getCurrentUserId());
            articleVo.setUpdatePartyId(getCurrentUserPartyId());
            int num = articleFacade.updateArticle(articleVo);
            if(num > 0) {
                responseJson(response,"ok","修改成功");
            }else {
                responseJson(response,"err","修改失败");
            }

        }
    }


    @RequestMapping("/update/publishArticle.action")
    public void publishArticle(Long id, HttpServletResponse response) throws Exception{

        if(!articleFacade.checkPublish5Num(ArticleChannel.PROJECT.getType(),getCurrentUserId(),id)) {
            responseJson(response,"err","每天只能发布5篇项目");
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

        int num = articleFacade.publishArticle(id);

        if(num > 0) {
            responseJson(response,"ok","发布成功");
        }else {
            responseJson(response,"err","发布失败");
        }

    }

    @RequestMapping("/update/cancelPublishArticle.action")
    public void cancelPublishArticle(Long id,HttpServletResponse response) throws Exception{
        int num = articleFacade.cancelPublish(id);

        if(num > 0) {
            responseJson(response,"ok","取消成功");
        }else {
            responseJson(response,"err","取消失败");
        }
    }

    @RequestMapping("/update/offLineArticle.action")
    public void offLineArticle(Long id,HttpServletResponse response) throws Exception{
        int num = articleFacade.offLineArticle(id);
        if(num > 0) {
            responseJson(response,"ok","下线成功");
        }else {
            responseJson(response,"err","下线失败");
        }
    }


    @RequestMapping("/update/copyArticle.action")
    public void copyArticle(Long id,HttpServletResponse response) throws Exception{
        int num = articleFacade.copyArticle(id);
        if(num > 0) {
            responseJson(response,"ok","复制成功");
        }else {
            responseJson(response,"err","复制失败");
        }

    }

    @RequestMapping("/update/batchPublishArticle.action")
    public void batchPublishArticle(String articleIds,HttpServletResponse response) throws Exception{
        List<String> listIds = Arrays.asList(articleIds.split(","));

        if(listIds.size() > 5) {
            responseJson(response,"err","每次只能发布5篇项目");
            return;
        }

        if(!articleFacade.checkBatchPublish5Num(ArticleChannel.PROJECT.getType(),listIds.size(),getCurrentUserId(),listIds)) {
            responseJson(response,"err","每天只能发布5篇已发布过的项目");
            return;
        }

        if(articleFacade.checkBatchPublishDate(listIds)) {
            responseJson(response,"err","截止日期应大于发布时期");
            return;
        }

        if(!accountService.isFull(getCurrentUserId(),articleFacade.getBatchMoney(listIds))) {
            responseJson(response,"err","账户余额不足，请减少发布条数");
            return;
        }

        articleFacade.batchPublish(listIds);
        responseJson(response,"ok","批量发布成功");
    }


    @RequestMapping("/update/batchCancelArticle.action")
    public void batchCancelArticle(String articleIds,HttpServletResponse response) throws Exception{
        List<String> listIds = Arrays.asList(articleIds.split(","));
        articleFacade.batchCancel(listIds);
        responseJson(response,"ok","批量取消发布成功");
    }


    @RequestMapping("/update/batchOfflineArticle.action")
    public void batchOfflineArticle(String articleIds,HttpServletResponse response) throws Exception{
        List<String> listIds = Arrays.asList(articleIds.split(","));
        articleFacade.batchOffline(listIds);
        responseJson(response,"ok","批量下线成功");
    }

    @RequestMapping("/update/batchCopyArticle.action")
    public void batchCopyArticle(String articleIds,HttpServletResponse response) throws Exception{
        List<String> listIds = Arrays.asList(articleIds.split(","));
        articleFacade.batchCopy(listIds);
        responseJson(response,"ok","批量复制成功");
    }



}
