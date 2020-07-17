package com.autozi.cheke.mobile.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.autozi.cheke.article.entity.ArticleOrder;
import com.autozi.cheke.article.type.ArticleOrderStatus;
import com.autozi.cheke.article.type.ArticleStatus;
import com.autozi.cheke.mobile.constant.MobileConstant;
import com.autozi.cheke.mobile.facade.TaskApiFacade;
import com.autozi.cheke.service.article.IArticleOrderService;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.exception.B2bException;
import com.autozi.common.core.page.Page;

/**
 * 推客App端_任务功能
 * @author 魏唯
 *
 */
@RequestMapping("/task/tk/")
@Controller
public class TaskApiController extends BaseApiController {

	@Autowired
	private TaskApiFacade taskApiFacade;
	@Autowired
	private IArticleOrderService articleOrderService;
	
	/**
	 * 领取任务
	 * @param request
	 * @param response
	 * @param articleId
	 */
	@RequestMapping("taskDetail.api")
	public void taskDetail(HttpServletRequest request, HttpServletResponse response, Long articleId){
		try {
			User user = super.getUser(request);
			//是否登录
			if(user==null){
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
			JSONObject data = taskApiFacade.getTaskDetail(articleId);
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
 		}catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
	//确认领取任务
	@RequestMapping("getTask.api")
	public void getTask(HttpServletRequest request, HttpServletResponse response, Long articleId){
		try {
			User user = super.getUser(request);
			//是否登录
			if(user==null){
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
			JSONObject data = taskApiFacade.getTask(articleId,user);

			int bCode = data.getInt("bCode");
			if(bCode == 0) {
				this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
			}else if(bCode == 1) {
				this.response(request,response, MobileConstant.Error._error, "已领取过任务");
			}else{
				this.response(request,response, MobileConstant.Error._error, "领取任务已过期");
			}
 		}catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}

	/**
	 * 这个没有用
	 * @param request
	 * @param response
	 * @param articleId
	 * @param shareType
     */
	@RequestMapping("getTaskCallback.api")
	public void getTaskCallback(HttpServletRequest request, HttpServletResponse response, Long articleId, Integer shareType){
		try {
			User user = super.getUser(request);
			//是否登录
			if(user==null){
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
			if(shareType==null){
				throw new B2bException("分享类型不能为空");
			}
			taskApiFacade.getTaskCallback(articleId,user,shareType);
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg);
 		}catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
	
	/*任务
	1.是否登录
	2.未领任务
	3.进行中的任务(继续分享)
	4.已完成的任务
	*/
	//任务列表
	@RequestMapping("listTask.api")
	public void listTask(HttpServletRequest request, HttpServletResponse response,String source){
		try {
			JSONObject data = new JSONObject();
			User user = super.getUser(request);
			//是否登录
			if(user==null){
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
			if(StringUtils.isBlank(source)){
				throw new B2bException("source为空");
			}
			Page<Map<String, Object>> page = new Page<Map<String, Object>>();
			String pageNo = request.getParameter("pageNo");
	        if (StringUtils.isNotBlank(pageNo)) {
	            page.setPageNo(Integer.parseInt(pageNo));
	        } else {
	            page.setPageNo(MobileConstant.PageSize._page_no);
	        }
	        String pageSizeStr = request.getParameter("pageSize");
	        if (StringUtils.isNotBlank(pageSizeStr)) {
	            page.setPageSize(Integer.parseInt(pageSizeStr));
	        } else {
	            page.setPageSize(MobileConstant.PageSize._page_size);
	        }
	        Map<String, Object> filters = new HashMap<String, Object>();
	        List<ArticleOrder> articleOrderList = articleOrderService.getOrderByUserId(user.getId());
	        if(articleOrderList.size()==0){
	        	//进行中和已完成的任务都没有,提示他领取任务,返回推荐任务,推荐任务根据总资金降序
	        	Map<String, Object> map = new HashMap<String, Object>();
	        	map.put("status", ArticleStatus.PUBLISH.getType());
				map.put("aIsTask",1);
	        	map.put("orderBy", "aci.ALL_COST desc");
	        	JSONArray array = taskApiFacade.getHotTaskList(map);
	        	data.put("hasTask", false);
	        	data.put("hotTask", array.toString());
				data.put("taskArray", new JSONArray().toString());
	        	this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
	        	return;
	        }
			if(source.equals(ArticleOrderStatus.ACTIVATING.getType().toString())){
				filters.put("status", ArticleOrderStatus.ACTIVATING.getType());
			}else {//已完成的任务
				filters.put("status", ArticleOrderStatus.ENDING.getType());
			}

			JSONArray taskArray = taskApiFacade.getTaskList(page, filters, user);
			data.put("curPageNo", page.getPageNo());
            data.put("totalPages", page.getTotalPages());
            data.put("taskArray", taskArray.toString());
			data.put("hasTask", true);

			List<ArticleOrder> tuiTask = articleOrderService.getOrderByUserIdAndStatus(user.getId());
			if(tuiTask.size() == 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("status", ArticleStatus.PUBLISH.getType());
				map.put("aIsTask",1);
				map.put("orderBy", "aci.ALL_COST desc");
				JSONArray array = taskApiFacade.getHotTaskList(map);
				data.put("hotTask", array.toString());
				data.put("hasTask", false);
			}

			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
 		}catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
	
}
