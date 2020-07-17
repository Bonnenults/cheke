package com.autozi.cheke.web.article.facade;

import com.autozi.cheke.article.entity.*;
import com.autozi.cheke.article.type.ArticleChannel;
import com.autozi.cheke.article.type.ArticleOrderStatus;
import com.autozi.cheke.article.type.ArticleStatus;
import com.autozi.cheke.article.type.ArticleTypeEnum;
import com.autozi.cheke.service.article.*;
import com.autozi.cheke.service.search.IArticleSearchService;
import com.autozi.cheke.web.article.vo.ArticleTagVo;
import com.autozi.cheke.web.article.vo.ArticleVo;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.o2o.PageUtil;
import com.autozi.common.utils.util1.HtmlUtils;
import com.autozi.common.utils.util1.RandomUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wang-lei on 2017/11/29.
 */
@Component
public class ArticleFacade {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IArticleCountInfoService articleCountInfoService;

    @Autowired
    private IArticleTagService articleTagService;

    @Autowired
    private IArticleTagRelationService articleTagRelationService;

    @Autowired
    private IArticleOrderService articleOrderService;

    @Autowired
    private IArticleShareService articleShareService;

    @Autowired
    private ArticleRedisFacade articleRedisFacade;

    @Autowired
    private IArticleSearchService articleSearchService;



    public Page<ArticleVo> findArticlePage(Page<Article> page,Map<String,Object> filter) throws Exception{
        return convertArticleVo(articleService.findArticlePage(page,filter));
    }

    public Article getByArticleId(Long id) throws Exception {
        Article article = articleService.getArticleById(id);
        if (article != null && StringUtils.isNotBlank(article.getBody())) {
            article.setBody(HtmlUtils.readHtml( article.getBody()));
        }
        return article;
    }

    public ArticleCountInfo getCountInfoByArticleId(Long articleId) {
        return articleCountInfoService.getCountInfoByArticleId(articleId);
    }

    public Page<ArticleVo> findQueryArticlePage(Page<Article> page,Map<String,Object> filter) throws Exception{
        return convertQueryArticleVo(articleService.findArticlePage(page,filter));
    }

