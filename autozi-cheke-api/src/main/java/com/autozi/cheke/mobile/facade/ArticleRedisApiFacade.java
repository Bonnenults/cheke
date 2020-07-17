package com.autozi.cheke.mobile.facade;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.article.entity.ArticleCountInfo;
import com.autozi.cheke.article.entity.ArticleOrder;
import com.autozi.cheke.article.entity.ArticleShare;
import com.autozi.cheke.article.type.ArticleCommissionConstants;
import com.autozi.cheke.article.type.ArticleOrderStatus;
import com.autozi.cheke.article.type.ArticleRedisConstants;
import com.autozi.cheke.article.type.ArticleStatus;
import com.autozi.cheke.service.article.IArticleCountInfoService;
import com.autozi.cheke.service.article.IArticleOrderService;
import com.autozi.common.cache.jedis.proxy.RedisProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wang-lei on 2017/12/21.
 */
@Component
public class ArticleRedisApiFacade {


    @Autowired
    private IArticleCountInfoService articleCountInfoService;

    @Autowired
    private IArticleOrderService articleOrderService;

    @Autowired
    private RedisProxy redisProxy;

    /**
     * 资讯、项目、培训审核通过时，统计信息放到redis中
     * @param article
     */
    public void auditPassArticle(Article article) {
        ArticleCountInfo articleCountInfo = articleCountInfoService.getCountInfoByArticleId(article.getId());
        _saveArticleCountInfo(articleCountInfo);
        _saveArticle(article);
    }

    /**
     *重新组装视频课程body后保存到redis
     */
    public void saveArticleBody(Long articleId,String body) {
        Article article = _getArticle(articleId);
        if(article!=null){
            article.setBody(body);
            _saveArticle(article);
        }

    }

    /**
     * 领取任务后，把任务存到redis中
     */
    public void afterGetTask(ArticleOrder articleOrder) {
        _saveArticleOrder(articleOrder);
    }

    /**
     * 普通分享后，把分享信息放到redis中
     */
    public void afterNormalShare(ArticleShare articleShare) {
        _saveArticleShare(articleShare);
    }

    /**
     * 统计点赞、点Low、留言次数、分享到朋友圈、微信好友、QQ空间、QQ好友、微博
     * @param articleId
     */
    public void countArticleInfo(Integer type,Long articleId,boolean flag) {
        ArticleCountInfo info = _getArticleCountInfo(articleId);

        switch(type.intValue()) {
            case 1:
                info.setWxFriendsCircle(info.getWxFriendsCircle() + 1);
                _saveArticleCountInfo(info);
                break;
            case 2:
                info.setWxFriends(info.getWxFriends() + 1);
                _saveArticleCountInfo(info);
                break;
            case 3:
                info.setQqZone(info.getQqZone() + 1);
                _saveArticleCountInfo(info);
                break;
            case 4:
                info.setQqFriends(info.getQqFriends() + 1);
                _saveArticleCountInfo(info);
                break;
            case 5:
                info.setSina(info.getSina() + 1);
                _saveArticleCountInfo(info);
                break;
            case 10:
                info.setLeaveWords(info.getLeaveWords() + 1);
                _saveArticleCountInfo(info);
                break;
            case 11:
                //若该用户已点low，则点low数-1
                if(flag){
                    info.setDislikes((info.getDislikes() - 1)<0 ? 0 : (info.getDislikes() - 1));
                }
                info.setLikes(info.getLikes() + 1);
                _saveArticleCountInfo(info);
                break;
            case 12:
                //若该用户已点赞，则点赞数-1
                if(flag){
                    info.setLikes((info.getLikes() - 1) < 0 ? 0 : (info.getLikes() - 1));
                }
                info.setDislikes(info.getDislikes() + 1);
                _saveArticleCountInfo(info);
                break;
            case 13:
                info.setLikes((info.getLikes() - 1) < 0 ? 0 : (info.getLikes() - 1));
                _saveArticleCountInfo(info);
                break;
            case 14:
                info.setDislikes((info.getDislikes() - 1)<0 ? 0 : (info.getDislikes() - 1));
                _saveArticleCountInfo(info);
                break;
            default:
                break;
        }

    }

