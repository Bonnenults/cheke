package com.autozi.cheke.service.mobile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.autozi.common.utils.util3.SMSUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.autozi.cheke.mobile.dao.MobilePhoneCodeDao;
import com.autozi.cheke.mobile.dao.MobilePhoneMessageEntryDao;
import com.autozi.cheke.mobile.entity.MobilePhoneCode;
import com.autozi.cheke.mobile.entity.MobilePhoneMessageEntry;
import com.autozi.common.utils.util2.DateTools;
import com.autozi.common.utils.util2.MobileConfigUtils;
import com.autozi.common.utils.web.SendMessageToMobileUtils;
import org.springframework.stereotype.Service;

@Service
public class MobilePhoneCodeServiceImpl implements IMobilePhoneCodeService {

	@Autowired
	private MobilePhoneCodeDao mobilePhoneCodeDao;
	@Autowired
	private MobilePhoneMessageEntryDao mobilePhoneMessageEntryDao;
	
	@Override
	public MobilePhoneCode getByMobilePhone(String phone) {
		return mobilePhoneCodeDao.getByMobilePhone(phone);
	}

	public void checkCodeSuccess(String phone) {
		MobilePhoneCode mobilePhoneCode = mobilePhoneCodeDao.getByMobilePhone(phone);
		mobilePhoneCode.setStatus(MobilePhoneCode.STATUS_SUCCESS);
		mobilePhoneCode.setUpdateTime(new Date());
		mobilePhoneCodeDao.update(mobilePhoneCode);
		//取最新验证码，更新状态为成功
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("mobilePhone", phone);
		filter.put("code", mobilePhoneCode.getCode());
		filter.put("orderBy", " MMPM.create_time desc ");
		List<MobilePhoneMessageEntry> mobilePhoneMessageEntryList = mobilePhoneMessageEntryDao.findListForMap(filter);
		if (mobilePhoneMessageEntryList != null && mobilePhoneMessageEntryList.size() > 0) {
			MobilePhoneMessageEntry mobilePhoneMessageEntry = mobilePhoneMessageEntryList.get(0);
			mobilePhoneMessageEntry.setCheckResult(MobilePhoneMessageEntry.CHECK_RESULT_SUCCESS);
			mobilePhoneMessageEntryDao.update(mobilePhoneMessageEntry);
		}
	}

	@Override
	public boolean checkMaxSendNumber(String mobilePhone) {
		MobilePhoneCode mobilePhoneCode = mobilePhoneCodeDao.getByMobilePhone(mobilePhone);
		//判断一天内是否发送超过了N次
		if (mobilePhoneCode != null) {
			//当前发送日期
			String cur_yyyymmdd = DateTools.formatDateForMore(DateTools.getCurrentDate(), "yyyyMMdd");
			//最后一次发送日期
			String last_yyyymmdd = DateTools.formatDateForMore(mobilePhoneCode.getLastSendTime(), "yyyyMMdd");
			//同一天内，且发送次数大于等于最大次数
			if(last_yyyymmdd.equals(cur_yyyymmdd) && mobilePhoneCode.getSendNumber() >= MobileConfigUtils._mobilePhoneCode_send_number){
				return false;
			}
		}
		return true;
	}

	/**
	  * @Description: 生成手机验证码，发送至手机，并保存至数据库
	  * @user zhiyun.chen
	  * @dateTime 2014-3-20下午04:21:47
	 */
	@Async
	public String createMobilePhoneCode(String mobilePhone, String code, Integer type){
		SMSUtils.sendMessage(mobilePhone, new String[]{code},SMSUtils.MODEL_ID_CK_ZC);
		//3、保存至数据库
		MobilePhoneCode mobilePhoneCode = mobilePhoneCodeDao.getByMobilePhone(mobilePhone);
		Date nowDate = new Date();
		if (mobilePhoneCode == null) {//第一次获取验证码
			mobilePhoneCode = new MobilePhoneCode();
			mobilePhoneCode.setMobilePhone(mobilePhone);
			mobilePhoneCode.setCode(code);
			mobilePhoneCode.setCreateTime(nowDate);
			mobilePhoneCode.setLastSendTime(nowDate);
			mobilePhoneCode.setStatus(MobilePhoneCode.STATUS_NOT);
			mobilePhoneCode.setTotalSendNumber(1);
			mobilePhoneCode.setSendNumber(1);
			mobilePhoneCodeDao.insert(mobilePhoneCode);
		} else {
			mobilePhoneCode.setCode(code);
			mobilePhoneCode.setStatus(MobilePhoneCode.STATUS_NOT);
			mobilePhoneCode.setTotalSendNumber(mobilePhoneCode.getTotalSendNumber() + 1);
			
			String cur_yyyymmdd = DateTools.formatDateForMore(DateTools.getCurrentDate(), "yyyyMMdd");
			String last_yyyymmdd = DateTools.formatDateForMore(mobilePhoneCode.getLastSendTime(), "yyyyMMdd");
			//如果是同一天，当天发送次数加1
			if(last_yyyymmdd.equals(cur_yyyymmdd)){
				mobilePhoneCode.setSendNumber(mobilePhoneCode.getSendNumber() + 1);
			} else {//当天发送次数为1
				mobilePhoneCode.setSendNumber(1);
			}
			mobilePhoneCode.setLastSendTime(nowDate);
			mobilePhoneCodeDao.update(mobilePhoneCode);
		}
		return code;
	}

	@Async
	public void directSendMobilePhoneCode(String mobilePhone, String content, Integer type, String code){
		//boolean sendResult = SendMessageToMobileUtils.directSend(mobilePhone,content,"");
		boolean sendResult = SMSUtils.sendMessage(mobilePhone, new String[]{content},SMSUtils.MODEL_ID_CK_ZC);
		MobilePhoneMessageEntry mobilePhoneMessageEntry = new MobilePhoneMessageEntry();
		mobilePhoneMessageEntry.setMobilePhone(mobilePhone);
		mobilePhoneMessageEntry.setContent(content);
		if(sendResult){
			mobilePhoneMessageEntry.setSendResult(MobilePhoneMessageEntry.SEND_RESULT_SUCCESS);
		}else{
			mobilePhoneMessageEntry.setSendResult(MobilePhoneMessageEntry.SEND_RESULT_NOT);
		}
		mobilePhoneMessageEntry.setType(type);
		mobilePhoneMessageEntry.setCreateTime(new Date());
		if(StringUtils.isBlank(code)){
			mobilePhoneMessageEntry.setCode("000000");
		}else{
			mobilePhoneMessageEntry.setCode(code);
		}
		mobilePhoneMessageEntry.setCheckResult(MobilePhoneMessageEntry.CHECK_RESULT_NOT);
		mobilePhoneMessageEntryDao.insert(mobilePhoneMessageEntry);
	}

}
