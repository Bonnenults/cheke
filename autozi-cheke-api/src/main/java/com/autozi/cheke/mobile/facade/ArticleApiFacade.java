package com.autozi.cheke.mobile.facade;

import com.autozi.cheke.article.entity.*;
import com.autozi.cheke.article.type.ArticleChannel;
import com.autozi.cheke.article.type.ArticleOrderStatus;
import com.autozi.cheke.article.type.ArticleTypeEnum;
import com.autozi.cheke.basic.entity.Properties;
import com.autozi.cheke.course.entity.CourseUserRelation;
import com.autozi.cheke.course.type.CourseStuStatus;
import com.autozi.cheke.mobile.web.RabbitmqUtil;
import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.service.article.*;
import com.autozi.cheke.service.basic.IPropertiesService;
import com.autozi.cheke.service.course.ICourseUserRelationService;
import com.autozi.cheke.service.party.IChekeFansService;
import com.autozi.cheke.service.party.IPartyService;
import com.autozi.cheke.service.search.IArticleSearchService;
import com.autozi.cheke.service.user.IChekeCollectionService;
import com.autozi.cheke.service.user.IUserService;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.exception.B2bException;
import com.autozi.common.core.page.Page;
import com.autozi.common.search.es.entity.SearchKeyWords;
import com.autozi.common.utils.util1.HtmlUtils;
import com.autozi.common.utils.util2.ApplicationProperties;
import com.autozi.common.utils.util2.CommonUtils;
import com.autozi.common.utils.web.HttpClientUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ArticleApiFacade {

	@Autowired
	private IArticleService articleService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IArticleOrderService articleOrderService;
	@Autowired
	private IPartyService partyService;
	@Autowired
	private IChekeFansService chekeFansService;
	@Autowired
	private IArticleShareService articleShareService;
    @Autowired
    private IArticleClickService articleClickService;
    @Autowired
	private ArticleRedisApiFacade articleRedisApiFacade;
	@Autowired
	private RabbitmqUtil rabbitmqUtil;
	@Autowired
	private IArticleTagService articleTagService;
	@Autowired
	private IArticleCountInfoService articleCountInfoService;
	@Autowired
	private IArticleSearchService articleSearchService;
    @Autowired
    private ICourseUserRelationService courseUserRelationService;
    @Autowired
    private CourseRedisApiFacade courseRedisApiFacade;
    @Autowired
    private PropertiesRedisApiFacade propertisRedisApiFacade;
    @Autowired
    private IPropertiesService propertiesService;
    @Autowired
    private IChekeCollectionService chekeCollectionService;


    public JSONArray findSearchTitle(String title) {
		JSONArray array = new JSONArray();
		List<SearchKeyWords> list = articleSearchService.findByTitle(title);

		if(list.size() > 10) {
			for(int i = 0; i < 10; i++) {
				SearchKeyWords key = list.get(i);
				JSONObject o = new JSONObject();
				o.put("keyWord",key.getTitle());
				array.add(o);
			}
		}else {
			for(SearchKeyWords keyWords : list) {
				JSONObject o = new JSONObject();
				o.put("keyWord",keyWords.getTitle());
				array.add(o);
			}
		}
		return array;
	}


	public JSONArray findArticleTag(String channelType) {
		JSONArray array = new JSONArray();
		Map<String,Object> param = new HashMap<>();
		param.put("channelType",channelType);
		List<ArticleTag> list = articleTagService.findArticleTagList(param);

		for(ArticleTag tag : list) {
			JSONObject o = new JSONObject();
			o.put("code",tag.getCode());
			o.put("name",tag.getName());
			o.put("id",tag.getId());
			array.add(o);
		}

		return array;
	}
	
	
	public JSONArray findArticleByPage(Page<Map<String, Object>> page, Map<String, Object> filters) throws Exception {
		JSONArray array = new JSONArray();
        Page<Article> _page = new Page<Article>();
        CommonUtils.pageConversion(page, _page);
        _page = articleService.findArticlePage(_page, filters);

        for (Article article : _page.getResult()) {
        	array.add(convertArticle(article));
        }
        CommonUtils.pageConversion(_page, page);
        return array;
	}

    public JSONArray findArticleByPageForIndex(Page<Map<String, Object>> page, Map<String, Object> filters,Map<String, Object> adFilters) throws Exception {
        JSONArray array = new JSONArray();
        Page<Article> _page = new Page<Article>();
        CommonUtils.pageConversion(page, _page);
        _page = articleService.findArticlePage(_page, filters);
        List<Article> adlist = articleService.findListForMap(adFilters);

        String space = propertisRedisApiFacade.getSpace();
        if(space == null){
            Map<String, Object> filter = new HashedMap();
            filter.put("popKey","SPACE");
            Properties properties = propertiesService.getProperties(filter);

            space = ( properties ==null ? "5" : properties.getValue());//若配置为空，则默认设为5
            propertisRedisApiFacade.saveSpace(space.toString());
        }
		if(Integer.valueOf(space) == 0){
			space = "5";//若配置为0，则默认设为5
		}
        int i=0;
        int j=(_page.getPageNo()-1)*(_page.getPageSize()/Integer.valueOf(space));
        for (Article article : _page.getResult()) {
            array.add(convertArticle(article));
            i++;
            if (i==Integer.valueOf(space) && adlist.size()!=0 && j<adlist.size()){
                array.add(convertArticle(adlist.get(j % adlist.size())));
                i=0;
                if(j<adlist.size()){
                    j++;
                }else{
                    j=0;
                }
            }
        }
        CommonUtils.pageConversion(_page, page);
        return array;
    }

	public JSONArray findArticleByFilter(Map<String, Object> filters) throws Exception {
		JSONArray array = new JSONArray();
        List<Article> list = articleService.findListForMap(filters);

		for (Article article : list) {
			array.add(convertAisCourse(article));
		}

		return array;
	}

    private JSONObject convertAisCourse(Article article) {
        JSONObject data = new JSONObject();
        //文章Id
        data.put("articleId", article.getId()==null?"":article.getId());
        //资讯类别
        data.put("type", article.getType()==null?"":article.getType());
        //资讯类别(咨询频道)
        data.put("typeCN", article.getType()==null?"": ArticleTypeEnum.getNameByType(article.getType()));
        data.put("courseId", article.getCourseId()==null?"":article.getCourseId());

        return data;
    }


    private JSONObject convertArticle(Article article) {
		JSONObject data = new JSONObject();
        JSONArray imgArry = new JSONArray();
		if(article == null){
			return null;
		}
		//文章Id
		data.put("articleId", article.getId()==null?"":article.getId());
        //课程Id
        data.put("ID", article.getCourseId()==null?"":article.getCourseId());
		//标题
		data.put("title", article.getTitle()==null?"":article.getTitle());
        //资讯类别
        data.put("type", article.getType()==null?"":article.getType());
		//资讯类别(咨询频道)
		data.put("typeCN", article.getType()==null?"": ArticleTypeEnum.getNameByType(article.getType()));
		//标签(项目培训)
		data.put("tag", article.getTag()==null?"":article.getTag());
        if(article.getImage()!=null && !"".equals(article.getImage())){
            imgArry.add(article.getImage());
        }
        if(article.getImage2()!=null && !"".equals(article.getImage2())){
            imgArry.add(article.getImage2());
        }
        if(article.getImage3()!=null && !"".equals(article.getImage3())){
            imgArry.add(article.getImage3());
        }

        //缩略图
        data.put("image", imgArry);

        //视频URL
        data.put("video_url", article.getVideo_url()==null?"":article.getVideo_url());
		//是否置顶
        if(article.getaIsTop()!=null && article.getaIsTop() > 0){
            String COUNT_SORT = propertisRedisApiFacade.getCountSort();
            if(COUNT_SORT == null){
                Map<String, Object> filter = new HashedMap();
                filter.put("popKey","COUNT_SORT");
                Properties properties = propertiesService.getProperties(filter);

                COUNT_SORT = properties==null ? "20" : properties.getValue();
                propertisRedisApiFacade.saveSpace(COUNT_SORT.toString());
            }
            //若文章为广告类则设置aIsTop为0。
            if(article.getChannelType()!=null && article.getChannelType()==ArticleChannel.AD.getType()){
                data.put("aIsTop", 0);
			}else{
                data.put("aIsTop", Math.abs(article.getaIsTop()-Integer.valueOf(COUNT_SORT)-1));
            }

        }else{
            data.put("aIsTop", article.getaIsTop()==null?"0":article.getaIsTop());
        }

		//是否推广
		data.put("aIsTask", article.getaIsTask()==null?"":article.getaIsTask());
		//频道类别
		data.put("channelType", article.getChannelType()==null?"":article.getChannelType());
        //展示类别
        data.put("displayType", article.getDisplayType()==null?"":article.getDisplayType());
		//频道类别（CN）
		data.put("channelTypeCN", article.getChannelType()==null?"":ArticleChannel.getChannelName(article.getChannelType()));
		//发布到推客的时间
		String publishTimeCN = "";
		Long publishTime = article.getPublishTime().getTime();
		Long nowTime = System.currentTimeMillis();
		if(nowTime - publishTime<60*60*1000){//1小时以内
			publishTimeCN = "刚刚";
		}else if(nowTime - publishTime < 60*60*1000*24){
			publishTimeCN = new SimpleDateFormat("HH:mm").format(article.getPublishTime());
		}else{
			publishTimeCN = new SimpleDateFormat("MM月dd日").format(article.getPublishTime());
		}
		data.put("publishTime", article.getPublishTime()==null?"":new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(article.getPublishTime()));
		data.put("publishTimeCN", publishTimeCN);
		//发布人，若为广告则显示来源
        if( !article.getChannelType().equals(ArticleChannel.AD.getType())){
            Map<String, Object> cheKeHao = this.getCheKeHao(article.getCreateUserId());
            data.put("chekeUserName", cheKeHao.get("partyName"));
        }else{
            data.put("chekeUserName", article.getSource() == null?"" : article.getSource());
        }

		//增加阅读量
		ArticleCountInfo countInfo = articleRedisApiFacade.getCountInfo(article.getId());
		data.put("pageView",countInfo == null ? 0 : countInfo.getPageView());
		return data;
	}

	/**
	 * 通过文章创建人的车客号
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getCheKeHao(Long userId){
		Map<String, Object> map = new HashMap<String, Object>();
		if(userId==null){
			throw new B2bException("userId为空");
		}
		User user = userService.getUserById(userId);
		if(user==null){
			throw new B2bException("用户查询为空");
		}
		Party party = partyService.getPartyById(user.getPartyId());
		map.put("partyId", party.getId());
		map.put("partyName", party.getName());
		//头像
		map.put("partyImageUrl", party.getImageUrl());
		return map;
	}
	

	public JSONObject getArticleDetail(Long articleId, User user) {
		JSONObject data = new JSONObject();
		Article article = articleRedisApiFacade.getArticle(articleId);

		if(article==null){
			article = articleService.getArticleById(articleId);
			try {

                article.setBody(HtmlUtils.readHtml(article.getBody()));

			} catch (Exception e) {
				e.printStackTrace();
			}
			articleRedisApiFacade.auditPassArticle(article);
		}
        String redisArticleBody = article.getBody();
        if(redisArticleBody.contains(".html") || redisArticleBody.contains("系统找不到指定的文件")){
            redisArticleBody="";
        }
        if(!redisArticleBody.contains("my-player")){
            String video_url = article.getVideo_url();
            String body = "";
            if(video_url!=null&&video_url.length()!=0){

                body = "<video id=\"my-player\" class=\"video-js vjs-big-play-centered\"  webkit-playsinline=\"true\" playsinline=\"true\" x5-video-player-type='h5' x5-video-player-fullscreen='true'  controls preload=\"auto\" data-setup='{}'>"
                        +"<source src="+video_url+" type='video/mp4' />"
                        +"</video>";

                String newBody = body+redisArticleBody;
                articleRedisApiFacade.saveArticleBody(article.getId(),newBody);

            }
        }

        Long courseId = article.getCourseId();

        if(courseId != null && !"".equals(courseId) && user != null){

            Map<String, Object> filters = new HashMap<String, Object>();
            filters.put("userId",user.getId());
            filters.put("articleId",articleId);
            filters.put("courseId",courseId);
            CourseUserRelation courseUserRelation = courseUserRelationService.findCourseUserRelationObj(filters);

            if(courseUserRelation==null) {
                addArtcileToMyPlan(courseId,user,courseUserRelation);
            }

        }
		//获取文章统计信息
		ArticleCountInfo articleCountInfo = articleRedisApiFacade.getCountInfo(articleId);
		if(articleCountInfo == null){
			articleCountInfo = articleCountInfoService.getCountInfoByArticleId(articleId);
			articleRedisApiFacade.saveArticleCountInfo(articleCountInfo);
			//throw new B2bException("文章统计数据为空"); 注意：暂时注掉，后期查看有没有影响
		}
		//获取文章点击信息
        ArticleClick articleClick = null;
        if(user!=null){
            Map<String, Object> filter = new HashMap<String, Object>();
            filter.put("userId",user.getId());
            filter.put("articleId",articleId);
            articleClick = articleClickService.getArticleClickbyFilter(filter);
        }

		//文章阅读量+1
		articleRedisApiFacade.countPageView(articleId);
		buildArtcileDetail(articleId, user, data, article, articleCountInfo,articleClick);
		return data;
	}

    /**
     * 加入我的学习计划
     */
    private void addArtcileToMyPlan(Long courseId, User user,CourseUserRelation courseUserRelation) {

		//根据articleID获取到courseId，然后取出所有的article

        Map<String, Object> filter = new HashMap<>();
        filter.put("courseId",courseId);

        List<Article> list = articleService.findListForMap(filter);

        for (Article aisCourse : list) {

			courseUserRelation = new CourseUserRelation();
            courseUserRelation.setId(System.currentTimeMillis());
			courseUserRelation.setArticleId(aisCourse.getId());
			courseUserRelation.setCourseId(aisCourse.getCourseId());
			courseUserRelation.setUserId(user.getId());
			courseUserRelation.setStatus(CourseStuStatus.WAITING.getType());
			courseUserRelation.setCreateTime(new Date());
			courseUserRelation.setCurrentProgress("0");
			courseUserRelation.setRanking(0);
			courseUserRelationService.addCourseUserRelation(courseUserRelation);
        }
        //课程学习人数+1
        courseRedisApiFacade.countAddStuNum(courseId);

    }

    /**
	 * 封装文章详情
	 * @param articleId
	 * @param user
	 * @param data
	 * @param article
	 * @param articleCountInfo
	 */
	private void buildArtcileDetail(Long articleId, User user, JSONObject data,
		Article article, ArticleCountInfo articleCountInfo,ArticleClick articleClick) {
		//文章是否已删除
		data.put("isDelete",article.getStatus().intValue() == -3 ? true : false);
		data.put("articleId",articleId);
		//标题
		data.put("title", article.getTitle());
		//发布人
		User cheke = userService.getUserById(article.getCreateUserId());
		Party party = partyService.getPartyById(cheke.getPartyId());

		//车客Id
		data.put("partyId", party.getId());
		//车客号
		data.put("chekeUserName", party.getName());
		//车客头像
		data.put("chekeUserImage", party.getImageUrl());
		//是否原创
		data.put("source", article.getSource()==null?"":article.getSource());
		if(article.getVideo_url() != null && article.getVideo_url().length()!=0){
			data.put("video_url", article.getVideo_url()==null?"":article.getVideo_url());
			String url = article.getVideo_url()+"?avinfo";
			try {
				JSONObject videoObj = JSONObject.fromObject(HttpClientUtil.httpGet(url,null));
				if(videoObj != null){
					JSONArray videoArr = JSONArray.fromObject(videoObj.get("streams"));
					int width =Integer.valueOf(JSONObject.fromObject( videoArr.get(0)).get("width").toString());
					int height =Integer.valueOf(JSONObject.fromObject( videoArr.get(0)).get("height").toString());
					data.put("width", width);
					data.put("height", height);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        //如果是课程，则返回课程相关信息,如进度
		if((Objects.equals(article.getChannelType(), ArticleChannel.COURSE.getType()) || article.getCourseId()!=null)
				&& user!=null){
            Map<String, Object> filters = new HashMap<>();
            filters.put("userId", user.getId());
            filters.put("articleId", articleId);
            CourseUserRelation courseUserRelation = courseUserRelationService.findCourseUserRelationObj(filters);
            data.put("currentProgress", courseUserRelation==null?"0" : courseUserRelation.getCurrentProgress() );
        }
        data.put("courseId", article.getCourseId()==null?"":article.getCourseId());

        String bodyUrl = ApplicationProperties.getValue("cheKeDomain")+"/article/tk/detailArticle.action?userId="+(user == null ? "" : user.getId())+"&articleId="+articleId;
		//文章内容
		data.put("body", bodyUrl);
		//点赞/点LOW
		if(articleClick == null){
            //什么都没点
            data.put("likesFlag", 0);
        }else{
            //点赞或者点LOW
            data.put("likesFlag", articleClick.getClickType()==null?"":articleClick.getClickType());
        }
		//点赞次数
		data.put("likesNum", articleCountInfo.getLikes()==null?"":articleCountInfo.getLikes());
		//点low次数
		data.put("dislikesNum", articleCountInfo.getDislikes()==null?"":articleCountInfo.getDislikes());
		//阅读量
		data.put("pageViewNum", articleCountInfo.getPageView()==null?"":articleCountInfo.getPageView());
        //是否收藏
		Integer isCollection = 0;
		if(user!=null){
			Boolean collectionFlag = chekeCollectionService.isCollected(articleId, user.getId());
			if(collectionFlag){
				isCollection = 1;
			}
		}
		data.put("isCollection", isCollection);
		//是否付费
		data.put("aIsTask", article.getaIsTask()==null?"":article.getaIsTask());
		data.put("onceCost",articleCountInfo.getOnceCost());
		data.put("endTime", article.getEndTime() == null ? "永久" : new SimpleDateFormat("yyyy.MM.dd").format(article.getEndTime()));
		data.put("createTime",new SimpleDateFormat("MM月dd日").format(article.getCreateTime()));

		String publishTimeCN = "";
		if(article.getPublishTime() != null) {
			Long publishTime = article.getPublishTime().getTime();
			Long nowTime = System.currentTimeMillis();
			if(nowTime - publishTime<60*60*1000){//1小时以内
				publishTimeCN = "刚刚";
			}else if(nowTime - publishTime < 60*60*1000*24){
				publishTimeCN = new SimpleDateFormat("HH:mm").format(article.getPublishTime());
			}else{
				publishTimeCN = new SimpleDateFormat("MM月dd日").format(article.getPublishTime());
			}
			data.put("publishTime", article.getPublishTime()==null?"":new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(article.getPublishTime()));
		}
		data.put("publishTimeCN", publishTimeCN);

		data.put("isDated",articleRedisApiFacade.checkArticleDated(articleId));
		data.put("isHaveMoney",articleRedisApiFacade.checkArticleIsHaveMoney(articleId));

		//留言,关注,是否领取任务
		if(user == null){
			data.put("userName", "");
			data.put("userPhone", "");
			data.put("isAttention", false);//没登录就没关注
			data.put("hasTask", false);
		}else{
			data.put("userName", user.getName()==null?"":user.getName());
			data.put("userPhone", user.getPhone()==null?"":user.getPhone());
			//是否关注过
			Boolean attentionFlag = chekeFansService.isAttention(user.getId(), article.getCreatePartyId());
			data.put("isAttention", attentionFlag);
			ArticleOrder articleOrder = articleOrderService.getOrderByArticleIdAndUserId(articleId, user.getId());
			data.put("hasTask", articleOrder==null?false:true);
		}
	}

	/**
	 * 增加分享点击量
	 * @param request
	 * @param articleId
	 */
	private void shareToIncrClickNum(HttpServletRequest request, Long articleId) {
		String type = request.getParameter("type");
		if (StringUtils.isNotBlank(type)) {
			if(type.equals("order")){
				//任务分享出去的
				String userId = request.getParameter("userId");
				ArticleOrder order = articleOrderService.getOrderByArticleIdAndUserId(articleId, Long.valueOf(userId));
				if(order!=null){
					if(ArticleOrderStatus.ACTIVATING.getType().equals(order.getStatus())){
						//任务没结束
						Integer clickNum = order.getClickNum()+1;
						order.setClickNum(clickNum);
						if(clickNum==order.getMaxClickNum()){
							//点击次数等于最大点击次数  将任务关闭
							order.setStatus(ArticleOrderStatus.ENDING.getType());
						}
					}
				}
			}else if(type.equals("share")){
				//普通分享
				String shareIp = request.getParameter("shareIp");
				Map<String, Object> filter = new HashMap<String, Object>();
				filter.put("articleId", articleId);
				filter.put("shareIp", shareIp);
				ArticleShare articleShare = articleShareService.getArticleShareByFilter(filter);
				if(articleShare!=null){
					Integer clickNum = articleShare.getClickNum()+1;
					articleShare.setClickNum(clickNum);
				}
			}
		}
	}

	public JSONObject detailArticleByShare(Long articleId, Long userId, Integer sharetype) throws Exception{
		JSONObject data = new JSONObject();
		Article article = articleService.getArticleById(articleId);
		if(article==null){
			throw new B2bException("没有该文章");
		}
		ArticleCountInfo articleCountInfo = articleRedisApiFacade.getCountInfo(articleId);
		if(articleCountInfo == null){
			throw new B2bException("文章统计数据为空");
		}
		// 统计调MQ
		if(userId!=null){
			ArticleMqEntity articleMqEntity = new ArticleMqEntity();
			articleMqEntity.setArticleId(articleId);
			articleMqEntity.setUserId(userId);
			articleMqEntity.setShareType(sharetype);
			rabbitmqUtil.sendMessageInJsonToExchange(articleMqEntity);
		}
		return data;
	}

	public Long deleteObj(Long articleId){
		return articleRedisApiFacade.deleteArticleCountInfo(articleId);
	}

	public Long deleteArticle(Long articleId){
		return articleRedisApiFacade.deleteArticle(articleId);
	}
}
