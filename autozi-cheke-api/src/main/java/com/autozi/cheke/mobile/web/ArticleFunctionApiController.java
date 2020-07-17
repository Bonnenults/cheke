package com.autozi.cheke.mobile.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.autozi.cheke.article.entity.ArticleCountInfo;
import com.autozi.cheke.article.type.ArticleChannel;
import com.autozi.cheke.article.type.ArticleStatus;
import com.autozi.cheke.mobile.facade.ArticleApiFacade;
import com.autozi.cheke.mobile.facade.ArticleRedisApiFacade;
import com.autozi.cheke.mobile.facade.UserApiFacade;
import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.service.article.IArticleCountInfoService;
import com.autozi.common.core.page.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.autozi.cheke.article.type.ArticleClickType;
import com.autozi.cheke.mobile.constant.MobileConstant;
import com.autozi.cheke.mobile.facade.ArticleFunctionApiFacade;
import com.autozi.cheke.service.article.IArticleClickService;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.exception.B2bException;

import java.util.HashMap;
import java.util.Map;

/**
 * 文章内的功能
 * 			留言_点赞_关注_分享 
 * @author 魏唯
 *
 */
@RequestMapping("/articleFunction/tk/")
@Controller
public class ArticleFunctionApiController extends BaseApiController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ArticleFunctionApiFacade articleFunctionApiFacade ;
	@Autowired
	private ArticleApiFacade articleApiFacade ;
	@Autowired
	private UserApiFacade userApiFacade ;
	@Autowired
    private ArticleRedisApiFacade articleRedisApiFacade;
	@Autowired
    private IArticleCountInfoService articleCountInfoService;
	
	/**
	 * 留言提交保存
	 * @param request
	 * @param response
	 * @param articleId
	 * @param userName
	 * @param userPhone
	 * @param leaveWords
	 */
	@RequestMapping("saveLeaveWord.api")
	public void saveLeaveWord(HttpServletRequest request, HttpServletResponse response, Long articleId, String userName, String userPhone, String leaveWords){
		try {
			User user = super.getUser(request);
			if (articleId==null) {
				throw new B2bException("articleId为空");
			}
			if(StringUtils.isBlank(userName)){
				throw new B2bException("姓名不能为空");
			}
			if(StringUtils.isBlank(userPhone)){
				throw new B2bException("手机号不能为空");
			}
			if(StringUtils.isBlank(leaveWords)){
				throw new B2bException("留言不能为空");
			}
			articleFunctionApiFacade.saveLeaveWords(articleId,userName,userPhone,leaveWords,user);
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg);
 		}catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
	
	
	//关注(强制登录)  文章内的关注功能，关注车客
	@RequestMapping("goAttentionCK.api") 
	public void goAttentionCK(HttpServletRequest request, HttpServletResponse response, Long partyId){
		try {
			User user = super.getUser(request);
			//是否登录
			if(user==null){
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
			JSONObject obj = articleFunctionApiFacade.goAttentionCK(partyId,user.getId());
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,obj.toString());
 		}catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}

	//关注(强制登录)  关注列表
	@RequestMapping("attentionList.api")
	public void attentionList(HttpServletRequest request, HttpServletResponse response){
		JSONObject data = new JSONObject();
		try {
			User user = super.getUser(request);
			//是否登录
			if(user==null){
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
			//封装分页
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
			//封装查询条件
			Map<String, Object> filters = new HashMap<String, Object>();
			filters.put("userId", user.getId());
			JSONArray list = articleFunctionApiFacade.findAttentionByPage(page, filters);
			data.put("list", list);
			data.put("pageNo", page.getPageNo());
			data.put("totalPages", page.getTotalPages());
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
		}catch (B2bException e){
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}


    //关注(强制登录)  车客详情
	@RequestMapping("attentionParty.api")
	public void attentionParty(HttpServletRequest request, HttpServletResponse response,Long partyId){
		JSONObject data = new JSONObject();
		try {
			User user = super.getUser(request);
			if(user==null){
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
			JSONObject obj = articleFunctionApiFacade.getPartyById(partyId,user);
			data.put("party", obj);
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
		}catch (B2bException e){
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}

	//车客资讯列表
	@RequestMapping("listPartyArticle.api")
	public void listPartyArticle(HttpServletRequest request, HttpServletResponse response,Integer channelType,Long partyId){
		JSONObject data = new JSONObject();
		try {
			//封装分页
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
			User user = userApiFacade.getUserByPartyId(partyId);
			if(user==null){
				this.response(request,response, MobileConstant.Error._partyIsNull, MobileConstant.Error._partyIsNull_msg,data.toString());
			}
			Long userId = user.getId();
			//封装查询条件
			Map<String, Object> filters = new HashMap<String, Object>();
			filters.put("channelType", channelType);
			filters.put("createUserId", userId);
			filters.put("status", ArticleStatus.PUBLISH.getType());
			filters.put("orderBy", "ar.PUBLISH_TIME desc");
			JSONArray list = articleApiFacade.findArticleByPage(page, filters);
			data.put("pageNo", page.getPageNo());
			data.put("totalPages", page.getTotalPages());
			data.put("list", list.toString());
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
		}catch (B2bException e){
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}

	//点赞和点low
	@RequestMapping("clickLikeOrDisLike.api")
	public void clickLikeOrDisLike(HttpServletRequest request, HttpServletResponse response, Long articleId, Integer action){
		try {
			User user = super.getUser(request);
			String ip = request.getRemoteAddr();
			log.info("点赞类型：{},文章ID：{}",action,articleId);
			articleFunctionApiFacade.clickLikeOrDisLike(articleId, action,user,ip);
			//获取统计信息
            ArticleCountInfo articleCountInfo = articleRedisApiFacade.getCountInfo(articleId);
            if(articleCountInfo == null){
                articleCountInfo = articleCountInfoService.getCountInfoByArticleId(articleId);
                articleRedisApiFacade.saveArticleCountInfo(articleCountInfo);
            }
            JSONObject json = new JSONObject();
            json.put("likesFlag",action);
            json.put("likesNum",articleCountInfo.getLikes()==null?"":articleCountInfo.getLikes());
            json.put("dislikesNum",articleCountInfo.getDislikes()==null?"":articleCountInfo.getDislikes());
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg, json.toString());
 		}catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
	
	//普通分享
	@RequestMapping("share/shareArticle.api")
	public void shareArticle(HttpServletRequest request, HttpServletResponse response, Long articleId, Integer shareType){
		try {
			User user = super.getUser(request);
			if (articleId==null) {
				throw new B2bException("articleId为空");
			}
			if(shareType==null){
				throw new B2bException("分享类型不能为空");
			}
			JSONObject data = articleFunctionApiFacade.shareArticle(articleId, shareType,user);
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
 		}catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
	
	/**
	 * 分享完以后更新分享量
	 * @param request
	 * @param response
	 * @param articleId
	 * @param shareType
	 */
	@RequestMapping("share/shareArticleCallback.api")
	public void shareArticleCallback(HttpServletRequest request, HttpServletResponse response, Long articleId, Integer shareType){
		try {
			User user = super.getUser(request);
			if (articleId==null) {
				throw new B2bException("articleId为空");
			}
			if(shareType==null){
				throw new B2bException("分享类型不能为空");
			}
			String ip = request.getRemoteAddr();
			articleFunctionApiFacade.shareArticleCallback(articleId, shareType,ip,user);
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg);
 		}catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}


    /**
     * 评论
     * @param request
     * @param response
     * @param articleId
     * @param userName
     * @param userPhone
     * @param leaveWords
     */
    @RequestMapping("saveComment.api")
    public void saveComment(HttpServletRequest request, HttpServletResponse response, Long articleId, String userName, String userPhone, String leaveWords){
        try {
            User user = super.getUser(request);
            if (articleId==null) {
                throw new B2bException("articleId为空");
            }
            if(StringUtils.isBlank(userName)){
                throw new B2bException("姓名不能为空");
            }
            if(StringUtils.isBlank(userPhone)){
                throw new B2bException("手机号不能为空");
            }
            if(StringUtils.isBlank(leaveWords)){
                throw new B2bException("留言不能为空");
            }
            articleFunctionApiFacade.saveLeaveWords(articleId,userName,userPhone,leaveWords,user);
            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg);
        }catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }
}
