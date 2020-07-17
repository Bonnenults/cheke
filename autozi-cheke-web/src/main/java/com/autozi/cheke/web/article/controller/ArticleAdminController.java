package com.autozi.cheke.web.article.controller;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.article.entity.ArticleCountInfo;
import com.autozi.cheke.article.entity.ArticleTag;
import com.autozi.cheke.article.entity.ArticleType;
import com.autozi.cheke.article.type.ArticleChannel;
import com.autozi.cheke.article.type.ArticleStatus;
import com.autozi.cheke.article.type.ArticleTypeEnum;
import com.autozi.cheke.party.type.IPartyType;
import com.autozi.cheke.service.basic.IPropertiesService;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.util.web.SensitivewordFilter;
import com.autozi.cheke.web.article.facade.ArticleAdminFacade;
import com.autozi.cheke.web.article.facade.ArticleRedisFacade;
import com.autozi.cheke.web.article.vo.ArticleVo;
import com.autozi.cheke.web.basic.facade.PropertiesRedisFacade;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.util1.HtmlUtils;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by wang-lei on 2017/11/28.
 */
@Controller
@RequestMapping("/article/admin/")
public class ArticleAdminController extends BaseController{

    @Autowired
    private ArticleAdminFacade articleAdminFacade;

    @Autowired
    private PropertiesRedisFacade propertiesRedisFacade;

    @Autowired
    private IPropertiesService propertiesService;

    @Autowired
    private ArticleRedisFacade articleRedisFacade;


    @RequestMapping("/list/listArticle.action")
    public String listArticle(Model uiModel,HttpServletRequest request,String ajax) throws Exception{
        Map<String,Object> filter = buildFilter(request,uiModel);
        Page<Article> page = buildPage(request);

        //第一次进入默认是审核中
        if(filter.get("status") == null) {
            filter.put("status",ArticleStatus.REVIEW.getType());
            uiModel.addAttribute("status",filter.get("status") == null ? "1" : filter.get("status").toString());
        }else {
            if(Integer.parseInt(filter.get("status").toString()) == -100) {
                uiModel.addAttribute("status",filter.get("status") == null ? "1" : filter.get("status").toString());
                //条件为全部
//                filter.remove("status");
            }else {
                uiModel.addAttribute("status",filter.get("status") == null ? "1" : filter.get("status").toString());

                if(Integer.parseInt(filter.get("allFilter") == null ? "0" : filter.get("allFilter").toString()) == 0) {
                    if(Integer.parseInt(filter.get("status").toString()) == -1) {
                        //取消和拒绝的状态
                        filter.remove("status");
                        filter.put("cancelAndRefuseStatus",true);
                    }
                }
            }
        }
        //不包含待发布的
        filter.put("notWaitingFlag",true);
        //不包含广告
        filter.put("notAdFlag",true);
        filter.put("orderBy", "concat(ar.A_IS_TOP,ar.PUBLISH_TIME) desc");
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
        uiModel.addAttribute("partyClassMap", IPartyType.partyClassMap);

        if(ajax != null) {
            return "/admin/article/listArticle_ajax";
        }
        return "/admin/article/listArticle.html";
    }



