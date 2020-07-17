package com.autozi.cheke.mobile.constant;

import com.autozi.cheke.mobile.util.MobileConfigUtils;



public interface MobileConstant {
	public interface PageSize{
		int _page_size = 10;
		int _page_no = 1;
	}
	
	/**
	 * 错误信息
	 * 
	 * @author Administrator
	 *
	 */
	public interface Error{
		String _ok = "0000";
		String _ok_msg = "OK";
		
		String _error = "1000";
		String _error_msg = "数据加载失败，请稍后重试！";
		
		String _error_access = "0001";
		String _error_access_msg = "请求数据失败，请稍后重试！";
		
		String _mobilePhoneIsEmpty = "0002";
		String _mobilePhoneIsEmpty_msg = "手机号码必须输入";
		
		String _passwdIsEnpty = "0003";
		String _passwdIsEnpty_msg = "请输入密码";
		
		String _passwordError = "0004";
		String _passwordError_msg = "密码错误";
		
		String _mobilePhoneCodeIsEmpty = "0005";
		String _mobilePhoneCodeIsEmpty_msg = "手机验证码不能为空";
		
		String _mobilePhoneCodeIsError = "0006";
		String _mobilePhoneCodeIsError_msg = "手机验证码错误或已失效";
		
		String _userPasswdIsError = "0007";
		String _userPasswdIsError_msg = "用户名或密码错误";
		
		String _tokenIsExpire = "0008";
		String _tokenIsExpire_msg = "登录过期，请重新登录";
		
		String _errorToken = "0009";
		String _errorToken_msg = "令牌无效，请重新登录";
		
		String _oldPdMust = "0010";
		String _oldPdMust_msg = "旧密码必须输入";
		
		String _newPdMust = "0011";
		String _newPdMust_msg = "新密码必须输入";
		
		String _dupNewIsDiff = "0012";
		String _dupNewIsDiff_msg = "两次密码输入不一致";
		
		String _oldPdError = "0013";
		String _oldPdError_msg = "旧密码错误";
		
		String _nameMust = "0014";
		String _nameMust_msg = "名称必须输入";
		
		String _mobilePhoneIsError = "0015";
		String _mobilePhoneIsError_msg = "手机号码格式不正确";
		
		String _mobilePhoneExist = "0016";
		String _mobilePhoneExist_msg = "手机号码已经存在";
		
		String _mobilePhoneCodeMaxSend = "0017";
		String _mobilePhoneCodeMaxSend_msg = "一天内最多获取"+ MobileConfigUtils._mobilePhoneCode_send_number + "次验证码";
		
		String _txImgMust = "0018";
		String _txImgMust_msg = "头像必须是图片";
		
		String _txImgIsToBig = "0019";
		String _txImgIsToBig_msg = "头像大小不能超过1M";
		
		String _userIsNotExist = "0020";
		String _userIsNotExist_msg = "手机号码不存在";
		
		String _connectorNameIsEmpty = "0021";
		String _connectorNameIsEmpty_msg = "请输入联系人";
		
		String _connectorPhoneIsEmpty = "0022";
		String _connectorPhoneIsEmpty_msg = "请输入手机号码";
		
		String _connectorIsNull = "0023";
		String _connectorIsNull_msg = "联系人不存在";
		
		String _connectorExist = "0024";
		String _connectorExist_msg = "联系人已存在";
		
		String _connectorIdIsNotNull = "0025";
		String _connectorIdIsNotNull_msg = "联系人主键不能为空";
		
		String _userNuEnabled = "0026";
		String _userNuEnabled_msg = "该用户已被锁定或删除！请联系管理员";
		
		String _licenseIsMust = "0027";
		String _licenseIsMust_msg = "请输入车牌号";
		
		String _carIdIsMust = "0028";
		String _carIdIsMust_msg = "请选择您的爱车";
		
		String _userCarRelationIdIsMust = "0029";
		String _userCarRelationIdIsMust_msg = "车主与车关联主键不能为空";
		
		String _carBrandIdIsMust = "0030";
		String _carBrandIdIsMust_msg = "请选择汽车品牌";
		
		String _carSeriesIdIsMust = "0031";
		String _carSeriesIdIsMust_msg = "请选择汽车车系";
		
		String _packageIdIsMust = "0032";
		String _packageIdIsMust_msg = "请选择服务套餐";
		
		String _markMapIsMust = "0033";
		String _markMapIsMust_msg = "地图坐标不能为空";
		
		String _currentMileageIsMust = "0034";
		String _currentMileageIsMust_msg = "请输入行驶里程";
		
		String _carOnRoadTimeIsMust = "0035";
		String _carOnRoadTimeIsMust_msg = "请输入购车日期";
		
		String _partyIdIsMust = "0036";
		String _partyIdIsMust_msg = "请选择汽修厂";
		
		String _connectorIdIsMust = "0037";
		String _connectorIdIsMust_msg = "请选择联系人";
		
		String _carModelIdIsMust = "0038";
		String _carModelIdIsMust_msg = "请选择车型";
		
		String _reserveTimeIsMust = "0039";
		String _reserveTimeIsMust_msg = "请填写预约时间";
		
		String _packageIdOrCategoryIdIsMust = "0039";
		String _packageIdOrCategoryIdIsMust_msg = "请选择服务分类或者服务套餐";
		
		String _reserveOrderTypeIsMust = "0040";
		String _reserveOrderTypeIsMust_msg = "预约单类型必传";
		
		String _reserveOrderIdIsMust = "0041";
		String _reserveOrderIdIsMust_msg = "请选择预约单";
		