    public int countArticleByUserId(Long userId) {
        return articleService.countArticleByCurrentUserId(userId);
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


    /**
     * 查询文章关联的tag标签
     * @param articleId
     * @return
     */
    public List<ArticleTagRelation> findArticleTagRelationList(Long articleId) {
        Map<String,Object> filter = new HashMap<>();
        filter.put("articleId",articleId);
        return articleTagRelationService.findRelationList(filter);
    }

    /**
     * 获取文章标签列表
     * @param filter
     * @return
     */
    public List<ArticleTagVo> findArticleTagList(Map<String,Object> filter, Long articleId) {
        List<ArticleTagVo> voList = new ArrayList<>();

        List<ArticleTag> allTag =  articleTagService.findArticleTagList(filter);
        List<ArticleTagRelation> relations = findArticleTagRelationList(articleId == null ? -1000L : articleId);

        for(ArticleTag at : allTag) {
            ArticleTagVo vo = new ArticleTagVo();
            BeanUtils.copyProperties(at,vo);
            for(ArticleTagRelation re : relations) {
                if(re.getArticleTagId().longValue() == at.getId().longValue()) {
                    vo.setSelectFlag(1);
                }
            }
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 批量发布资讯文章
     * @param articleIds
     */
    public void batchPublish(List<String> articleIds) {
        articleService.batchPublish(articleIds);
    }

    /**
     * 批量取消发布资讯文章
     * @param articleIds
     */
    public void batchCancel(List<String> articleIds) {
        articleService.batchCancel(articleIds);
    }

    /**
     * 批量下线
     * @param articleIds
     */
    public void batchOffline(List<String> articleIds) {

        for(String articleId : articleIds) {
            articleRedisFacade.offLineRedisArticle(Long.parseLong(articleId));

            //所有领取的任务都下线
            List<ArticleOrder> listOrder = articleOrderService.getOrderByArticleId(Long.parseLong(articleId));
            for(ArticleOrder order : listOrder) {
                order.setStatus(ArticleOrderStatus.ENDING.getType());
                order.setUpdateTime(new Date());
                articleOrderService.updateArticleOrder(order);

                ArticleOrder redisOrder = articleRedisFacade.getOrder(order.getUserId(),Long.parseLong(articleId));
                redisOrder.setStatus(ArticleOrderStatus.ENDING.getType());
                redisOrder.setUpdateTime(new Date());
                articleRedisFacade.afterGetTask(redisOrder);
            }

            articleSearchService.deleteTitle(Long.parseLong(articleId));
        }

        articleService.batchOffline(articleIds);
    }

    /**
     * 批量导入待发布
     * @param articleIds
     */
    public void batchCopy(List<String> articleIds) {
        for(String articleId : articleIds) {
            copyArticle(Long.parseLong(articleId));
        }
    }




    /**
     * 更新资讯、项目时，更新统计表中的钱数
     * @param articleVo
     * @return
     */
    public int updateArticle(ArticleVo articleVo) throws IOException {
        Article article = new Article();
        //组装分类名称,返回文章分类的ID
        List<String> tagIdList = Arrays.asList(articleVo.getTag().split(","));
        Calendar calendar  =Calendar.getInstance();
        String oldHtmlStr = null;
        if (articleVo.getId() != null) {
            Article oldArticle = articleService.getArticleById(articleVo.getId());
            oldHtmlStr = oldArticle.getBody();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String fullPath ="/article/"+sdf.format(new Date())+"/";
        String fileName =+ System.currentTimeMillis() + RandomUtil.CreateNoncestr(4) + ".html";
        HtmlUtils.createHtmlFile(articleVo.getBody(), fullPath,fileName);
        articleVo.setBody(fullPath+fileName);
        BeanUtils.copyProperties(articleVo,article);
        articleService.updateArticleNotPublic(article,tagIdList,articleVo.getAllCost(),articleVo.getOnceCost(),articleVo.getMostCost());
        if (StringUtils.isNotBlank(oldHtmlStr)) {
            HtmlUtils.deleteFile(oldHtmlStr, "");
        }
        return 1;
    }

    /**
     * 新增资讯、项目时，初始化统计表
     * @param articleVo
     * @return
     */
    public int addArticle(ArticleVo articleVo) throws IOException {
        Article article = new Article();
        //组装分类名称
        List<String> tagIdList = Arrays.asList(articleVo.getTag().split(","));
        Calendar calendar  =Calendar.getInstance();
        String fullPath = "/article/" + calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DAY_OF_YEAR) + "/" ;
        String fileName =+ System.currentTimeMillis() + RandomUtil.CreateNoncestr(4) + ".html";
        HtmlUtils.createHtmlFile(articleVo.getBody(), fullPath,fileName);
        articleVo.setBody(fullPath+fileName);
        BeanUtils.copyProperties(articleVo,article);
        int result = articleService.addArticleReturnId(article,tagIdList,articleVo.getAllCost(),articleVo.getOnceCost(),articleVo.getMostCost());
        return  result;
    }

    public int publishArticle(Long articleId) {
        return articleService.publishArticle(articleId);
    }

    public int cancelPublish(Long articleId) {
        return articleService.cancelPublish(articleId);
    }

    public int offLineArticle(Long articleId) {
        articleRedisFacade.offLineRedisArticle(articleId);

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

        return articleService.offLineArticle(articleId);
    }

    public int copyArticle(Long articleId) {
        return articleService.copyArticle(articleId);
    }


    /**
     * 拼装Article
     * @param articlePage
     * @return
     */
    private Page<ArticleVo> convertArticleVo(Page<Article> articlePage) {
        Page<ArticleVo> voPage = new Page<>();
        List<ArticleVo> voList = new ArrayList<>();

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

            voList.add(vo);
        }

        PageUtil.convertPage(articlePage,voPage,voList);
        return voPage;
    }


    private Page<ArticleVo> convertQueryArticleVo(Page<Article> articlePage) {
        Page<ArticleVo> voPage = new Page<>();
        List<ArticleVo> voList = new ArrayList<>();

        for(Article ac : articlePage.getResult()) {
            ArticleVo vo = new ArticleVo();
            BeanUtils.copyProperties(ac,vo);

            //查询费用
            ArticleCountInfo countInfo = articleCountInfoService.getCountInfoByArticleId(vo.getId());

            if(countInfo != null) {
                vo.setAllCost(countInfo.getAllCost());
                vo.setUsedCost(countInfo.getUsedCost());
                vo.setOnceCost(countInfo.getOnceCost());
                vo.setTwitter(countInfo.getTwitter());
                vo.setLeaveWords(countInfo.getLeaveWords());
            }

            vo.setTypeName(ArticleTypeEnum.getNameByType(ac.getType()));
            vo.setChannelName(ArticleChannel.getChannelName(ac.getChannelType()));
            vo.setStatusName(ArticleStatus.getNameByType(ac.getStatus()));
            voList.add(vo);
        }

        PageUtil.convertPage(articlePage,voPage,voList);
        return voPage;
    }

    public ArticleVo getByArticleVoId(Long id) throws Exception {
        ArticleVo articleVo = new ArticleVo();
        Article article = articleService.getArticleById(id);
        BeanUtils.copyProperties(article,articleVo);
        if(StringUtils.isNotBlank(article.getBody())){
            articleVo.setBody(HtmlUtils.readHtml(article.getBody()));
        }

        ArticleCountInfo countInfo = articleCountInfoService.getCountInfoByArticleId(id);
        articleVo.setAllCost(countInfo.getAllCost());

        return articleVo;
    }

    public List<ArticleVo> getTaskList(Map<String,Object> filter) throws Exception{
        List<Article> list = articleService.findListForMap(filter);
        List<ArticleVo> voList = new ArrayList<>();
        for (int i = 0; i < list.size() && i<10; i++) {
            Article article = list.get(i);
            Long articleId = article.getId();
            ArticleCountInfo countInfo = articleCountInfoService.getCountInfoByArticleId(articleId);
            if(countInfo==null){
                continue;
            }
            Integer count = articleOrderService.getTaskCount(articleId);
            Double allCost = countInfo.getAllCost();
            Double usedCost = countInfo.getUsedCost();
            Double cost = allCost-usedCost;
            ArticleVo vo = new ArticleVo();
            vo.setId(articleId);
            vo.setTitle(article.getTitle());
            String publishTime="";
            publishTime=new SimpleDateFormat("yyyy-MM-dd").format(article.getPublishTime());
            vo.setPublishTimeStr(publishTime);
//            String beginTime = "";
//            if(article.getEndTime()==null){
//                beginTime = "永久";
//            }else{
//                beginTime = new SimpleDateFormat("yyyy-MM-dd").format(article.getBeginTime());
//            }
//            vo.setBeginTimeStr(beginTime);
            vo.setAllCost(allCost);
            vo.setUsedCost(usedCost);
            vo.setCost(cost);
            vo.setShareAmount(count==null?0:count);//领取个数
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 一天只能发5条资讯、项目、培训
     * @param channelType
     * @return
     */
    public boolean checkPublish5Num(Integer channelType,Long createUserId,Long articleId) {
        boolean flag = true;
        int num = articleService.countDayArticleNum(channelType,createUserId,articleId);
        if(num >= 5) {
            flag = false;
        }
        return flag;
    }

    /**
     * 批量发布时，校验是否超过5个
     * @param channelType
     * @param articleSize
     * @return
     */
    public boolean checkBatchPublish5Num(Integer channelType,Integer articleSize,Long createUserId,List<String> listIds) {
        boolean flag = true;

        List<Long> paids =  articleService.countDayArticleId(channelType,createUserId);

        int noLike = 0;
        for(String aid : listIds) {
            if(!paids.contains(Long.parseLong(aid))) {
                noLike ++;
            }
        }

        for(String articleId : listIds) {
            int num = articleService.countDayArticleNum(channelType,createUserId,Long.parseLong(articleId));

            if(num + noLike > 5) {
                flag = false;
                return flag;
            }

            if(num >= 5) {
                flag = false;
                return flag;
            }
        }
        return flag;
    }


    /**
     * 截止日期应该要大于当前日期
     * @param articleId
     * @return
     */
    public boolean checkPublishDate(Long articleId) {
        Date nowTime = new Date();
        Article article = articleService.getArticleById(articleId);

        if(article.getEndTime() == null) {
            return false;
        }

        if(nowTime.getTime() > article.getEndTime().getTime()) {
            return true;
        }

        return false;
    }


    public boolean checkBatchPublishDate(List<String> articleIds) {
        boolean flag = false;
        Date nowTime = new Date();
        for(String articleId : articleIds) {
            Article article = articleService.getArticleById(Long.parseLong(articleId));

            if(article.getEndTime() == null) {
                continue;
            }

            if(nowTime.getTime() > article.getEndTime().getTime()) {
                return true;
            }

        }

        return flag;
    }


    public double getBatchMoney(List<String> articleIds) throws Exception{
        double allMoney = 0.0;
        for(String articleId : articleIds) {
            ArticleVo articleVo = getByArticleVoId(Long.parseLong(articleId));

            if(articleVo.getaIsTask() == 1) {
                allMoney += articleVo.getAllCost();
            }

        }
        return allMoney;
    }

}
