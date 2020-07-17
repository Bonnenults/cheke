package com.autozi.cheke.web.article.vo;

import com.autozi.cheke.util.web.BaseView;

import java.util.Date;

/**
 * Created by wang-lei on 2017/11/22.
 * 文章
 */
public class ArticleVo extends BaseView {

    private String title; //标题
    private Integer type; //资讯类别，1公司新闻，2行业新闻,31视频推广 32单图推广 33多图推广

    private String tag; //标签
    private String mark; //文章标记
    private String source; //来源

    private String image; //缩略图、单图、多图图1
    private String image2; //多图ad，图2
    private String image3; //多图ad，图1

    private String video_url;//视频Ad，视频url


    private String body; //正文

    private Integer aIsTop; //是否置顶 1置顶，0不置顶（暂时不用）

    private Integer aIsTask; //是否生成付费推广任务 1生成，0不生成

    private Integer aIsAd; //是否是广告 0否 1是

    private Integer status; //状态，0待发布，1审核中，2已发布，3已上线，4已下线，-1拒绝，-2取消
    private Integer channelType; //频道类别，1资讯，2培训视频，3项目,4广告

    private Integer displayType;//0文字 1单图小  2单图大 3多图 4视频

    private Date beginTime; //开始时间
    private Date endTime; //结束时间

    private Long createUserId;
    private Long updateUserId;

    private Long createPartyId;
    private Long updatePartyId;

    private Date createTime; //创建时间/数据新建的时间
    private Date updateTime; //修改时间

    private Date publishTime; //发布时间/上线时间
    private Date offlineTime; //下线时间

    private Date commitTime; //提交审核时间
    private Date passTime; //审核通过时间
    private Date autoEndTime; //自动完成结束时间


    private String refuseReason; // 拒绝理由

    private String intro; //简介


    //新增内容
    private Integer pageView; //浏览量
    private Integer shareAmount; //分享量


    private String typeName; //项目类型名称
    private String channelName; //频道名称
    private String statusName; //状态名称

    private Double mostCost; //单个任务最多获取费用


    private String partyClass; //车客属性
    private String companyName; //公司名称


    //信息查询新增字段
    private Double usedCost; //余额

    private Integer twitter; //推客人数
    private Integer leaveWords; //留言数量


    private Double allCost; //总投入费用
    private Double onceCost; //点击一次费用
    private Double cost;//已花费金客

    private String beginTimeStr;//任务开始时间
    private String publishTimeStr; //任务上线时间

    private Integer num;

    public Integer getNum() {   return num; }

    public void setNum(Integer num) {  this.num = num; }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Long getCreatePartyId() {
        return createPartyId;
    }

    public void setCreatePartyId(Long createPartyId) {
        this.createPartyId = createPartyId;
    }

    public Long getUpdatePartyId() {
        return updatePartyId;
    }

    public void setUpdatePartyId(Long updatePartyId) {
        this.updatePartyId = updatePartyId;
    }

    public Double getUsedCost() {
        return usedCost;
    }

    public void setUsedCost(Double usedCost) {
        this.usedCost = usedCost;
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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getPartyClass() {
        return partyClass;
    }

    public void setPartyClass(String partyClass) {
        this.partyClass = partyClass;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public Date getAutoEndTime() {
        return autoEndTime;
    }

    public void setAutoEndTime(Date autoEndTime) {
        this.autoEndTime = autoEndTime;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(Date offlineTime) {
        this.offlineTime = offlineTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getaIsTop() {
        return aIsTop;
    }

    public void setaIsTop(Integer aIsTop) {
        this.aIsTop = aIsTop;
    }

    public Integer getaIsTask() {
        return aIsTask;
    }

    public void setaIsTask(Integer aIsTask) {
        this.aIsTask = aIsTask;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getBeginTimeStr() {
        return beginTimeStr;
    }

    public void setBeginTimeStr(String beginTimeStr) {
        this.beginTimeStr = beginTimeStr;
    }

    public String getPublishTimeStr() {return publishTimeStr;}

    public void setPublishTimeStr(String publishTimeStr) {this.publishTimeStr = publishTimeStr;}
    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public Integer getaIsAd() {
        return aIsAd;
    }

    public void setaIsAd(Integer aIsAd) {
        this.aIsAd = aIsAd;
    }

    public Integer getDisplayType() {  return displayType; }

    public void setDisplayType(Integer displayType) {  this.displayType = displayType; }
}
