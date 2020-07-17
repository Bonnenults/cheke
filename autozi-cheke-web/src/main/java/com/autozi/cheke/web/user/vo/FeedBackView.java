package com.autozi.cheke.web.user.vo;


import com.autozi.cheke.util.web.BaseView;

import java.util.Date;

/**
 * @Auther: mingxin.li
 * @Date: 2018-06-15 17:40
 * @Description:
 */

public class FeedBackView extends BaseView {
    private String title;
    private String content;
    private String contactInfo;
    private Date createTime;//  创建时间
    private Long createUserId; //创建人ID

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
}
