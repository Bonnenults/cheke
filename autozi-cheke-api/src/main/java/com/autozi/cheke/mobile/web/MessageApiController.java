package com.autozi.cheke.mobile.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.autozi.cheke.basic.entity.Notice;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.autozi.cheke.mobile.constant.MobileConstant;
import com.autozi.cheke.mobile.facade.MessageApiFacade;
import com.autozi.cheke.party.entity.ChekeLetterRelation;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.exception.B2bException;
import com.autozi.common.core.page.Page;

/**
 * 推客App端_消息功能
 * @author 魏唯
 *
 */
@RequestMapping("/message/tk/")
@Controller
public class MessageApiController extends BaseApiController {

	@Autowired
	private MessageApiFacade messageApiFacade;
	
	//发私信
	@RequestMapping("sendMessage.api")
	public void sendMessage(HttpServletRequest request, HttpServletResponse response, Long partyId, String content){
		try {
			User user = super.getUser(request);
			//是否登录
			if(user==null){
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
			if(StringUtils.isBlank(content)){
				throw new B2bException("发送内容不能为空");
			}
			if(content.length()>100){
				this.response(request,response, MobileConstant.Error._letterIs100,MobileConstant.Error._letterIs100_msg);
				return;
			}
			if(partyId == null){
				throw new B2bException("partyId不能为空");
			}
			messageApiFacade.sendMessage(user.getId(),partyId,content);
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg);
 		}catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
	
	//消息列表
	@RequestMapping("listMessage.api")
	public void listMessage(HttpServletRequest request, HttpServletResponse response){
		JSONObject data = new JSONObject();
		try {
			User user = super.getUser(request);
			//是否登录
			if(user==null){
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
			Page<Map<String,Object>> page = new Page<>();
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
	        filters.put("userId", user.getId());
			JSONArray taskArray = messageApiFacade.getTaskList(page, filters);
			data.put("curPageNo", page.getPageNo());
            data.put("totalPages", page.getTotalPages());
            data.put("taskArray", taskArray.toString());
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
 		}catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
	
	//消息记录
	@RequestMapping("messageHistory.api")
	public void messageHistory(HttpServletRequest request, HttpServletResponse response, Long relationId){
		try {
			User user = super.getUser(request);
			//是否登录
			if(user==null){
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
			JSONArray data = messageApiFacade.getMessageHistory(user.getId(),relationId);
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
 		}catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}

	//消息记录
	@RequestMapping("getMessage.api")
	public void getMessage(HttpServletRequest request, HttpServletResponse response, Long partyId){
		try {
			User user = super.getUser(request);
			//是否登录
			if(user==null){
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
			JSONArray data = messageApiFacade.getMessage(user.getId(),partyId);
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
		}catch (B2bException e){
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
	
	//通知列表
	@RequestMapping("listNotice.api")
	public void listNotice(HttpServletRequest request, HttpServletResponse response){
		JSONObject data = new JSONObject();
		try {
			User user = super.getUser(request);
			//是否登录
			if(user==null){
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
			Page<Notice> page = new Page<>();
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
	        filters.put("userId", user.getId());
			JSONArray noticeArray = messageApiFacade.getNoticeList(page, filters);
			data.put("curPageNo", page.getPageNo());
            data.put("totalPages", page.getTotalPages());
            data.put("noticeArray", noticeArray.toString());
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
 		}catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
	
	//通知详情
	@RequestMapping("noticeDetail.api")
	public void noticeDetail(HttpServletRequest request, HttpServletResponse response, Long noticeId){
		try {
			User user = super.getUser(request);
			//是否登录
			if(user==null){
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
			if(noticeId == null){
				throw new B2bException("noticeId不能为空");
			}
			JSONObject data = messageApiFacade.getNoticeDetail(user.getId(),noticeId);
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
