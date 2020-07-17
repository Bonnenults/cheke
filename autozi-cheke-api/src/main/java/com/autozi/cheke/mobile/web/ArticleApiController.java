package com.autozi.cheke.mobile.web;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.article.entity.ArticleCountInfo;
import com.autozi.cheke.article.type.ArticleChannel;
import com.autozi.cheke.article.type.ArticleStatus;
import com.autozi.cheke.mobile.constant.MobileConstant;
import com.autozi.cheke.mobile.facade.ArticleApiFacade;
import com.autozi.cheke.mobile.facade.ArticleFunctionApiFacade;
import com.autozi.cheke.mobile.facade.ArticleRedisApiFacade;
import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.service.article.IArticleService;
import com.autozi.cheke.service.basic.IPropertiesService;
import com.autozi.cheke.service.party.IPartyService;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.exception.B2bException;
import com.autozi.common.core.page.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * 推客App端_首页推荐_培训_资讯_项目列表_搜索
 * @author 魏唯
 *
 */
@RequestMapping("/article/tk/")
@Controller
public class ArticleApiController extends BaseApiController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ArticleApiFacade articleApiFacade;

	@Autowired
	private ArticleRedisApiFacade articleRedisApiFacade;

	@Autowired
	private IPartyService partyService;

	@Autowired
	private ArticleFunctionApiFacade articleFunctionApiFacade;

	@Autowired
	private IPropertiesService propertiesService;

	/**
     * 首页列表		articleList
     * @param request
     * @param response
     */
	@RequestMapping("index/listArticle.api")
	public void listArticle(HttpServletRequest request, HttpServletResponse response){
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
			//封装查询条件
			Map<String, Object> filters = new HashMap<String, Object>();
            Map<String, Object> adFilters = new HashMap<String, Object>();
			String searchKeyWord = request.getParameter("searchKeyWord");
			if (StringUtils.isNotBlank(searchKeyWord)) {
				filters.put("searchKeyWord", searchKeyWord);
			}

//			Map<String , Object> filter = new HashedMap();
//			filter.put("popKey","COUNT_SORT");
//			int COUNT_SORT= Integer.valueOf(propertiesService.getProperties(filter).getValue().toString());

//            String sortCondition="IF(a_is_top>0 ,1,"+COUNT_SORT+"),a_is_top ,publish_time DESC";
            //文章状态：2已上线
			filters.put("status", ArticleStatus.PUBLISH.getType());
			filters.put("orderBy", "concat(ar.A_IS_TOP,ar.PUBLISH_TIME) desc");
            //filters.put("orderBy", sortCondition);
			filters.put("notAdFlag",true);

            adFilters.put("channelType", ArticleChannel.AD.getType());
            adFilters.put("status", ArticleStatus.PUBLISH.getType());
            adFilters.put("orderBy", "concat(ar.A_IS_TOP,ar.PUBLISH_TIME) desc");

			//JSONArray list = articleApiFacade.findArticleByPage(page, filters);
			JSONArray list = articleApiFacade.findArticleByPageForIndex(page, filters,adFilters);
            //需要将广告插入到文章list中

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

	@RequestMapping("/index/listKeyWords.api")
	public void listKeyWords(HttpServletRequest request,HttpServletResponse response,String keywords) {
		JSONObject data = new JSONObject();
		data.put("list", articleApiFacade.findSearchTitle(keywords));
		this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
	}


	@RequestMapping("/index/listArticleTag.api")
	public void listArticleTag(HttpServletRequest request,HttpServletResponse response,String channelType) {
		JSONObject data = new JSONObject();
		data.put("list", articleApiFacade.findArticleTag(channelType));
		this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
	}
	
	
	/**
	 * 推荐列表		recomendList
	 * @param request
	 * @param response
	 */
	@RequestMapping("index/listHotArticle.api")
	public void listHotArticle(HttpServletRequest request, HttpServletResponse response){
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
	        //封装查询条件
			Map<String, Object> filters = new HashMap<String, Object>();
			String searchKeyWord = request.getParameter("searchKeyWord");
			if (StringUtils.isNotBlank(searchKeyWord)) {
				filters.put("searchKeyWord", searchKeyWord);
			}
			filters.put("status", ArticleStatus.PUBLISH.getType());//2已上线
			filters.put("orderBy", "ar.PUBLISH_TIME desc");

			JSONArray list = articleApiFacade.findArticleByPage(page, filters);
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
	//培训列表		trainList
	@RequestMapping("index/listTrainArticle.api")
	public void listTrainArticle(HttpServletRequest request, HttpServletResponse response){
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
	        //封装查询条件
			Map<String, Object> filters = new HashMap<String, Object>();
//			String searchKeyWord = request.getParameter("searchKeyWord");
//			if (StringUtils.isNotBlank(searchKeyWord)) {
//				filters.put("searchKeyWord", searchKeyWord);
//			}
			String tagId = request.getParameter("tagId");
			if(StringUtils.isNotBlank(tagId)) {
				filters.put("tagId",tagId);
			}
			filters.put("channelType", ArticleChannel.VIDEO.getType());
			filters.put("status", ArticleStatus.PUBLISH.getType());
			filters.put("orderBy", "ar.PUBLISH_TIME desc");
			JSONArray list = articleApiFacade.findArticleByPage(page, filters);
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
	//资讯列表		informationList
	@RequestMapping("index/listInforArticle.api")
	public void listInforArticle(HttpServletRequest request, HttpServletResponse response){
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
	        //封装查询条件
			Map<String, Object> filters = new HashMap<String, Object>();
//			String searchKeyWord = request.getParameter("searchKeyWord");
//			if (StringUtils.isNotBlank(searchKeyWord)) {
//				filters.put("searchKeyWord", searchKeyWord);
//			}
			String tagId = request.getParameter("tagId");
			if(StringUtils.isNotBlank(tagId)) {
				filters.put("tagId",tagId);
			}
			filters.put("channelType", ArticleChannel.ZIXUN.getType());
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
	//项目列表		projectList
	@RequestMapping("index/listProjectArticle.api")
	public void listProjectArticle(HttpServletRequest request, HttpServletResponse response){
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
	        //封装查询条件
			Map<String, Object> filters = new HashMap<String, Object>();
//			String searchKeyWord = request.getParameter("searchKeyWord");
//			if (StringUtils.isNotBlank(searchKeyWord)) {
//				filters.put("searchKeyWord", searchKeyWord);
//			}
			String tagId = request.getParameter("tagId");
			if(StringUtils.isNotBlank(tagId)) {
				filters.put("tagId",tagId);
			}
			filters.put("channelType", ArticleChannel.PROJECT.getType());
			filters.put("status", ArticleStatus.PUBLISH.getType());
			filters.put("orderBy", "ar.PUBLISH_TIME desc");
			JSONArray list = articleApiFacade.findArticleByPage(page, filters);
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
	
	/* 搜索框的打开,和历史记录的显示
	 * 历史记录的删除
	 * 搜索结果展示
	 */
	@RequestMapping("index/searchArticleKeyWord.api")
	public void searchArticleKeyWord(HttpServletRequest request, HttpServletResponse response){
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
	        //封装查询条件
	        Map<String, Object> filters = new HashMap<String, Object>();
			String keyWord = request.getParameter("keyWord");
			if (StringUtils.isNotBlank(keyWord)) {
				filters.put("keyWord", keyWord);
			}
			String channelType = request.getParameter("channelType");
			if (StringUtils.isNotBlank(channelType)) {
				filters.put("channelType", Integer.valueOf(channelType));
			}
			String tagId = request.getParameter("tagId");
			if(StringUtils.isNotBlank(tagId)) {
				filters.put("tagId",tagId);
			}
			filters.put("status", ArticleStatus.PUBLISH.getType());
			filters.put("orderBy", "ar.PUBLISH_TIME desc");
			JSONArray list = articleApiFacade.findArticleByPage(page, filters);
			data.put("list", list);
			data.put("pageNo", page.getPageNo());
			data.put("totalPages", page.getTotalPages());
			data.put("keyWord", keyWord);
			data.put("channelType", channelType);
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
	 * 文章详细内容的展示
	 * @param request
	 * @param response
	 * @param articleId
	 */
	@RequestMapping("index/detailArticle.api")
	public void detailArticle(HttpServletRequest request, HttpServletResponse response, Long articleId){
		try {
			User user = super.getUser(request);
			if (articleId==null) {
				throw new B2bException("articleId为空");
			}
			JSONObject data = articleApiFacade.getArticleDetail(articleId,user);

			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
 		}catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}

	//=====================H5=====================
	/**
	 * 分享出去的文章详细内容的展示（统计接口）
	 * @param articleId
	 */
	@RequestMapping("detailArticleByShare.action")
	public String detailArticleByShare(Model uiModel, Long articleId, Long userId, Integer shareType){
		if (articleId==null) {
			throw new B2bException("articleId为空");
		}

		Article article = articleRedisApiFacade.getArticle(articleId);
		Party party = partyService.getPartyById(article.getCreatePartyId());
		ArticleCountInfo countInfo = articleRedisApiFacade.getCountInfo(articleId);

		uiModel.addAttribute("article",article);
		uiModel.addAttribute("party",party);
		uiModel.addAttribute("countInfo",countInfo);
		uiModel.addAttribute("userId",userId);

		try {
			log.info("发送MQ消息，articleId：{}，userId：{}，shareType：{}",articleId,userId,shareType);
			countCommit(articleId,userId,shareType);
			log.info("发送MQ消息END....");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "/index.html";
	}


	private void countCommit(final Long articleId, final Long userId,final Integer shareType) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					articleApiFacade.detailArticleByShare(articleId,userId,shareType);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * APP内部Body内容
	 * @param articleId
	 */
	@RequestMapping("detailArticle.action")
	public String detailArticle(Model uiModel, Long articleId, Long userId){

		User user = getUser(userId);

		Article article = articleRedisApiFacade.getArticle(articleId);
		Party party = partyService.getPartyById(article.getCreatePartyId());
		ArticleCountInfo countInfo = articleRedisApiFacade.getCountInfo(articleId);
		uiModel.addAttribute("article",article);
		uiModel.addAttribute("party",party);
		uiModel.addAttribute("countInfo",countInfo);
		uiModel.addAttribute("user",user);

		return "/detial_ajax";
	}


	@RequestMapping("saveLeaveWord.action")
	@ResponseBody
	public JSONObject saveLeaveWord(HttpServletRequest request, Long articleId, String userName, String userPhone, String leaveWords,Long userId){
		JSONObject json = new JSONObject();
		try {
			User user = super.getUser(userId);
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
			json.put("code","ok");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code","err");
		}
		return json;
	}

	//点赞和点low
	@RequestMapping("clickLikeOrDisLike.action")
	@ResponseBody
	public JSONObject clickLikeOrDisLike(HttpServletRequest request, HttpServletResponse response, Long articleId, Integer action,Long userId){
		JSONObject json = new JSONObject();
		try {
			User user = super.getUser(userId);
			String ip = request.getRemoteAddr();
			log.info("点赞类型：{},文章ID：{}",action,articleId);
			articleFunctionApiFacade.clickLikeOrDisLike(articleId, action,user,ip);
			json.put("code","ok");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code","err");
		}
		return json;
	}

	@RequestMapping("deleteArticleCount.action")
	public Long deleteArticleCount(Long articleId){


		Long deleteArticleId=articleApiFacade.deleteObj(articleId);

		return deleteArticleId;
	}

	@RequestMapping("deleteRedisArticle.action")
	public Long deleteRedisArticle(Long articleId){


		Long deleteArticleId=articleApiFacade.deleteArticle(articleId);

		return deleteArticleId;
	}

}
