package com.autozi.cheke.service.article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.autozi.cheke.article.type.ArticleOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autozi.cheke.article.dao.ArticleCountInfoDao;
import com.autozi.cheke.article.dao.ArticleOrderDao;
import com.autozi.cheke.article.entity.ArticleCountInfo;
import com.autozi.cheke.article.entity.ArticleOrder;
import com.autozi.cheke.article.entity.ArticleShare;
import com.autozi.cheke.article.type.ArticleClickType;
import com.autozi.common.core.page.Page;

/**
 * Created by wang-lei on 2017/11/28.
 */
@Service
public class ArticleOrderService implements IArticleOrderService{

    @Autowired
    private ArticleOrderDao articleOrderDao;
    @Autowired
    private ArticleCountInfoService articleCountInfoService;

    @Override
    public Long addArticleOrder(ArticleOrder articleOrder) {
		articleOrderDao.insert(articleOrder);
        return articleOrder.getId();
    }

    @Override
    public int updateArticleOrder(ArticleOrder articleOrder) {
        return articleOrderDao.update(articleOrder);
    }

    @Override
    public ArticleOrder getOrderById(Long id) {
        return articleOrderDao.get(id);
    }

	@Override
	public ArticleOrder getOrderByArticleIdAndUserId(Long articleId, Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articleId", articleId);
		map.put("userId", userId);
		List<ArticleOrder> list = articleOrderDao.findListForMap(map);

		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<ArticleOrder> getOrderByUserId(Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return articleOrderDao.findListForMap(map);
	}

	@Override
	public Page<ArticleOrder> findPageForMap(Page<ArticleOrder> page, Map<String, Object> filter) {
		return articleOrderDao.findPageForMap(page, filter);
	}

    @Override
    public Integer countAllClickNum(Long articleId) {
        return articleOrderDao.countAllClickNum(articleId);
    }

	@Override
	public void updataOrderAndCountInfo(ArticleOrder articleOrder) {
		articleOrderDao.update(articleOrder);
	}

	@Override
	public Integer getTaskCount(Long articleId) {
		return articleOrderDao.getTaskCount(articleId);
	}

	@Override
	public List<ArticleOrder> getOrderByArticleId(Long articleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articleId", articleId);
		return articleOrderDao.findListForMap(map);
	}

	@Override
	public List<ArticleOrder> getOrderByUserIdAndStatus(Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("status", ArticleOrderStatus.ACTIVATING.getType());
		return articleOrderDao.findListForMap(map);
	}
}
