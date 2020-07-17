package com.autozi.cheke.party.entity;

import com.autozi.common.dal.entity.IdEntity;
import com.autozi.common.dal.format.Column;
import com.autozi.common.dal.format.persistence;

import java.util.Date;

/**
 * 融合到企业Party中
 * User: 刘宇
 * Date: 12-4-10
 * Time: 上午10:29
 */
public class CompanyParty extends IdEntity {
	@Column(ColumnType=persistence.TEMPORARY)
	private static final long serialVersionUID = -1846601617551098587L;
	
	private String description;            //介绍

	
    private String businessLicense;   //营业执照注册号
    private String organizationNumber;//组织机构代码
    
    private String creditCardNumber;	//贷款卡号
    
    private Double registerCapital;   //注册资本
    private Date registerDate;        //注册时间
    private String registerPerson;    //法人代表
    private String legalPersonIdNo;	//法人身份证号
    private String registerAreaId;	//注册区域ID
    private String registerAddress;   //注册地址
    
    private String operationAreaId;	//经营区域ID
    private String operationAddress;//实际经营地址
    
    private String officeAreaId;		//办公地址ID
    
    private Integer employeeNum;	//员工数量
    private Double factoryArea;		//厂房面积
    private Double purchasePreYear;	//年采购额
    private Double turnoverPreYear;	//年营业额


    private String taxIdentificationNumber;    //纳税人识别号
    private String taxNumber;         //税务登记号
    private String  invoiceTitle; //发票抬头
    private String taxAddress;	//发票地址
    private String taxPhone;	//发票电话
    
    private String bankName; //开户行名称
    private String bankAccount; //银行账号
    private String bankAccountPermits;//银行开户许可证
    private String socialCreditCode;   //统一社会信用代码

    private Double evaluationScores; 	//评价分数
    private Integer evaluationCount;   //评价总次数
    private Long allianceId; //o2o汽修厂品牌id（联盟ID）
    private Integer provideService; //是否提供服务（1：是，2：否）
    
    private Double markMap_x; //地图标注X轴
    private Double markMap_y; //地图标注y轴
    private Double trafficScores; //交通评分
    private Double serveScores; //服务评分
    private Double efficiencyScores; //效率评分
    private Integer autoRepairFactoryGrade; //汽修厂等级
    private String autoRepairFactoryLogo; //汽修厂logo
    
    private String servicePhone;            //客服电话
    private String markMap;                 //地图标注
    private String businessHours ;          //营业时间
    
    private String businessLicenseImagePath;	//营业执照附件路径
    private String taxCertificateImagePath;		//税务登记证附件路径
    private String lgCertFrontImagePath;		//法人身份证正面
    private String lgCertBackImagePath;		//法人身份证反面
    private String insCodeImagePath;		//组织机构代码
    private String bankAccPrmImagePath;		//银行开户许可证
    private String socialCreditImagePath;       //统一社会信用代码证
    private String otherImagePath1;    //其它附件路径1
    private String otherImagePath2;    //其它附件路径2
    private String otherImagePath3;    //其它附件路径3
    
    
    public String getLgCertFrontImagePath() {
		return lgCertFrontImagePath;
	}

	public void setLgCertFrontImagePath(String lgCertFrontImagePath) {
		this.lgCertFrontImagePath = lgCertFrontImagePath;
	}

	public String getLgCertBackImagePath() {
		return lgCertBackImagePath;
	}

	public void setLgCertBackImagePath(String lgCertBackImagePath) {
		this.lgCertBackImagePath = lgCertBackImagePath;
	}

	public String getInsCodeImagePath() {
		return insCodeImagePath;
	}

	public void setInsCodeImagePath(String insCodeImagePath) {
		this.insCodeImagePath = insCodeImagePath;
	}

	public String getBankAccPrmImagePath() {
		return bankAccPrmImagePath;
	}

	public void setBankAccPrmImagePath(String bankAccPrmImagePath) {
		this.bankAccPrmImagePath = bankAccPrmImagePath;
	}

	public String getSocialCreditImagePath() {
        return socialCreditImagePath;
    }

    public void setSocialCreditImagePath(String socialCreditImagePath) {
        this.socialCreditImagePath = socialCreditImagePath;
    }

    public String getTaxIdentificationNumber() {
		return taxIdentificationNumber;
	}

	public void setTaxIdentificationNumber(String taxIdentificationNumber) {
		this.taxIdentificationNumber = taxIdentificationNumber;
	}

