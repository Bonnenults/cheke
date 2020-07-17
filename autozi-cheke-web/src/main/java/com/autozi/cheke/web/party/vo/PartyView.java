package com.autozi.cheke.web.party.vo;

import com.autozi.cheke.util.web.BaseView;

import java.util.Date;

/**
 * Created by lbm on 2017/11/29.
 */
public class PartyView extends BaseView {
    private String code;                       //系统内部编码
    private String name;                       //
    private String companyName;               //车客：企业全称 个人类型代表公司名称
    private String description;               //车客：公司简介
    private String nameFirstLetter;          //名称首字母
    private String email;                     //企业邮箱
    private String phone;                     //联系电话
    private String address;                   //车客：企业地址
    private Long areaId;                      //车客：所在区
    private String socialCreditCode;        //车客：企业社会信息用代码
    private String invoiceTitle;            //车客：发票抬头
    private String invoiceNumber;           //车客：纳税人识别码
    private String invoiceAddress;          //车客：地址、电话
    private String invoiceBank;             //车客：开户行及账号
    private String mobile;                    //车客：运营负责人手机号
    private String connectorName;            //车客：运营负责人、个人类型存储真实姓名
    private String idNumber;                  //车客：运营负责人身份证号 推客个人身份证号
    private Integer status;                 // 1正常 2新增审核 3修改审核 4删除
    private Integer partyType;              //车客、推客、平台 等
    private Integer partyClass;             //车客：主体类型 包括：企业单位、政府机构、展览媒介、个人
    private Integer companyType;             // 车客：公司类别
    private Integer mediaType;              //车客：展览媒介的媒体属性 个人的职位类别
    private Date updateTime;                //更新时间
    private Long createPartyId;             //创建主体ID
    private Long createUserId;              //创建人ID
    private Date createTime;                //创建时间
    private String loginName;                //用户名
    private Long userId;
    private String validateCode;//手机验证码
    private String certificateImg;//资质证明
    private String certificateOther;//资质证明其它
    private String authorImg;//授权书
    private String identifyImgA;//身份证正面
    private String identifyImgB;//身份证反面
    private String imageUrl;//车客LOGO

    //非party字段
    private String letterCount;
    private String refuseReason;            //拒绝原因
    private Long partyId;
    private Integer userStatus;                 // 1正常 2锁定

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameFirstLetter() {
        return nameFirstLetter;
    }

    public void setNameFirstLetter(String nameFirstLetter) {
        this.nameFirstLetter = nameFirstLetter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public String getInvoiceBank() {
        return invoiceBank;
    }

    public void setInvoiceBank(String invoiceBank) {
        this.invoiceBank = invoiceBank;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPartyType() {
        return partyType;
    }

    public void setPartyType(Integer partyType) {
        this.partyType = partyType;
    }

    public Integer getPartyClass() {
        return partyClass;
    }

    public void setPartyClass(Integer partyClass) {
        this.partyClass = partyClass;
    }

    public Integer getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
    }

    public Integer getMediaType() {
        return mediaType;
    }

    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreatePartyId() {
        return createPartyId;
    }

    public void setCreatePartyId(Long createPartyId) {
        this.createPartyId = createPartyId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getCertificateImg() {
        return certificateImg;
    }

    public void setCertificateImg(String certificateImg) {
        this.certificateImg = certificateImg;
    }

    public String getCertificateOther() {
        return certificateOther;
    }

    public void setCertificateOther(String certificateOther) {
        this.certificateOther = certificateOther;
    }

    public String getAuthorImg() {
        return authorImg;
    }

    public void setAuthorImg(String authorImg) {
        this.authorImg = authorImg;
    }

    public String getIdentifyImgA() {
        return identifyImgA;
    }

    public void setIdentifyImgA(String identifyImgA) {
        this.identifyImgA = identifyImgA;
    }

    public String getIdentifyImgB() {
        return identifyImgB;
    }

    public void setIdentifyImgB(String identifyImgB) {
        this.identifyImgB = identifyImgB;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getLetterCount() {
        return letterCount;
    }

    public void setLetterCount(String letterCount) {
        this.letterCount = letterCount;
    }

    public Long getPartyId() {
        return partyId;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
}
