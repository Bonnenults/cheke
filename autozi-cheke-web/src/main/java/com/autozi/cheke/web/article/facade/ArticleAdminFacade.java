package com.autozi.cheke.web.article.facade;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.article.entity.ArticleCountInfo;
import com.autozi.cheke.article.entity.ArticleOrder;
import com.autozi.cheke.article.entity.ArticleTag;
import com.autozi.cheke.article.type.ArticleChannel;
import com.autozi.cheke.article.type.ArticleOrderStatus;
import com.autozi.cheke.article.type.ArticleStatus;
import com.autozi.cheke.article.type.ArticleTypeEnum;
import com.autozi.cheke.basic.entity.Properties;
import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.party.type.IPartyType;
import com.autozi.cheke.service.article.*;
import com.autozi.cheke.service.basic.IPropertiesService;
import com.autozi.cheke.service.party.IPartyService;
import com.autozi.cheke.service.search.IArticleSearchService;
import com.autozi.cheke.service.settle.IAccountService;
import com.autozi.cheke.web.article.vo.ArticleVo;
import com.autozi.cheke.web.basic.facade.PropertiesRedisFacade;
import com.autozi.common.core.page.Page;
import com.autozi.common.search.es.entity.SearchKeyWords;
import com.autozi.common.utils.o2o.PageUtil;
import com.autozi.common.utils.util1.HtmlUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by wang-lei on 2017/11/29.
 */
@Component
public class ArticleAdminFacade {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IArticleCountInfoService articleCountInfoService;

    @Autowired
    private IArticleTagService articleTagService;

    @Autowired
    private IPartyService partyService;

    @Autowired
    private IArticleOrderService articleOrderService;

    @Autowired
    private IArticleShareService articleShareService;

    @Autowired
    private ArticleRedisFacade articleRedisFacade;

    @Autowired
    private IArticleSearchService articleSearchService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IArticleTagRelationService articleTagRelationService;

    @Autowired
    private IPropertiesService propertiesService;

    @Autowired
    private PropertiesRedisFacade propertiesRedisFacade;


    public Page<ArticleVo> findArticlePage(Page<Article> page,Map<String,Object> filter) throws Exception{
        return convertArticleVo(articleService.findArticlePage(page,filter));
    }

    public Page<ArticleTag> findArticleTagPage(Page<ArticleTag> page,Map<String,Object> filter) {
        return articleTagService.findArticleTagPage(page,filter);
    }

    public int addArticleTag(ArticleTag articleTag) {
        return articleTagService.addArticleTag(articleTag);
    }

    public int updateArticleTag(ArticleTag articleTag) {
        return articleTagService.updateArticleTag(articleTag);
    }

    public int deleteArticleTag(Long articleTagId) {
        return articleTagService.deleteArticleTagById(articleTagId);
    }

    public boolean checkedIsRef(Long articleTagId) {
        return articleTagRelationService.checkedIsRef(articleTagId);
    }


    public Article getByArticleId(Long id) {
        return articleService.getArticleById(id);
    }

    public ArticleCountInfo getCountInfoByArticleId(Long articleId) {
        return articleCountInfoService.getCountInfoByArticleId(articleId);
    }

    public int updateArticle(Article article) {
        return articleService.updateArticle(article);
    }

