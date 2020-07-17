package com.autozi.cheke.mobile.facade;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.article.entity.ArticleCountInfo;
import com.autozi.cheke.article.entity.ArticleOrder;
import com.autozi.cheke.article.type.ArticleOrderStatus;
import com.autozi.cheke.service.article.IArticleOrderService;
import com.autozi.cheke.service.article.IArticleService;
import com.autozi.cheke.service.user.IUserService;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.util2.CommonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class TaskApiFacade {

	@Autowired
	private IArticleService articleService;
	@Autowired
	private IArticleOrderService articleOrderService;
	@Autowired
	private ArticleRedisApiFacade articleRedisApiFacade;
	@Autowired
	private IUserService userService;
	
	
	public JSONObject getTaskDetail(Long articleId) {
		JSONObject data = new JSONObject();
		Article article = articleService.getArticleById(articleId);
		ArticleCountInfo articleCountInfo = articleRedisApiFacade.getCountInfo(article.getId());
		//点击一次费用
		data.put("onceCost", articleCountInfo.getOnceCost()==null?"0.00":articleCountInfo.getOnceCost());
		//有效期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		data.put("endTime", article.getEndTime()==null?"永久":sdf.format(article.getEndTime()));
		return data;
	}
	
	public JSONObject getTask(Long articleId, User user) {
		JSONObject data = new JSONObject();
		Article article = articleService.getArticleById(articleId);
		if(articleRedisApiFacade.checkGetTask(articleId)){
			ArticleOrder order = articleOrderService.getOrderByArticleIdAndUserId(articleId, user.getId());
			if(order==null){
				buildeArticleOrder(articleId,user);
				data.put("getTaskCodeMsg", "任务领取成功");
				data.put("bCode",0);
			}else {
				data.put("getTaskCodeMsg", "已领过此任务");
				data.put("bCode",1);
			}
			data.put("title", article.getTitle());
			data.put("image", article.getImage());
		}else{
			data.put("getTaskCodeMsg", "任务领取失败");
			data.put("bCode",-1);
		}
		return data;
	}

	private void buildeArticleOrder(Long articleId, User user) {
		ArticleOrder articleOrder = new ArticleOrder();
		articleOrder.setArticleId(articleId);
		articleOrder.setUserId(user.getId());
		articleOrder.setCreateTime(new Date());
		articleOrder.setClickNum(0);
		ArticleCountInfo countInfo = articleRedisApiFacade.getCountInfo(articleId);
		Integer maxClickNum = (int) (countInfo.getMostCost()/countInfo.getOnceCost());
		articleOrder.setMaxClickNum(maxClickNum);
		articleOrder.setUpdateTime(new Date());
		articleOrder.setStatus(ArticleOrderStatus.ACTIVATING.getType());
		articleOrder.setMyselfMoney(0.00D);
		articleOrder.setOutMoney(0.00D);

		articleOrder.setSyncMyselfMoney(0.0);
		articleOrder.setSyncOutMoney(0.0);

		//判断是否有上级推客
		Long tuikeId = userService.getTopTuikeId(user.getId());
		if(tuikeId != null) {
			articleOrder.setHasParentTwr(1);
			articleOrder.setTopTuikeId(tuikeId);
		}else {
			articleOrder.setHasParentTwr(0);
		}

		Long orderId = articleOrderService.addArticleOrder(articleOrder);
		articleOrder.setId(orderId);

		articleRedisApiFacade.afterGetTask(articleOrder);
	}
	
	public void getTaskCallback(Long articleId, User user, Integer shareType) {
		ArticleOrder articleOrder = articleOrderService.getOrderByArticleIdAndUserId(articleId, user.getId());
		articleOrder.setType(shareType);
		articleRedisApiFacade.countArticleInfo(articleOrder.getType(), articleOrder.getArticleId(),false);
		articleOrderService.updataOrderAndCountInfo(articleOrder);
	}

	/**
	 * 获取当前用户的任务列表
	 * @param page 
	 * @param filters 
	 * @param user
	 * @throws Exception 
	 */
	public JSONArray getTaskList(Page<Map<String, Object>> page, Map<String, Object> filters, User user) throws Exception {
		JSONArray articleOrderArray = new JSONArray();
		Page<ArticleOrder> _page = new Page<ArticleOrder>();
		CommonUtils.pageConversion(page, _page);
		filters.put("userId",user.getId());
		_page = articleOrderService.findPageForMap(_page, filters);
		for (ArticleOrder articleOrder : _page.getResult()) {
			JSONObject articleOrderObj = new JSONObject();
			Article article = articleService.getArticleById(articleOrder.getArticleId());
			articleOrderObj.put("articleId",article.getId());
			articleOrderObj.put("title", article.getTitle());
			articleOrderObj.put("image", article.getImage());


			Long createTime = articleOrder.getCreateTime().getTime();
			Long nowTime = System.currentTimeMillis();
			if(nowTime - createTime < 60*60*1000*24){
				articleOrderObj.put("createTime", new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(articleOrder.getCreateTime()));
			}else{
				articleOrderObj.put("createTime", new SimpleDateFormat("yyyy年MM月dd日").format(articleOrder.getCreateTime()));
			}


			articleOrderObj.put("clickNum", articleOrder.getClickNum());
//			ArticleCountInfo countInfo = articleRedisApiFacade.getCountInfo(article.getId());
//			Double money = countInfo.getOnceCost()*articleOrder.getClickNum();
			articleOrderObj.put("money", articleOrder.getMyselfMoney());
			articleOrderArray.add(articleOrderObj);
		}
		CommonUtils.pageConversion(_page, page);
		return articleOrderArray;
	}

	public JSONArray getHotTaskList(Map<String, Object> map) {
		List<Article> articleList = articleService.findListForMap(map);
		JSONArray articleArray = new JSONArray();
		int size =  articleList.size() < 10 ? articleList.size() : 10 ;
		for (int i = 0; i < size; i++) {
			JSONObject articleObj = new JSONObject();
			articleObj.put("articleId", articleList.get(i).getId());
			articleObj.put("title", articleList.get(i).getTitle());
			articleObj.put("image", articleList.get(i).getImage());


			String publishTimeCN = "";
			if(articleList.get(i).getPublishTime() != null) {
				Long publishTime = articleList.get(i).getPublishTime().getTime();
				Long nowTime = System.currentTimeMillis();
				if(nowTime - publishTime<60*60*1000){//1小时以内
					publishTimeCN = "刚刚";
				}else if(nowTime - publishTime < 60*60*1000*24){
					publishTimeCN = new SimpleDateFormat("HH:mm").format(articleList.get(i).getPublishTime());
				}else{
					publishTimeCN = new SimpleDateFormat("MM月dd日").format(articleList.get(i).getPublishTime());
				}
			}
			articleObj.put("createTime",publishTimeCN);

			articleArray.add(articleObj);
		}
		return articleArray;
	}
}