    /**
     * 统计文章的浏览量（每进入一次页面，算是一次访问量）
     * @param articleId
     */
    public void countPageView(Long articleId) {
        ArticleCountInfo info = _getArticleCountInfo(articleId);
        info.setPageView(info.getPageView() + 1);
        _saveArticleCountInfo(info);
    }

    /**
     * 统计课程类文章的学习中人数(+1)
     * @param articleId
     */
    public void countStuNum(Long articleId) {
        ArticleCountInfo info = _getArticleCountInfo(articleId);
        info.setStuNum(info.getStuNum() + 1);
        _saveArticleCountInfo(info);
    }


    /**
     * 统计课程类文章的学成人数
     * @param articleId
     */
    public void countCompletedNum(Long articleId) {
        ArticleCountInfo info = _getArticleCountInfo(articleId);
        info.setStuNum(info.getStuNum() - 1 <0 ? 0 : info.getStuNum() - 1);
        info.setCompletedNum(info.getCompletedNum() + 1);
        _saveArticleCountInfo(info);
    }

    /**
     * 统计文章的分享量（每转发一次，算是一次分享）
     * @param articleId
     */
    public void countShareAmount(Long articleId) {
        ArticleCountInfo info = _getArticleCountInfo(articleId);
        info.setShareAmount(info.getShareAmount() + 1);
        info.setTwitter(info.getTwitter() + 1);
        _saveArticleCountInfo(info);
    }


    /**
     * 统计用户，普通的分享点击次数
     * @param userId
     * @param articleId
     */
    public void countArticleShare(Long userId, Long articleId,Integer type) {
        ArticleShare share = _getArticleShare(userId,articleId,type);
        ArticleCountInfo countInfo = _getArticleCountInfo(articleId);
        if(share != null) {
            share.setClickNum(share.getClickNum() + 1);
            _saveArticleShare(share);
        }
        countInfo.setPageView(countInfo.getPageView() + 1);
        _saveArticleCountInfo(countInfo);
    }


    /**
     * 统计钱的逻辑
     * <>重要</>
     */
    private void countArticleOrderShare(Article article,Long articleId,Long userId,Integer type) {
        //1、文章的状态是上线的时候，才会统计钱
        if(article.getStatus().intValue() == ArticleStatus.PUBLISH.getType().intValue()) {
            ArticleOrder checkedOrder = articleOrderService.getOrderByArticleIdAndUserId(articleId, userId);
            //2、如果是推广任务（并且用户领取了任务），更新order点击量并计算获取佣金，更新share分享点击量、更新countInfo总点击量
            if (checkedOrder != null) {
                ArticleOrder order = _getArticleOrder(userId, articleId);
                ArticleCountInfo countInfo = _getArticleCountInfo(articleId);
                countOrderMoney(article,order,countInfo);
            } else {
                //用户没有领取任务，按普通分享一样
                countArticleShare(userId, articleId, type);
            }
        }else {
            //下线的文章也只按普通分享统计
            countArticleShare(userId, articleId, type);
            completeTask(articleId);
        }
    }

    /**
     * 判断文章活动是否永久有效，默认是永久有效
     * @param article
     * @return
     */
    private boolean isForeverArticle(Article article) {
        boolean flag = true;
        if(article.getEndTime() != null) {
            flag = false;
        }
        return flag;
    }

    private void countForeverArticleMoney(ArticleOrder order,ArticleCountInfo countInfo) {
        //有剩余的钱才会扣除
        if(countInfo.getUsedCost() > 0) {
            //领取的金额小于单个任务最大的金额才能领取佣金
            if ((order.getMyselfMoney() + order.getOutMoney()) < countInfo.getMostCost()) {
                countInfo.setUsedCost(countInfo.getUsedCost() - (countInfo.getOnceCost()));

                //4、判断是否订单是否有上级推客，如果有的话，计算给上级推客的钱
                if (order.getHasParentTwr() == 1) {
                    order.setMyselfMoney(order.getMyselfMoney() + (countInfo.getOnceCost() * ArticleCommissionConstants.MYSELF_COMMISSION));
                    order.setOutMoney(order.getOutMoney() + (countInfo.getOnceCost() * ArticleCommissionConstants.OUT_COMMISSION));
                } else {
                    order.setMyselfMoney(order.getMyselfMoney() + countInfo.getOnceCost());
                }

            }
        }
    }

