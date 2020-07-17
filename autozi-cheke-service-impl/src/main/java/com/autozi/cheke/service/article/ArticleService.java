package com.autozi.cheke.service.article;

import com.autozi.cheke.article.dao.ArticleDao;
import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.article.entity.ArticleCountInfo;
import com.autozi.cheke.article.entity.ArticleTag;
import com.autozi.cheke.article.entity.ArticleTagRelation;
import com.autozi.cheke.article.type.ArticleStatus;
import com.autozi.cheke.service.settle.IAccountService;
import com.autozi.common.core.page.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wang-lei on 2017/11/28.
 */
@Service
public class ArticleService implements IArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleCountInfoService articleCountInfoService;

    @Autowired
    private ArticleTagRelationService articleTagRelationService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private IAccountService accountService;

    @Override
    public int addArticle(Article article) {
        return articleDao.insert(article);
    }

    @Override
    public int addArticleReturnId(Article article, List<String> tagIdList, Double allCost, Double onceCost, Double mostCost) {
        article.setStatus(ArticleStatus.WAITING.getType());
        article.setCreateTime(new Date());
        article.setTag(assembleArticleTagName(tagIdList));
        articleDao.insert(article);
        Long articleId = article.getId();

        //绑定文章和分类
        for (String tagId : tagIdList) {
            ArticleTagRelation relation = new ArticleTagRelation();
            relation.setArticleId(articleId);
            relation.setArticleTagId(Long.parseLong(tagId));
            articleTagRelationService.addArticleTagRelation(relation);
        }

        //初始化统计表
        initCountInfo(articleId, allCost, onceCost, mostCost);

        return 1;
    }

    /**
     * 初始化统计表
     *
     * @param articleId
     * @return
     */
    private int initCountInfo(Long articleId, Double allCost, Double onceCost, Double mostCos) {
        ArticleCountInfo ac = new ArticleCountInfo();
        ac.setArticleId(articleId);
        ac.setAllCost(allCost == null ? 0.0 : allCost);
        ac.setOnceCost(onceCost == null ? 0.0 : onceCost);
        ac.setMostCost(mostCos == null ? 0.0 : mostCos);
        ac.setUsedCost(allCost == null ? 0.0 : allCost);

        ac.setStuNum(0);
        ac.setCompletedNum(0);
        ac.setTwitter(0);
        ac.setLeaveWords(0);
        ac.setLikes(0);
        ac.setDislikes(0);
        ac.setPageView(0);
        ac.setShareAmount(0);
        ac.setWxFriends(0);
        ac.setWxFriendsCircle(0);
        ac.setQqFriends(0);
        ac.setQqZone(0);
        ac.setSina(0);
        return articleCountInfoService.addCountInfo(ac);
    }


      /* 重新组装TagName
       * @param articleVo
       */
    private String assembleArticleTagName(List<String> tagIdList) {
        String tagName = "";
        for (String tagId : tagIdList) {
            ArticleTag articleTag = articleTagService.getArticleTagById(Long.parseLong(tagId));
            tagName += articleTag.getName() + "、";
        }
        tagName = tagName.substring(0, tagName.length() - 1);
        return tagName;
    }

    @Override
    public int updateArticleNotPublic(Article article, List<String> tagIdList, Double allCost, Double onceCost, Double mostCost) {

        article.setUpdateTime(new Date());
        article.setTag(assembleArticleTagName(tagIdList));
        articleDao.updateNul(article);

        //组装分类名称,返回文章分类的ID
        //原来的绑定关系删除，重新绑定
        articleTagRelationService.deleteRelationByArticleId(article.getId());


        //绑定文章和分类
        for (String tagId : tagIdList) {
            ArticleTagRelation relation = new ArticleTagRelation();
            relation.setArticleId(article.getId());
            relation.setArticleTagId(Long.parseLong(tagId));
            articleTagRelationService.addArticleTagRelation(relation);
        }

        //只能修改这三个Money，如果文章、项目、视频已经发布过了，任何东西不可修改
        ArticleCountInfo ac = articleCountInfoService.getCountInfoByArticleId(article.getId());
        ac.setAllCost(allCost == null ? 0.0 : allCost);
        ac.setMostCost(mostCost == null ? 0.0 : mostCost);
        ac.setOnceCost(onceCost == null ? 0.0 : onceCost);
        ac.setUsedCost(allCost == null ? 0.0 : allCost);
        articleCountInfoService.updateCountInfoNul(ac);

        return 1;
    }


    @Override
    public int updateArticle(Article article) {
        return articleDao.update(article);
    }

    @Override
    public Article getArticleById(Long id) {
        return articleDao.get(id);
    }

    @Override
    public Article getArticleByFilter(Map<String, Object> filter) {
        Article article = null;
        List<Article> list = articleDao.findListForMap(filter);
        if (list != null && list.size() > 0) {
            article = list.get(0);
        }
        return article;
    }

    @Override
    public Page<Article> findArticlePage(Page<Article> page, Map<String, Object> filter) throws Exception {
        return articleDao.findPageForMap(page, filter);
    }

    @Override
    public List<Article> findListForMap(Map<String, Object> filter) {
        return articleDao.findListForMap(filter);
    }

    @Override
    public Integer countArticleByCurrentUserId(Long userId) {
        Map<String, Object> filter = new HashMap<>();
        filter.put("createUserId", userId);
        return articleDao.countArticle(filter);
    }

    @Override
    public void batchPublish(List<String> articleIds) {
        for (String id : articleIds) {
            Article article = getArticleById(Long.parseLong(id));
            article.setStatus(ArticleStatus.REVIEW.getType());
            article.setCommitTime(new Date());
            articleDao.update(article);

            if(article.getaIsTask().intValue() == 1) {
                //扣除账户金额
                ArticleCountInfo countInfo = articleCountInfoService.getCountInfoByArticleId(Long.parseLong(id));
                accountService.payTask(article.getCreateUserId(),countInfo.getAllCost(),Long.parseLong(id));
            }
        }
    }

    @Override
    public void batchCancel(List<String> articleIds) {
        for (String id : articleIds) {
            Article article = getArticleById(Long.parseLong(id));
            article.setStatus(ArticleStatus.CANCEL.getType());
            articleDao.update(article);

            if(article.getaIsTask().intValue() == 1) {
                //扣除账户金额
                ArticleCountInfo countInfo = articleCountInfoService.getCountInfoByArticleId(article.getId());
                accountService.refundCk(article.getCreateUserId(),countInfo.getAllCost(),article.getId());
            }

        }
    }

    @Override
    public void batchOffline(List<String> articleIds) {
        for (String id : articleIds) {
            Article article = getArticleById(Long.parseLong(id));
            article.setStatus(ArticleStatus.OFFLINE.getType());
            article.setOfflineTime(new Date());
            //如果为课程，则将课程ID和num置为null
            if(article.getCourseId() != null && !"".equals(article.getCourseId())){
                article.setCourseId(null);
                article.setNum(null);
            }
            articleDao.update(article);

            if(article.getaIsTask().intValue() == 1) {
                ArticleCountInfo countInfo = articleCountInfoService.getCountInfoByArticleId(article.getId());
                accountService.refundCk(article.getCreateUserId(),countInfo.getUsedCost(),article.getId());
            }
        }
    }

    @Override
    public int publishArticle(Long articleId) {
        Article article = getArticleById(articleId);
        article.setStatus(ArticleStatus.REVIEW.getType());
        article.setCommitTime(new Date());

        if(article.getaIsTask().intValue() == 1) {
            //扣除账户金额
            ArticleCountInfo countInfo = articleCountInfoService.getCountInfoByArticleId(articleId);
            accountService.payTask(article.getCreateUserId(),countInfo.getAllCost(),articleId);
        }

        return articleDao.update(article);
    }

    @Override
    public int offLineArticle(Long articleId) {
        Article article = getArticleById(articleId);
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
        return articleDao.update(article);
    }

    @Override
    public int cancelPublish(Long articleId) {
        Article article = getArticleById(articleId);
        article.setStatus(ArticleStatus.CANCEL.getType());

        if(article.getaIsTask().intValue() == 1) {
            //返还账户金额
            ArticleCountInfo countInfo = articleCountInfoService.getCountInfoByArticleId(article.getId());
            accountService.refundCk(article.getCreateUserId(),countInfo.getAllCost(),articleId);
        }

        return articleDao.update(article);
    }

    @Override
    public int copyArticle(Long articleId) {
        Article target = new Article();
        Article article = articleDao.get(articleId);
        ArticleCountInfo articleCountInfo = articleCountInfoService.getCountInfoByArticleId(articleId);

        Map<String,Object> tagFilter = new HashMap<>();
        tagFilter.put("articleId",articleId);
        List<ArticleTagRelation> oldTag = articleTagRelationService.findRelationList(tagFilter);

        BeanUtils.copyProperties(article, target);
        target.setId(null);
        target.setCreateTime(new Date());
        target.setUpdateTime(null);
        target.setPublishTime(null);
        target.setOfflineTime(null);
        target.setCommitTime(null);
        target.setPassTime(null);
        target.setRefuseReason(null);
        target.setStatus(ArticleStatus.WAITING.getType());

        articleDao.insert(target);

        //绑定分类
        for(ArticleTagRelation relation : oldTag) {
            ArticleTagRelation nr = new ArticleTagRelation();
            nr.setArticleId(target.getId());
            nr.setArticleTagId(relation.getArticleTagId());
            articleTagRelationService.addArticleTagRelation(nr);
        }

        initCountInfo(target.getId(),articleCountInfo.getAllCost(),articleCountInfo.getOnceCost(),articleCountInfo.getMostCost());

        return 1;
    }

    @Override
    public int countDayArticleNum(Integer channelType,Long createUserId,Long id) {
        return articleDao.countDayArticleNum(channelType,createUserId,id);
    }

    @Override
    public List<Long> countDayArticleId(Integer channelType, Long createUserId) {
        return articleDao.countDayArticleId(channelType,createUserId);
    }

    @Override
    public List<Article> findListForEndTimeTrigger() {
        return articleDao.findListForEndTimeTrigger();
    }
}
