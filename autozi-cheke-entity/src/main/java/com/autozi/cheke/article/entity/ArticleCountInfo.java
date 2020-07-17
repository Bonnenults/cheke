package com.autozi.cheke.article.entity;

import com.autozi.common.dal.entity.IdEntity;

/**
 * Created by wang-lei on 2017/11/23.
 * 总的 统计信息
 */
public class ArticleCountInfo extends IdEntity{

    private static final long serialVersionUID = 517461239386777092L;

    private Long articleId;

    private Integer stuNum;
    private Integer completedNum;

    private Integer twitter; //推客
    private Integer leaveWords; //留言
    private Integer likes; //点赞
    private Integer dislikes; //点low


    private Integer pageView; //浏览量
    private Integer shareAmount; //分享量


    private Integer wxFriendsCircle; //朋友圈
    private Integer wxFriends; //微信好友

    private Integer qqZone; //QQ空间
    private Integer qqFriends; //QQ好友

    private Integer sina; //新浪微博



    private Double allCost; //总投入费用
    private Double onceCost; //点击一次费用
    private Double mostCost; //单个任务最多获取费用
    private Double usedCost; //剩余金额


    public Double getAllCost() {
		return allCost;
	}

	public void setAllCost(Double allCost) {
		this.allCost = allCost;
	}

	public Double getOnceCost() {
		return onceCost;
	}

	public void setOnceCost(Double onceCost) {
		this.onceCost = onceCost;
	}

	public Double getMostCost() {
		return mostCost;
	}

	public void setMostCost(Double mostCost) {
		this.mostCost = mostCost;
	}

	public Double getUsedCost() {
		return usedCost;
	}

	public void setUsedCost(Double usedCost) {
		this.usedCost = usedCost;
	}

	public Integer getPageView() {
        return pageView;
    }

    public void setPageView(Integer pageView) {
        this.pageView = pageView;
    }

    public Integer getShareAmount() {
        return shareAmount;
    }

    public void setShareAmount(Integer shareAmount) {
        this.shareAmount = shareAmount;
    }

    public Integer getWxFriendsCircle() {
        return wxFriendsCircle;
    }

    public void setWxFriendsCircle(Integer wxFriendsCircle) {
        this.wxFriendsCircle = wxFriendsCircle;
    }

    public Integer getWxFriends() {
        return wxFriends;
    }

    public void setWxFriends(Integer wxFriends) {
        this.wxFriends = wxFriends;
    }

    public Integer getQqZone() {
        return qqZone;
    }

    public void setQqZone(Integer qqZone) {
        this.qqZone = qqZone;
    }

    public Integer getQqFriends() {
        return qqFriends;
    }

    public void setQqFriends(Integer qqFriends) {
        this.qqFriends = qqFriends;
    }

    public Integer getSina() {
        return sina;
    }

    public void setSina(Integer sina) {
        this.sina = sina;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Integer getTwitter() {
        return twitter;
    }

    public void setTwitter(Integer twitter) {
        this.twitter = twitter;
    }

    public Integer getLeaveWords() {
        return leaveWords;
    }

    public void setLeaveWords(Integer leaveWords) {
        this.leaveWords = leaveWords;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    public Integer getCompletedNum() {  return completedNum; }

    public void setCompletedNum(Integer completedNum) {  this.completedNum = completedNum; }

    public Integer getStuNum() {
        return stuNum;
    }

    public void setStuNum(Integer stuNum) {
        this.stuNum = stuNum;
    }
}