    /**
     * 完成任务
     * @param articleId
     */
    private void completeTask(Long articleId) {
        List<ArticleOrder> listOrder = articleOrderService.getOrderByArticleId(articleId);
        for(ArticleOrder order : listOrder) {
            ArticleOrder redisOrder =  _getArticleOrder(order.getUserId(),order.getArticleId());
            redisOrder.setStatus(ArticleOrderStatus.ENDING.getType());
            redisOrder.setUpdateTime(new Date());
            _saveArticleOrder(redisOrder);
        }
    }

    /**
     * 有活动期限的文章统计
     * @param article
     * @param order
     * @param countInfo
     */
    private void countNoForeverArticleMoney(Article article,ArticleOrder order,ArticleCountInfo countInfo) {
        //有活动期限的
        //判断任务是否过期（两个条件，日期和剩余金额）
        Date currentDate = new Date();
        if(article.getBeginTime() != null && article.getEndTime() != null) {
            if (currentDate.getTime() >= article.getBeginTime().getTime() && currentDate.getTime() <= article.getEndTime().getTime() && countInfo.getUsedCost() > 0) {
                //3、领取的金额小于单个任务最大的金额才能领取佣金
                if ((order.getMyselfMoney() + order.getOutMoney()) < countInfo.getMostCost()) {
                    countInfo.setUsedCost(countInfo.getUsedCost() - (countInfo.getOnceCost()));

                    //4、判断是否订单是否有上级推客，如果有的话，计算给上级推客的钱
                    if (order.getHasParentTwr() == 1) {
                        order.setMyselfMoney(order.getMyselfMoney() + (countInfo.getOnceCost() * ArticleCommissionConstants.MYSELF_COMMISSION));
                        order.setOutMoney(order.getOutMoney() + (countInfo.getOnceCost() * ArticleCommissionConstants.OUT_COMMISSION));
                    } else {
                        order.setMyselfMoney(order.getMyselfMoney() + countInfo.getOnceCost());
                    }

                }
            }
        }else {
            countForeverArticleMoney(order,countInfo);
        }

    }


    /**
     * 统计金钱
     */
    private void countOrderMoney(Article article,ArticleOrder order,ArticleCountInfo countInfo) {

        //1、判断推客是否还可以获取钱，如果点击次数超过了最大点击次数了,就不会加钱了
        if (order.getClickNum() < order.getMaxClickNum()) {
            if(isForeverArticle(article)) {
                countForeverArticleMoney(order,countInfo);
            }else {
                countNoForeverArticleMoney(article,order,countInfo);
            }
        }

        //检验文章是否已完成任务
        if(taskIsComplete(countInfo)) {
            order.setStatus(ArticleOrderStatus.ENDING.getType());
            order.setUpdateTime(new Date());

            completeTask(countInfo.getArticleId());
        }

        order.setClickNum(order.getClickNum() + 1);
        _saveArticleOrder(order);
        countInfo.setPageView(countInfo.getPageView() + 1);
        _saveArticleCountInfo(countInfo);
    }

    private boolean taskIsComplete(ArticleCountInfo countInfo) {
        if(!(countInfo.getUsedCost() > 0)) {
            return true;
        }
        return false;
    }


    /**
     * 统计MQ中订单分享量
     * @param articleId
     * @param userId
     * @param type
     */
    public void countOrderShareMq(Long articleId,Long userId,Integer type) {
        Article article = _getArticle(articleId);

        //1、判断文章是否推广任务
        if(article.getaIsTask().intValue() == 0) {
            //2、普通分享，只更新share表点击量和总countinfo点击量
            countArticleShare(userId,articleId,type);
        }else {
             //3、任务分享
            countArticleOrderShare(article,articleId,userId,type);
        }

    }

