package com.autozi.cheke.mobile.facade;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.article.entity.ArticleClick;
import com.autozi.cheke.article.entity.ArticleLeaveWord;
import com.autozi.cheke.article.entity.ArticleShare;
import com.autozi.cheke.article.type.ArticleClickType;
import com.autozi.cheke.party.entity.ChekeFans;
import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.service.article.*;
import com.autozi.cheke.service.party.IChekeFansService;
import com.autozi.cheke.service.party.IPartyService;
import com.autozi.cheke.service.user.IChekeCollectionService;
import com.autozi.cheke.service.user.IUserService;
import com.autozi.cheke.user.entity.ChekeCollection;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.util2.ApplicationProperties;
import com.autozi.common.utils.util2.CommonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ArticleFunctionApiFacade {

	@Autowired
	private IArticleService articleService;
	@Autowired
	private IChekeFansService chekeFansService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IArticleClickService articleClickService;
	@Autowired
	private IArticleOrderService articleOrderService;
	@Autowired
	private IArticleLeaveWordService articleLeaveWordService;
	@Autowired
	private IArticleShareService articleShareService;
	@Autowired
	private ArticleRedisApiFacade articleRedisApiFacade;
	@Autowired
	private IPartyService partyService;


    /**
     * 留言保存
     * @param articleId
     * @param userName
     * @param userPhone
     * @param leaveWords
     * @param user
     * @return
     */
	public void saveLeaveWords(Long articleId, String userName, String userPhone, String leaveWords, User user) {
		ArticleLeaveWord articleLeaveWord = new ArticleLeaveWord();
		articleLeaveWord.setArticleId(articleId);
		Article article = articleService.getArticleById(articleId);
		articleLeaveWord.setLeaveTitle(article.getTitle());
		articleLeaveWord.setLeaveName(userName);
		articleLeaveWord.setLeavePhone(userPhone);
		articleLeaveWord.setLeaveMessage(leaveWords);
		articleLeaveWord.setCreateTime(new Date());
		articleLeaveWord.setUpdateTime(new Date());
		articleLeaveWord.setChannelType(article.getChannelType());
		articleLeaveWord.setChekeUserId(article.getCreateUserId());
		User cheke = userService.getUserById(article.getCreateUserId());
		if(cheke!=null){
			articleLeaveWord.setChekePartyId(cheke.getPartyId());
		}
		if (user==null) {
			//未登录   推客名称直接写死 推客手机号也写死
			articleLeaveWord.setCreateUserName("游客身份");
			articleLeaveWord.setCreateUserPhone("无");
		}else{
			articleLeaveWord.setCreateUserId(user.getId());
			articleLeaveWord.setCreateUserName(user.getName());
			articleLeaveWord.setCreateUserPhone(user.getPhone());
		}
		articleRedisApiFacade.countArticleInfo(ArticleClickType.LEAVEWORDS.getType(), articleLeaveWord.getArticleId(),false);
		articleLeaveWordService.addLeaveWord(articleLeaveWord);
	}


	public JSONObject goAttentionCK(Long partyId, Long userId) {
		chekeFansService.goAttentionCK(partyId, userId);
		JSONObject obj = new JSONObject();
		boolean isAttention = chekeFansService.isAttention(userId, partyId);
		obj.put("isAttention",isAttention);
		return obj;
	}


	/**
	 * 点赞/点low
	 * @param articleId
	 * @param action
	 * @param user
	 * @param ip
     */
	public void clickLikeOrDisLike(Long articleId, Integer action, User user, String ip) {
	    Map<String,Object> filter = new HashMap<>();
	    filter.put("articleId",articleId);
        filter.put("userId",user.getId());
        ArticleClick articleClick = articleClickService.getArticleClickbyFilter(filter);
        Boolean flag = false;

        if(articleClick == null){
            articleClick = new ArticleClick();
            articleClick.setArticleId(articleId);
            articleClick.setClickTime(new Date());
            articleClick.setClickIp(ip);
            articleClick.setUserId(user.getId());
            articleClick.setClickType(action);

            articleRedisApiFacade.countArticleInfo(action, articleClick.getArticleId(),false);
            articleClickService.addArticleClick(articleClick,action);

        }else{
/*            if(action == ArticleClickType.LIKES.getType()
                    && articleClick.getClickType() == ArticleClickType.DISLIKES.getType()){
                flag = true;
            }
            if(action == ArticleClickType.DISLIKES.getType()
                    && articleClick.getClickType() == ArticleClickType.LIKES.getType()){
                flag = true;
            }*/
            if(articleClick.getClickType() == ArticleClickType.LIKES.getType()
                    || articleClick.getClickType() == ArticleClickType.DISLIKES.getType()){
                flag = true;
            }

            articleClick.setClickTime(new Date());
            articleClick.setClickType(action);
            articleRedisApiFacade.countArticleInfo(action, articleClick.getArticleId(),flag);
            articleClickService.updataArticleClick(articleClick,action);


        }

	}

    /**
     * 分享文章
     * @param articleId
     * @param shareType
     * @param user
     * @return
     */
	public JSONObject shareArticle(Long articleId, Integer shareType,User user) {
		JSONObject data = new JSONObject();
		Article article = articleService.getArticleById(articleId);
		String shareURL = ApplicationProperties.getValue("cheKeDomain")+"/article/tk/detailArticleByShare.action?&userId="+(user == null ? "" : user.getId())+"&articleId="+articleId+"&shareType="+shareType;
		data.put("title", article.getTitle());
		if(article.getImage() != null && !"".equals(article.getImage())) {
			data.put("image", article.getImage());
		}else {
			data.put("image", ApplicationProperties.getValue("cheKeDomain") + "/images/ck-logo.jpg");
		}
		data.put("intro",article.getIntro() == null ? " " : article.getIntro());
		data.put("shareURL", shareURL);
		return data;
	}

	public void shareArticleCallback(Long articleId, Integer shareType, String ip, User user) {
		if(user!=null&&user.getId()!=null){
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put("articleId", articleId);
			filter.put("shareType", shareType);
			filter.put("userId", user.getId());
			ArticleShare share = articleShareService.getArticleShareByFilter(filter);
			if(share==null){
				ArticleShare articleShare = new ArticleShare();
				articleShare.setArticleId(articleId);
				articleShare.setShareTime(new Date());
				articleShare.setUserId(user.getId());
				articleShare.setType(shareType);
				articleShare.setShareIp(ip);
				articleShare.setClickNum(0);
				Long shareId = articleShareService.addArticleShareAndUpdataCountInfo(articleShare);
				articleShare.setId(shareId);

				articleRedisApiFacade.afterNormalShare(articleShare);
				articleRedisApiFacade.countArticleInfo(shareType,articleId,false);
				articleRedisApiFacade.countShareAmount(articleId);

			}
		}
	}

	public JSONArray findAttentionByPage(Page<Map<String, Object>> page, Map<String, Object> filters) throws Exception {
		JSONArray array = new JSONArray();
		Page<ChekeFans> _page = new Page<>();
		CommonUtils.pageConversion(page, _page);
		_page = chekeFansService.findPageForMap(_page, filters);
		for (ChekeFans chekeFans : _page.getResult()) {
			JSONObject obj = new JSONObject();
			Long partyId = chekeFans.getPartyId();
			Party party = partyService.getPartyById(partyId);
			if(party==null){
				continue;
			}
			String partyName = party.getName()==null?"":party.getName();
			String imageUrl = party.getImageUrl()==null?"":party.getImageUrl();
			String description= party.getDescription()==null?"":party.getDescription();
			//查看发布文章
			Map<String, Object> articleFilters = new HashMap<>();
			articleFilters.put("createPartyId", partyId);
			articleFilters.put("publishTimeStart",chekeFans.getUpdateTime());
			List<Article> list = articleService.findListForMap(articleFilters);
			String articleCount = "";
			boolean isRead = true;
			if(list!=null&&list.size()>0){
				articleCount = String.valueOf(list.size());
				isRead = false;
			}
			obj.put("partyId",partyId);
			obj.put("partyName",partyName);
			obj.put("partyImgUrl",imageUrl);
			obj.put("description", description);
			obj.put("isRead", isRead);
			obj.put("articleCount", articleCount);
			array.add(obj);
		}
		CommonUtils.pageConversion(_page, page);
		return array;
	}

	public JSONObject getPartyById(Long partyId,User user){
		ChekeFans chekeFans = chekeFansService.getChekeFans(partyId, user.getId());
		if(chekeFans!=null){
			chekeFansService.update(chekeFans);
		}
		JSONObject obj = new JSONObject();
		Party party = partyService.getPartyById(partyId);
		obj.put("partyId",party.getId());
		obj.put("partyName", party.getName() == null ? "" : party.getName());
		obj.put("imageUrl", party.getImageUrl() == null ? "" : party.getImageUrl());
		Integer fansCount = chekeFansService.getChekeFansCount(party.getId());
		obj.put("fansCount", fansCount == null ? 0 : fansCount.intValue());
		boolean isAttention = chekeFansService.isAttention(user.getId(), partyId);
		obj.put("isAttention",isAttention);
		return obj;
	}

}
