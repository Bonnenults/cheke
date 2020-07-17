package com.autozi.cheke.web.article.facade;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.article.entity.ArticleCountInfo;
import com.autozi.cheke.article.entity.ArticleOrder;
import com.autozi.cheke.article.entity.ArticleShare;
import com.autozi.cheke.article.type.ArticleCommissionConstants;
import com.autozi.cheke.article.type.ArticleRedisConstants;
import com.autozi.cheke.article.type.ArticleStatus;
import com.autozi.cheke.service.article.IArticleCountInfoService;
import com.autozi.cheke.service.article.IArticleOrderService;
import com.autozi.common.cache.jedis.proxy.RedisProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wang-lei on 2017/12/21.
 */
@Component
public class ArticleRedisFacade {


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
     * 更新文章信息到redis中
     * @param article
     */
    public void updateArticle(Article article) {
        _saveArticle(article);
    }

    /**
     * 下线后更新文章的状态
     * @param articleId
     */
    public void offLineRedisArticle(Long articleId) {
        Article article = _getArticle(articleId);
        if(article != null) {
            article.setStatus(ArticleStatus.OFFLINE.getType());
            article.setOfflineTime(new Date());
            //如果为课程，则将课程ID和num置为null
            if(article.getCourseId() != null && !"".equals(article.getCourseId())){
                article.setCourseId(null);
                article.setNum(null);
            }
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
    public void countArticleInfo(Integer type,Long articleId) {
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
                info.setLikes(info.getLikes() + 1);
                _saveArticleCountInfo(info);
                break;
            case 12:
                info.setDislikes(info.getDislikes() + 1);
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
     * 统计MQ中订单分享量
     * @param articleId
     * @param userId
     * @param type
     */
    public void countOrderShareMq(Long articleId,Long userId,Integer type) {

        //1、判断文章是否推广任务
        Article article = _getArticle(articleId);
        if(article.getaIsTask().intValue() == 0) {
            //2、如果是普通分享，只更新share表点击量和总countinfo点击量
            countArticleShare(userId,articleId,type);

        }else {
            ArticleOrder checkedOrder = articleOrderService.getOrderByArticleIdAndUserId(articleId,userId);
            //3、如果是推广任务（并且用户领取了任务），更新order点击量并计算获取佣金，更新share分享点击量、更新countInfo总点击量
            if(checkedOrder != null) {

                ArticleOrder order = _getArticleOrder(userId,articleId);
                ArticleCountInfo countInfo = _getArticleCountInfo(articleId);

                //1、判断推客是否还可以获取钱，（如果点击次数超过了最大点击次数了，说明推客的任务已经完成）
                if(order.getClickNum() < order.getMaxClickNum()) {

                    //2、判断是否永久有效
                    if(article.getEndTime() == null && countInfo.getUsedCost() > 0) {

                        //3、领取的金额小于单个任务最大的金额才能领取佣金
                        if((order.getMyselfMoney() + order.getOutMoney()) < countInfo.getMostCost()) {
                            countInfo.setUsedCost(countInfo.getUsedCost() - (countInfo.getOnceCost()));

                            //4、判断是否订单是否有上级推客，如果有的话，计算给上级推客的钱
                            if(order.getHasParentTwr() == 1) {
                                order.setMyselfMoney(order.getMyselfMoney() + (countInfo.getOnceCost() * ArticleCommissionConstants.MYSELF_COMMISSION));
                                order.setOutMoney(order.getOutMoney() + (countInfo.getOnceCost() * ArticleCommissionConstants.OUT_COMMISSION));
                            }else {
                                order.setMyselfMoney(order.getMyselfMoney() + countInfo.getOnceCost());
                            }

                        }

                    }else {
                        //2、判断任务是否过期（两个条件，日期和剩余金额）
                        Date currentDate = new Date();
                        if(currentDate.getTime() >= article.getBeginTime().getTime() && currentDate.getTime() <= article.getEndTime().getTime() && countInfo.getUsedCost() > 0) {

                            //3、领取的金额小于单个任务最大的金额才能领取佣金
                            if((order.getMyselfMoney() + order.getOutMoney()) < countInfo.getMostCost()) {
                                countInfo.setUsedCost(countInfo.getUsedCost() - (countInfo.getOnceCost()));

                                //4、判断是否订单是否有上级推客，如果有的话，计算给上级推客的钱
                                if(order.getHasParentTwr() == 1) {
                                    order.setMyselfMoney(order.getMyselfMoney() + (countInfo.getOnceCost() * ArticleCommissionConstants.MYSELF_COMMISSION));
                                    order.setOutMoney(order.getOutMoney() + (countInfo.getOnceCost() * ArticleCommissionConstants.OUT_COMMISSION));
                                }else {
                                    order.setMyselfMoney(order.getMyselfMoney() + countInfo.getOnceCost());
                                }

                            }
                        }

                    }
                }else {
                    //更改任务状态为已完成任务
                    order.setStatus(1);
                }

                order.setClickNum(order.getClickNum() + 1);
                _saveArticleOrder(order);
                countInfo.setPageView(countInfo.getPageView() + 1);
                _saveArticleCountInfo(countInfo);
            }else {
                //任务推广，但是用户没有领取任务，跟普通分享一样
                countArticleShare(userId,articleId,type);
            }
        }

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



    //**************************************************
    /**
     * 获取统计任务countinfo的key
     * @return
     */
    public Set<String> getTaskArticleCountInfo() {
        return redisProxy.getObjectClsValue(ArticleRedisConstants.ARTICLE_TASK_COUNT_INFO);
    }

    /**
     * 获取
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getTaskValue(String key) {
        T object = (T) redisProxy.getObjectClsValue(key);
        return object;
    }



    //**************************************************

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

}