    public boolean checkArticleIsHaveMoney(Long articleId) {
        ArticleCountInfo countInfo = _getArticleCountInfo(articleId);
        if(countInfo != null) {
            if(countInfo.getUsedCost() > 0) {
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    public boolean checkArticleDated(Long articleId) {
        boolean flag = false;

        Article article = _getArticle(articleId);
        if(article.getaIsTask().intValue() == 1) {
            if(article.getStatus().intValue() != ArticleStatus.PUBLISH.getType().intValue()) {
                flag = true;
                return flag;
            }

            ArticleCountInfo countInfo = _getArticleCountInfo(articleId);
            if(article.getBeginTime() != null && article.getEndTime() != null) {
                //不是永久有效
                Date currentDate = new Date();
                if (currentDate.getTime() >= article.getEndTime().getTime()) {
                    flag = true;
                }
            }else {
                //永久有效
                if(countInfo != null) {
                    if(countInfo.getUsedCost() <= 0) {
                        flag = true;
                    }
                }

            }
        }else {
            flag = true;
        }

        return flag;
    }


    /**
     * 获取缓存中Article
     * @param articleId
     * @return
     */
    public Article getArticle(Long articleId) {
        return _getArticle(articleId);
    }

    /**
     * 获取缓存中countinfo
     * @param articleId
     * @return
     */
    public ArticleCountInfo getCountInfo(Long articleId) {
        return _getArticleCountInfo(articleId);
    }

    /**
     * 获取缓存中的articleOrder
     * @param userId
     * @param articleId
     * @return
     */
    public ArticleOrder getOrder(Long userId, Long articleId) {
        return _getArticleOrder(userId,articleId);
    }

    /**
     * 获取缓存中的articleShare
     * @param userId
     * @param articleId
     * @return
     */
    public ArticleShare getShare(Long userId, Long articleId,Integer type) {
        return _getArticleShare(userId,articleId,type);
    }


    /**
     * 判断推客是否可以领取任务
     * @param articleId
     * @return
     */
    public Boolean checkGetTask(Long articleId) {
        Boolean flag = false;
        Article article = getArticle(articleId);
        ArticleCountInfo articleCountInfo = getCountInfo(articleId);

        //1、是否是任务
        if(article.getaIsTask() == 1) {
            //2、活动是否是永久
            if(article.getEndTime() == null && articleCountInfo.getUsedCost() > 0) {
                flag = true;
            } else {
                Date currentDate = new Date();
                //两个条件：1、任务活动时间，2、任务的剩余金额
                if (currentDate.getTime() >= article.getBeginTime().getTime() && currentDate.getTime() <= article.getEndTime().getTime() && articleCountInfo.getUsedCost() > 0) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 保存文章统计信息到resdis
     * @param articleCountInfo
     * @return
     */

    public void saveArticleCountInfo(ArticleCountInfo articleCountInfo) {
        _saveArticleCountInfo(articleCountInfo);
    }



    //==========================以下方法为内部方法==========================================

    private ArticleCountInfo _getArticleCountInfo(Long articleId) {
        return  redisProxy.getObjectClsValue(ArticleRedisConstants.ARTICLE_COUNT_INFO + articleId);
    }


    private Article _getArticle(Long articleId) {
        return redisProxy.getObjectClsValue(ArticleRedisConstants.ARTICLE_COUNT_ARTICLE + articleId);
    }

    private ArticleShare _getArticleShare(Long userId,Long articleId,Integer type) {
        return redisProxy.getObjectClsValue(ArticleRedisConstants.ARTICLE_SHARE + userId +":"+ articleId + ":" + type);
    }

    private ArticleOrder _getArticleOrder(Long userId,Long articleId) {
        return redisProxy.getObjectClsValue(ArticleRedisConstants.ARTICLE_ORDER + userId +":"+ articleId);
    }

    private void _saveArticle(Article article) {
        redisProxy.saveObjValue(ArticleRedisConstants.ARTICLE_COUNT_ARTICLE + article.getId(),article);
    }

    private void _saveArticleCountInfo(ArticleCountInfo articleCountInfo) {

        redisProxy.saveObjValue(ArticleRedisConstants.ARTICLE_COUNT_INFO + articleCountInfo.getArticleId(),articleCountInfo);

        //添加到任务
        if(redisProxy.getObjectClsValue(ArticleRedisConstants.ARTICLE_TASK_COUNT_INFO) == null) {
            Set<String> taskSet = new HashSet<>();
            taskSet.add(ArticleRedisConstants.ARTICLE_COUNT_INFO + articleCountInfo.getArticleId());
            redisProxy.saveObjValue(ArticleRedisConstants.ARTICLE_TASK_COUNT_INFO,taskSet);
        }else {
            Set<String> taskSet = redisProxy.getObjectClsValue(ArticleRedisConstants.ARTICLE_TASK_COUNT_INFO);
            taskSet.add(ArticleRedisConstants.ARTICLE_COUNT_INFO + articleCountInfo.getArticleId());
            redisProxy.saveObjValue(ArticleRedisConstants.ARTICLE_TASK_COUNT_INFO,taskSet);
        }

    }

    private void _saveArticleOrder(ArticleOrder articleOrder) {
        redisProxy.saveObjValue(ArticleRedisConstants.ARTICLE_ORDER + articleOrder.getUserId() +":"+articleOrder.getArticleId(),articleOrder);
        //添加到任务

        if(redisProxy.getObjectClsValue(ArticleRedisConstants.ARTICLE_TASK_ORDER) == null) {
            Set<String> taskSet = new HashSet<>();
            taskSet.add(ArticleRedisConstants.ARTICLE_ORDER + articleOrder.getUserId() +":"+articleOrder.getArticleId());
            redisProxy.saveObjValue(ArticleRedisConstants.ARTICLE_TASK_ORDER,taskSet);
        }else {
            Set<String> taskSet = redisProxy.getObjectClsValue(ArticleRedisConstants.ARTICLE_TASK_ORDER);
            taskSet.add(ArticleRedisConstants.ARTICLE_ORDER + articleOrder.getUserId() +":"+articleOrder.getArticleId());
            redisProxy.saveObjValue(ArticleRedisConstants.ARTICLE_TASK_ORDER,taskSet);
        }

    }

    private void _saveArticleShare(ArticleShare articleShare) {
        redisProxy.saveObjValue(ArticleRedisConstants.ARTICLE_SHARE + articleShare.getUserId() +":"+articleShare.getArticleId() +":"+articleShare.getType(),articleShare);
        //添加到任务
        if(redisProxy.getObjectClsValue(ArticleRedisConstants.ARTICLE_TASK_SHARE) == null) {
            Set<String> taskSet = new HashSet<>();
            taskSet.add(ArticleRedisConstants.ARTICLE_SHARE + articleShare.getUserId() +":"+articleShare.getArticleId() +":"+articleShare.getType());
            redisProxy.saveObjValue(ArticleRedisConstants.ARTICLE_TASK_SHARE,taskSet);
        }else {
            Set<String> taskSet = redisProxy.getObjectClsValue(ArticleRedisConstants.ARTICLE_TASK_SHARE);
            taskSet.add(ArticleRedisConstants.ARTICLE_SHARE + articleShare.getUserId() +":"+articleShare.getArticleId() +":"+articleShare.getType());
            redisProxy.saveObjValue(ArticleRedisConstants.ARTICLE_TASK_SHARE,taskSet);
        }
    }


    public Long deleteArticleCountInfo(Long articleId) {
        return redisProxy.deleteObj(ArticleRedisConstants.ARTICLE_COUNT_INFO + articleId);
    }

    public Long deleteArticle(Long articleId) {
        return redisProxy.deleteObj(ArticleRedisConstants.ARTICLE_COUNT_ARTICLE + articleId);
    }


}