	public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }


    public String getOrganizationNumber() {
        return organizationNumber;
    }

    public void setOrganizationNumber(String organizationNumber) {
        this.organizationNumber = organizationNumber;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public Double getRegisterCapital() {
        return registerCapital;
    }

    public void setRegisterCapital(Double registerCapital) {
        this.registerCapital = registerCapital;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getRegisterPerson() {
        return registerPerson;
    }

    public void setRegisterPerson(String registerPerson) {
        this.registerPerson = registerPerson;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

	public Double getEvaluationScores() {
		return evaluationScores;
	}

	public void setEvaluationScores(Double evaluationScores) {
		this.evaluationScores = evaluationScores;
	}

	public Integer getEvaluationCount() {
		return evaluationCount;
	}

	public void setEvaluationCount(Integer evaluationCount) {
		this.evaluationCount = evaluationCount;
	}


	public Integer getProvideService() {
		return provideService;
	}

	public void setProvideService(Integer provideService) {
		this.provideService = provideService;
	}

	public Long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(Long allianceId) {
		this.allianceId = allianceId;
	}

	public Double getMarkMap_x() {
		return markMap_x;
	}

	public void setMarkMap_x(Double markMap_x) {
		this.markMap_x = markMap_x;
	}

	public Double getMarkMap_y() {
		return markMap_y;
	}

	public void setMarkMap_y(Double markMap_y) {
		this.markMap_y = markMap_y;
	}

	public Double getTrafficScores() {
		return trafficScores;
	}

	public void setTrafficScores(Double trafficScores) {
		this.trafficScores = trafficScores;
	}

	public Double getServeScores() {
		return serveScores;
	}

	public void setServeScores(Double serveScores) {
		this.serveScores = serveScores;
	}

	public Double getEfficiencyScores() {
		return efficiencyScores;
	}

	public void setEfficiencyScores(Double efficiencyScores) {
		this.efficiencyScores = efficiencyScores;
	}

	public Integer getAutoRepairFactoryGrade() {
		return autoRepairFactoryGrade;
	}

	public void setAutoRepairFactoryGrade(Integer autoRepairFactoryGrade) {
		this.autoRepairFactoryGrade = autoRepairFactoryGrade;
	}

	public String getAutoRepairFactoryLogo() {
		return autoRepairFactoryLogo;
	}

	public void setAutoRepairFactoryLogo(String autoRepairFactoryLogo) {
		this.autoRepairFactoryLogo = autoRepairFactoryLogo;
	}

	public String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	public String getMarkMap() {
		return markMap;
	}

	public void setMarkMap(String markMap) {
		this.markMap = markMap;
	}

	public String getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	public String getBusinessLicenseImagePath() {
		return businessLicenseImagePath;
	}

	public void setBusinessLicenseImagePath(String businessLicenseImagePath) {
		this.businessLicenseImagePath = businessLicenseImagePath;
	}

	public String getTaxCertificateImagePath() {
		return taxCertificateImagePath;
	}

	public void setTaxCertificateImagePath(String taxCertificateImagePath) {
		this.taxCertificateImagePath = taxCertificateImagePath;
	}

	public String getOtherImagePath1() {
		return otherImagePath1;
	}

	public void setOtherImagePath1(String otherImagePath1) {
		this.otherImagePath1 = otherImagePath1;
	}

	public String getOtherImagePath2() {
		return otherImagePath2;
	}

	public void setOtherImagePath2(String otherImagePath2) {
		this.otherImagePath2 = otherImagePath2;
	}

	public String getOtherImagePath3() {
		return otherImagePath3;
	}

	public void setOtherImagePath3(String otherImagePath3) {
		this.otherImagePath3 = otherImagePath3;
	}

	public Integer getEmployeeNum() {
		return employeeNum;
	}

	public void setEmployeeNum(Integer employeeNum) {
		this.employeeNum = employeeNum;
	}

	public Double getFactoryArea() {
		return factoryArea;
	}

	public void setFactoryArea(Double factoryArea) {
		this.factoryArea = factoryArea;
	}

	public Double getPurchasePreYear() {
		return purchasePreYear;
	}

	public void setPurchasePreYear(Double purchasePreYear) {
		this.purchasePreYear = purchasePreYear;
	}

	public Double getTurnoverPreYear() {
		return turnoverPreYear;
	}

	public void setTurnoverPreYear(Double turnoverPreYear) {
		this.turnoverPreYear = turnoverPreYear;
	}

	public String getTaxAddress() {
		return taxAddress;
	}

	public void setTaxAddress(String taxAddress) {
		this.taxAddress = taxAddress;
	}

	public String getTaxPhone() {
		return taxPhone;
	}

	public void setTaxPhone(String taxPhone) {
		this.taxPhone = taxPhone;
	}

	public String getLegalPersonIdNo() {
		return legalPersonIdNo;
	}

	public void setLegalPersonIdNo(String legalPersonIdNo) {
		this.legalPersonIdNo = legalPersonIdNo;
	}

	public String getRegisterAreaId() {
		return registerAreaId;
	}

	public void setRegisterAreaId(String registerAreaId) {
		this.registerAreaId = registerAreaId;
	}

	public String getOperationAreaId() {
		return operationAreaId;
	}

	public void setOperationAreaId(String operationAreaId) {
		this.operationAreaId = operationAreaId;
	}

	public String getOperationAddress() {
		return operationAddress;
	}

	public void setOperationAddress(String operationAddress) {
		this.operationAddress = operationAddress;
	}

	public String getOfficeAreaId() {
		return officeAreaId;
	}

	public void setOfficeAreaId(String officeAreaId) {
		this.officeAreaId = officeAreaId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getBankAccountPermits() {
		return bankAccountPermits;
	}

	public void setBankAccountPermits(String bankAccountPermits) {
		this.bankAccountPermits = bankAccountPermits;
	}

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

}