		String _evaluationScoreIsMust = "0042";
		String _evaluationScoreIsMust_msg = "请填写评价分数";
		
		String _evaluationContentIsMust = "0043";
		String _evaluationContentIsMust_msg = "请填写评价内容";
		
		String _messageIdIsMust = "0044";
		String _messageIdIsMust_msg = "请选择消息";
		
		String _carLicenseLengthIs7= "0045";
		String _carLicenseLengthIs7_msg = "车牌号长度为7位";
		
		String _carVinLengthIs17 = "0046";
		String _carVinLengthIs17_msg = "车架号长度为17位";
		
		String _areaIdIsMust = "0047";
		String _areaIdIsMust_msg = "请选择区域";
		
		String _loginNameIsEmpty = "0048";
		String _loginNameIsEmpty_msg = "请输入用户名";
		
		String _nameIsMust = "0049";
		String _nameIsMust_msg = "请输入姓名";
		
		String _emailIsMust = "0050";
		String _emailIsMust_msg = "请输入电子邮件";
		
		String _categoryIdIsMust = "0051";
		String _categoryIdIsMust_msg = "请选择服务分类";
		
		String _projectIdsIsMust = "0052";
		String _projectIdsIsMust_msg = "请选择保养项目";
		
		String _loginNameNotExist = "0053";
		String _loginNameNotExist_msg = "用户名不存在";
		
		String _connectorNameIsMust = "0054";
		String _connectorNameIsMust_msg = "请输入联系人";
		
		String _connectorPhoneIsMust = "0055";
		String _connectorPhoneIsMust_msg = "请输入联系电话";
		
		String _sosOrderIdIsMust = "0056";
		String _sosOrderIdIsMust_msg = "请选择救援预约单";
		
		String _carLicenseExist= "0057";
		String _carLicenseExist_msg = "您已添加过此爱车！";
		
		String _addressIsMust = "0058";
		String _addressIsMust_msg = "请输入救援地址";
		
		String _rescueUserIdIsMust = "0059";
		String _rescueUserIdIsMust_msg = "请选择救援人员";
		
		String _sos_projectIdsOrCategoryIdIsMust = "0060";
		String _sos_projectIdsOrCategoryIdIsMust_msg = "请选择项目或者分类";
		
		String _smbyDeleteOrderGoods = "0061";
		
		String _smbyFinishOrder = "0062";
		
		String _userNotLogin = "0063";
		String _userNotLogin_msg = "用户权限不够";
		
		String _confirmPwdIsEnpty = "0064";
		String _confirmPwdIsEnpty_msg = "确认密码不能为空";
		
		String _invitationCodeIsError = "0065";
		String _invitationCodeIsError_msg = "邀请码错误";

		String _partyIsNull = "0066";
		String _partyIsNull_msg = "车客信息不存在";

		String _isNotImg = "0067";
		String _isNotImg_msg = "您上传的不是图片";

		String _imgIsBig = "0068";
		String _imgIsBig_msg = "上传的图片过大，不能超过3M";

		String _tkOnlyOne = "0069";
		String _tkOnlyOne_msg = "推客号仅可以修改一次";

		String _idNotUpload = "0070";
		String _idNotUpload_msg = "未上传身份证，请先完善信息";

		String _idNotVerify = "0071";
		String _idNotVerify_msg = "身份证正在审核中，暂时不能提现";

		String _isNot6Date = "0072";
		String _isNot6Date_msg = "每个月只能6号后才能提现";

		String _bankCardRepeat = "0073";
		String _bankCardRepeat_msg = "银行卡号重复";

		String _moneyPoor = "0074";
		String _moneyPoor_msg = "余额不足";

		String _moneyNull = "0075";
		String _moneyNull_msg = "请填写提现金额";

		String _money10 = "0076";
		String _money10_msg = "提现的金额必须是10的整数倍";

		String _letterIs100 = "0077";
		String _letterIs100_msg = "私信内容不能大于100字";

		String _loginFailedCount = "0078";
		String _loginFailedCount_msg = "今天连续输入密码错误超过5次，请明天再试";

		String _loginNameOnly = "0079";
		String _loginNameOnly_msg = "已存在相同用户名";

		String _nameLength = "0080";
		String _nameLength_msg = "昵称应为2-15个字";

		String _realNameLength = "0081";
		String _realNameLength_msg = "真实姓名应为2-30个字";

		String _loginNameLength = "0082";
		String _loginNameLength_msg = "推客号应为2-30个字";

		String _descriptionLength = "0083";
		String _descriptionLength_msg = "个人简介应小于30个字";

		String _addressLength = "0084";
		String _addressLength_msg = "地址字数要小于50";

		String _companyNameLength = "0085";
		String _companyNameLength_msg = "公司名称不能超过50个字";

		String _jobNameLength = "0086";
		String _jobNameLength_msg = "职位名称字不能超过50个字";

		String _IDCardNumberLength = "0087";
		String _IDCardNumberLength_msg = "身份证号位数不正确";

		String _IDCardValidate = "0088";
		String _IDCardValidate_msg = "身份证号格式不正确";

		String _bankNumberNull = "0089";
		String _bankNumberNull_msg = "请输入银行卡号";

		String _bankNumberLength = "0090";
		String _bankNumberLength_msg = "银行卡号位数不正确";

		String _bankNumber = "0091";
		String _bankNumber_msg = "银行卡号格式不正确，只能是数字";

		String _nullInput = "0092";
		String _nullInput_msg = "输入信息为空";

		String _feedbackLength = "0093";
		String _feedbackLength_msg = "意见反馈应小于200个字";

        String _titleLength = "0093";
        String _titleLength_msg = "意见反馈应小于200个字";

	}

}