    /**
     * 审核页面
     * @param id
     * @param uiModel
     * @return
     * @throws Exception
     */
    @RequestMapping("/update/auditArticle.action")
    public String auditArticlePage(@RequestParam(required = true) Long id, Model uiModel) throws Exception{
        Article  article = articleAdminFacade.getByArticleId(id);
        try {
            if (StringUtils.isNotBlank(article.getBody())) {
                article.setBody(HtmlUtils.readHtml( article.getBody()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArticleCountInfo  countInfo = articleAdminFacade.getCountInfoByArticleId(id);

        //关键词过滤
        SensitivewordFilter filter = new SensitivewordFilter();
        Set<String> set = filter.getSensitiveWord(article.getBody(),1);
        String word = "无";
        if(set.size()>0){
            word = set.toString();
        }
        uiModel.addAttribute("illegalString",word);

        uiModel.addAttribute("typeName", ArticleTypeEnum.getNameByType(article.getType()));
        uiModel.addAttribute("art",article);
        uiModel.addAttribute("countInfo",countInfo);
        uiModel.addAttribute("channelName",ArticleChannel.getChannelName(article.getChannelType()));

        return "/admin/article/auditArticle.html";
    }


    /**
     * 审核操作
     * @param
     * @param response
     */
    @RequestMapping("/update/audit.action")
    public void auditArticle(Long articleId,Integer auditFlag,String refuseReason,
                                        HttpServletResponse response) throws Exception{
        //审核
        int num = articleAdminFacade.updateAuditArticle(articleId,auditFlag,refuseReason);
        if(num > 0) {
            responseJson(response,"ok","修改成功");
        }else {
            responseJson(response,"err","修改失败");
        }

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

        return "/admin/article/previewArticleDetail.html";
    }


    /**
     * 下线操作
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping("/update/offLineArticle.action")
    public void offLineArticle(Long id,HttpServletResponse response) throws Exception{
        int num = articleAdminFacade.offLineArticle(id);
        if(num > 0) {
            responseJson(response,"ok","下线成功");
        }else {
            responseJson(response,"err","下线失败");
        }
    }


    /**
     * 文章分类列表
     * @param request
     * @param uiModel
     * @param ajax
     * @return
     */
    @RequestMapping("/list/listArticleTag.action")
    public String listArticleTag(HttpServletRequest request,Model uiModel,String ajax) {
        Map<String,Object> filter = buildFilter(request,uiModel);
        Page<ArticleTag> page = buildPage(request);

        Page<ArticleTag> vPage = articleAdminFacade.findArticleTagPage(page,filter);


        this.pageInfoByMap(uiModel,vPage,filter);
        uiModel.addAttribute("articleChannelMap", ArticleChannel.getChannelMap());
        uiModel.addAttribute("page",vPage);

        if(ajax != null) {
            return "/admin/article/listArticleTag_ajax";
        }
        return "/admin/article/listArticleTag.html";
    }


    /**
     * 新增或修改文章分类
     * @param articleTag
     * @param response
     */
    @RequestMapping("/create/createOrUpdateArticleTag.action")
    public void createOrUpdateArticleTag(ArticleTag articleTag,HttpServletResponse response) throws Exception{
        //新增
        if(articleTag.getId() == null) {
            articleTag.setCreateTime(new Date());
            articleTag.setCreateUserId(getCurrentUserId());
            int num = articleAdminFacade.addArticleTag(articleTag);
            if(num > 0) {
                responseJson(response,"ok","新增成功");
            }else {
                responseJson(response,"err","新增失败");
            }

        }else {
            //修改
            articleTag.setUpdateTime(new Date());
            int num = articleAdminFacade.updateArticleTag(articleTag);
            if(num > 0) {
                responseJson(response,"ok","修改成功");
            }else {
                responseJson(response,"err","修改失败");
            }

        }
    }

    @RequestMapping("/delete/deleteArticleTag.action")
    public void deleteArticleTag(Long id,HttpServletResponse response) {

        if(articleAdminFacade.checkedIsRef(id)) {
            responseJson(response,"err","不能删除有引用的分类");
            return;
        }

        int num = articleAdminFacade.deleteArticleTag(id);
        if(num > 0) {
            responseJson(response,"ok","删除成功");
        }else {
            responseJson(response,"err","删除失败");
        }
    }


    /**
     * 排序操作
     * @param
     * @param response
     */
    @RequestMapping("/update/sort.action")
    public void sortArticle(Long articleId,Integer a_is_top,
                             HttpServletResponse response) throws Exception{

        String COUNT_SORT = propertiesRedisFacade.getCountSort();
        if(COUNT_SORT == null) {
            Map<String, Object> filter = new HashedMap();
            filter.put("popKey", "COUNT_SORT");
            com.autozi.cheke.basic.entity.Properties properties = propertiesService.getProperties(filter);

            COUNT_SORT = properties==null ? "20" : properties.getValue();
            propertiesRedisFacade.saveCountSort(COUNT_SORT);
        }

        if(a_is_top<0 || a_is_top>Integer.valueOf(COUNT_SORT)){
            responseJson(response,"err","仅限对前20篇文章进行排序");
            return;
        }
        if(a_is_top != 0){
            a_is_top = Math.abs(a_is_top - Integer.valueOf(COUNT_SORT) - 1);
/*          //判断序号是否重复
            Article article = articleAdminFacade.getByArticleId(articleId);
            if(article.getChannelType() == ArticleChannel.AD.getType()){

                Map<String, Object> map = new HashedMap();
                map.put("channelType", ArticleChannel.AD.getType());
                map.put("aIsTop",a_is_top);
                Article sortedArticle = articleAdminFacade.getArticleByFilter(map);
                if(sortedArticle != null){
                    Integer num= Math.abs(a_is_top - Integer.valueOf(COUNT_SORT) - 1);
                    responseJson(response,"err","序号重复："+article.getTitle()+"已设置为"+num);
                    return;
                }
            }*/

        }


        //更新
        int num = articleAdminFacade.sorteArticle( articleId, a_is_top) ;
        if(num > 0) {
            responseJson(response,"ok","修改成功");
        }else {
            responseJson(response,"err","修改失败");
        }

    }

    /**
     * test
     */

    @RequestMapping("/update/test.action")
    public void test(HttpServletRequest request,  HttpServletResponse response) throws Exception{
        String value=request.getParameter("value");
        setString("D:\\WorkSpace\\SVN\\trunk\\autozi-cheke-api\\src\\main\\resources\\application","space","4");

    }


    public static void setString(String resourceFile, String key, String value){

        Properties prop = new Properties();
        try {
            if(resourceFile.indexOf(".properties")==-1){
                resourceFile+=".properties";
            }
            FileInputStream fis = new FileInputStream(resourceFile);
            try {
                prop.load(fis);
                fis.close();
                prop.setProperty(key, value);
                FileOutputStream fos = new FileOutputStream(resourceFile);
                prop.store(fos, "Copyright Thcic");
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("修改资源文件："+resourceFile+"异常！msg："+e.getMessage());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("无法获得资源文件：" + resourceFile);
        }
    }

    @RequestMapping("/list/listKeyWords.action")
    public void listKeyWords(HttpServletRequest request,HttpServletResponse response,String keywords) {
        JSONObject data = new JSONObject();
        data.put("list", articleAdminFacade.findSearchTitle(keywords));
        response(response, "0000", "OK",data.toString());
    }

    @RequestMapping("/list/listRedisTask.action")
    public void listRedisTask(HttpServletRequest request,HttpServletResponse response){
        Set<String> countSet = articleRedisFacade.getTaskArticleCountInfo();
        if(countSet != null) {
            for(Iterator<String> it = countSet.iterator(); it.hasNext();) {
                ArticleCountInfo articleCountInfo = articleRedisFacade.getTaskValue(it.next());
                if(articleCountInfo!=null){
                    System.out.println("============syncCountInfoData=============="+articleCountInfo.getArticleId());
                }
            }
        }
    }

}
