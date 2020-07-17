package com.autozi.cheke.trigger.article;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.article.entity.ArticleCountInfo;
import com.autozi.cheke.article.entity.ArticleOrder;
import com.autozi.cheke.article.entity.ArticleShare;
import com.autozi.cheke.article.type.ArticleOrderStatus;
import com.autozi.cheke.article.type.ArticleStatus;
import com.autozi.cheke.service.article.IArticleCountInfoService;
import com.autozi.cheke.service.article.IArticleOrderService;
import com.autozi.cheke.service.article.IArticleService;
import com.autozi.cheke.service.article.IArticleShareService;
import com.autozi.cheke.service.search.IArticleSearchService;
import com.autozi.cheke.service.settle.IAccountService;
import com.autozi.cheke.trigger.facade.ArticleRedisTaskFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by wang-lei on 2017/12/20.
 */
public class ArticleCountInfoTrigger {

    @Autowired
    private IArticleCountInfoService articleCountInfoService;

    @Autowired
    private IArticleOrderService articleOrderService;

    @Autowired
    private IArticleShareService articleShareService;

    @Autowired
    private ArticleRedisTaskFacade articleRedisTaskFacade;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IArticleSearchService articleSearchService;


    public void syncCountInfoData() {
        //更新count_info，此处多线程处理有问题
        Set<String> countSet = articleRedisTaskFacade.getTaskArticleCountInfo();
        if(countSet != null) {
            for(Iterator<String> it = countSet.iterator(); it.hasNext();) {
                ArticleCountInfo articleCountInfo = articleRedisTaskFacade.getTaskValue(it.next());
                if(articleCountInfo!=null){
                    System.out.println("============syncCountInfoData=============="+articleCountInfo.getId());
                }else{
                    System.out.println("============syncCountInfoData============== 空空如也！！！");
                }

                int i = articleCountInfoService.updateCountInfo(articleCountInfo);

                if(i >= 1) {
                    it.remove();
                    articleRedisTaskFacade.saveTaskArticleCountInfo(countSet);
                }
            }
        }
    }

    public void syncOrderData() {
        //更新order
        Set<String> orderSet = articleRedisTaskFacade.getTaskArticleOrder();
        if(orderSet != null) {
            for(Iterator<String> it = orderSet.iterator(); it.hasNext();) {
                ArticleOrder order = articleRedisTaskFacade.getTaskValue(it.next());


                Double mySelfMoney = order.getMyselfMoney();
                Double outMoney = order.getOutMoney();

                Double syncMySelfMoney = order.getSyncMyselfMoney();
                Double syncOutMoney = order.getSyncOutMoney();

                order.setSyncMyselfMoney((mySelfMoney - syncMySelfMoney) + syncMySelfMoney);
                order.setSyncOutMoney((outMoney - syncOutMoney) + syncOutMoney);
                articleRedisTaskFacade.afterGetTask(order);

                accountService.saveTaskRewardMoney(order.getUserId(),(mySelfMoney - syncMySelfMoney),order.getId());
                if(order.getTopTuikeId() != null) {
                    accountService.saveTaskPercentMoney(order.getTopTuikeId(),(outMoney - syncOutMoney),order.getId());
                }

                int i = articleOrderService.updateArticleOrder(order);
                if(i >= 1) {
                    it.remove();
                    articleRedisTaskFacade.saveTaskArticleOrder(orderSet);
                }
            }
        }

    }

    public void syncShareData() {
        //更新share
        Set<String> shareSet = articleRedisTaskFacade.getTaskArticleShare();
        if(shareSet != null) {
            for(Iterator<String> it = shareSet.iterator(); it.hasNext();) {
                ArticleShare share = articleRedisTaskFacade.getTaskValue(it.next());
                int i = articleShareService.updateArticleShare(share);

                if(i >= 1) {
                    it.remove();
                    articleRedisTaskFacade.saveTaskArticleShare(shareSet);
                }

            }
        }

    }

    /**
     * 根据活动结束日期，自动下线文章
     */
    public void autoOfflineArticle() {
        List<Article> list = articleService.findListForEndTimeTrigger();
        for(Article article : list) {
            article.setStatus(ArticleStatus.OFFLINE.getType());
            article.setOfflineTime(new Date());
            article.setAutoEndTime(new Date());
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
            List<ArticleOrder> listOrder = articleOrderService.getOrderByArticleId(article.getId());
            for(ArticleOrder order : listOrder) {
                order.setStatus(ArticleOrderStatus.ENDING.getType());
                order.setUpdateTime(new Date());
                articleOrderService.updateArticleOrder(order);

                ArticleOrder redisOrder = articleRedisTaskFacade.getOrder(order.getUserId(),article.getId());
                redisOrder.setStatus(ArticleOrderStatus.ENDING.getType());
                redisOrder.setUpdateTime(new Date());
                articleRedisTaskFacade.afterGetTask(redisOrder);

            }

            articleSearchService.deleteTitle(article.getId());

            articleRedisTaskFacade.offLineRedisArticle(article.getId());
            articleService.updateArticle(article);
        }

    }

}