    public int updateAuditArticle(Long id,Integer flag,String reason) {
        Integer num = 0;
        Article article = getByArticleId(id);

        //不通过
        if(flag == 0) {
            article.setStatus(ArticleStatus.REFUSE.getType());
            article.setRefuseReason(reason);
            num = updateArticle(article);

            if(article.getaIsTask().intValue() == 1) {
                //审核不通过时，退款到原账户
                ArticleCountInfo countInfo = articleCountInfoService.getCountInfoByArticleId(article.getId());
                accountService.refundCk(article.getCreateUserId(),countInfo.getAllCost(),article.getId());
            }



        }else if(flag == 1) {
            //通过
            article.setStatus(ArticleStatus.PUBLISH.getType());
            article.setPassTime(new Date());
            article.setPublishTime(new Date());
            num = updateArticle(article);

            try {
                if (StringUtils.isNotBlank(article.getBody())) {
                    article.setBody(HtmlUtils.readHtml(article.getBody()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //存入缓存
            articleRedisFacade.auditPassArticle(article);

            //添加到ES中
            SearchKeyWords keyWords = new SearchKeyWords();
            keyWords.setTitle(article.getTitle());
            keyWords.setBody(article.getBody());
            keyWords.setId(article.getId());
            articleSearchService.indexKeyWrods(keyWords);

        }
        return num;
    }

    /**
     * 下线文章
     * @param articleId
     * @return
     */
    public int offLineArticle(Long articleId) {
        Article article = getByArticleId(articleId);
        article.setStatus(ArticleStatus.OFFLINE.getType());
        article.setOfflineTime(new Date());
        //如果为课程，则将课程ID和num置为null
        if(article.getCourseId() != null && !"".equals(article.getCourseId())){
            article.setCourseId(null);
            article.setNum(null);
        }

        if(article.getaIsTask().intValue() == 1) {
            ArticleCountInfo countInfo = articleCountInfoService.getCountInfoByArticleId(article.getId());
            accountService.refundCk(article.getCreateUserId(),countInfo.getUsedCost(),article.getId());
        }

        //所有领取的任务都下线
        List<ArticleOrder> listOrder = articleOrderService.getOrderByArticleId(articleId);
        for(ArticleOrder order : listOrder) {
            order.setStatus(ArticleOrderStatus.ENDING.getType());
            order.setUpdateTime(new Date());
            articleOrderService.updateArticleOrder(order);

            ArticleOrder redisOrder = articleRedisFacade.getOrder(order.getUserId(),articleId);
            redisOrder.setStatus(ArticleOrderStatus.ENDING.getType());
            redisOrder.setUpdateTime(new Date());
            articleRedisFacade.afterGetTask(redisOrder);

        }

        articleSearchService.deleteTitle(articleId);

        //articleRedisFacade.offLineRedisArticle(articleId);
        return articleService.updateArticle(article);
    }

    /**
     * 设置文章排序
     * @param articleId
     * @return
     */
    public int sorteArticle(Long articleId,Integer a_is_top) {
        Article article = getByArticleId(articleId);
        article.setaIsTop(a_is_top);
        article.setUpdateTime(new Date());

        int flag = articleService.updateArticle(article);
        try {
            if (StringUtils.isNotBlank(article.getBody())) {
                article.setBody(HtmlUtils.readHtml(article.getBody()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        articleRedisFacade.updateArticle(article);

        return flag;
    }


    /**
     * 拼装Article
     * @param articlePage
     * @return
     */
    private Page<ArticleVo> convertArticleVo(Page<Article> articlePage) {
        Page<ArticleVo> voPage = new Page<>();
        List<ArticleVo> voList = new ArrayList<>();

        String COUNT_SORT = propertiesRedisFacade.getCountSort();
        if(COUNT_SORT == null) {
            Map<String, Object> filter = new HashedMap();
            filter.put("popKey", "COUNT_SORT");
            Properties properties = propertiesService.getProperties(filter);

            COUNT_SORT = properties==null ? "20" : properties.getValue();
            propertiesRedisFacade.saveCountSort(COUNT_SORT);
        }
        for(Article ac : articlePage.getResult()) {
            ArticleVo vo = new ArticleVo();
            BeanUtils.copyProperties(ac,vo);

            //查询浏览量、分享量
            ArticleCountInfo countInfo = articleCountInfoService.getCountInfoByArticleId(vo.getId());

            if(countInfo != null) {
                vo.setPageView(countInfo.getPageView());
                vo.setShareAmount(countInfo.getShareAmount());
            }
            vo.setTypeName(ArticleTypeEnum.getNameByType(ac.getType()));
            vo.setChannelName(ArticleChannel.getChannelName(ac.getChannelType()));
            vo.setStatusName(ArticleStatus.getNameByType(ac.getStatus()));
            if(ac.getaIsTop() != 0 && ac.getaIsTop() != null){
                int aIsTop = Math.abs(ac.getaIsTop()-Integer.valueOf(COUNT_SORT)-1);
                vo.setaIsTop(aIsTop);
            }

            //车客属性和公司名称
            Party party = partyService.getPartyById(vo.getCreatePartyId());

            if(party != null) {
                vo.setPartyClass(IPartyType.partyClassMap.get(party.getPartyClass() == null ? "" : party.getPartyClass().toString()));
                vo.setCompanyName(party.getCompanyName());
            }

            voList.add(vo);
        }

        PageUtil.convertPage(articlePage,voPage,voList);
        return voPage;
    }


    /**
     * 预览页面统计文章 总点击次数，总消费，退款金额（为任务时）
     * @param article
     * @param articleCountInfo
     * @return
     */
    public Map<String,Object> countArticleClick(Article article,ArticleCountInfo articleCountInfo) {
        Map<String,Object> result = new HashMap<>();

        if(article.getaIsTask().intValue() == 0) {
            //非任务推广，只统计点击次数
            result.put("allClickNum",articleShareService.countAllShareClickNum(article.getId()));
        }else if(article.getaIsTask().intValue() == 1) {
            //推广任务，统计总点击次数 和 消费、退款金额
            Integer allClickNum = articleOrderService.countAllClickNum(article.getId());

            if(articleCountInfo.getUsedCost() > 0) {
                //余额大于0说明还有剩余的钱，统计所有的次数
                double allUsedMoney = articleCountInfo.getAllCost() - articleCountInfo.getUsedCost();
                double refundMoney = articleCountInfo.getAllCost() - allUsedMoney;
                result.put("allClickNum",allClickNum);
                result.put("allUsedMoney",allUsedMoney);
                result.put("refundMoney",refundMoney);
            }else {
                //余额等于0时，说明钱已经消费完，消费金额=费用总投入
                double allUsedMoney = articleCountInfo.getAllCost();
                double refundMoney = 0;
                result.put("allClickNum",allClickNum);
                result.put("allUsedMoney",allUsedMoney);
                result.put("refundMoney",refundMoney);
            }

        }
        return result;
    }

    public int addArticleToCourse(Long articleId, Long courseId,Integer num){

        Map<String,Object> filter = new HashedMap();
        filter.put("courseId",courseId);
        filter.put("status",ArticleStatus.PUBLISH.getType());
        List<Article> list = articleService.findListForMap(filter);
        if(num == 0){
            if(list.size() > 0){
                num = list.size() + 1;
            }
        }
        for (Article ar : list) {
            if(ar.getNum() == num){
                return -2;
            }
        }
        Article article = articleService.getArticleById(articleId);
        article.setCourseId(courseId);
        article.setNum(num);

        int flag = articleService.updateArticle(article);
        try {
            if (StringUtils.isNotBlank(article.getBody())) {
                article.setBody(HtmlUtils.readHtml( article.getBody()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //存入缓存
        articleRedisFacade.auditPassArticle(article);

        return flag;
    }

    public int delArticleFromCourse(Long articleId){
        Article article = articleService.getArticleById(articleId);
        article.setCourseId(null);
        article.setNum(null);

        int flag = articleService.updateArticle(article);
        try {
            if (StringUtils.isNotBlank(article.getBody())) {
                article.setBody(HtmlUtils.readHtml( article.getBody()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //存入缓存
        articleRedisFacade.auditPassArticle(article);

        return flag;
    }


    public void batchAddArticleToCourse(List<String> articleIds, Long courseId){

        for (String id : articleIds) {
            Article article = articleService.getArticleById(Long.parseLong(id));
            article.setCourseId(courseId);
            //存入缓存
            articleRedisFacade.updateArticle(article);

            articleService.updateArticle(article);
        }

    }

    public JSONArray findSearchTitle(String title) {
        JSONArray array = new JSONArray();
        List<SearchKeyWords> list = articleSearchService.findByTitle(title);

        if(list.size() > 10) {
            for(int i = 0; i < 10; i++) {
                SearchKeyWords key = list.get(i);
                JSONObject o = new JSONObject();
                o.put("keyWord",key.getTitle());
                array.add(o);
            }
        }else {
            for(SearchKeyWords keyWords : list) {
                JSONObject o = new JSONObject();
                o.put("keyWord",keyWords.getTitle());
                array.add(o);
            }
        }
        return array;
    }

}
