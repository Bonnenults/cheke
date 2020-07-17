package com.autozi.cheke.mobile.facade;

import com.autozi.cheke.basic.entity.Area;
import com.autozi.cheke.basic.entity.Notice;
import com.autozi.cheke.mobile.entity.MobilePhoneCode;
import com.autozi.cheke.mobile.util.ImageUtils;
import com.autozi.cheke.mobile.util.MobileConfigUtils;
import com.autozi.cheke.service.basic.IAreaService;
import com.autozi.cheke.service.basic.INoticeService;
import com.autozi.cheke.service.mobile.IMobilePhoneCodeService;
import com.autozi.cheke.service.user.*;
import com.autozi.cheke.user.entity.*;
import com.autozi.cheke.user.type.IUserType;
import com.autozi.cheke.user.type.MedalRuleStatus;
import com.autozi.cheke.user.type.MedalStatus;
import com.autozi.common.utils.util1.MobilePhoneUtils;
import com.autozi.common.utils.util2.DateTools;
import com.autozi.common.utils.util2.DateUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserApiFacade {

	@Autowired
	private IUserService userService;
	@Autowired
	private IMobilePhoneCodeService mobilePhoneCodeService;
	@Autowired
	private IUserLoginStatusService userLoginStatusService;
	@Autowired
	private IAreaService areaService;
	@Autowired
	private INoticeService noticeService;
    @Autowired
    private IFeedBackService feedBackService;
    @Autowired
    private IUserCountInfoService userCountInfoService;
    @Autowired
    private MedalApiFacade medalApiFacade;
    @Autowired
    private IMedalRuleService medalRuleService;
    @Autowired
    private IPersonalMedalService personalMedalService;



    public boolean checkMobileExists(String phone) {
		User user = new User();
		user.setPhone(phone);
		return userService.checkMobileExists(user);
	}

	public boolean checkMobilePhoneCode(String phone, String verCode) {
		MobilePhoneCode mobilePhoneCode = mobilePhoneCodeService.getByMobilePhone(phone);
		if (mobilePhoneCode != null && mobilePhoneCode.getStatus() != MobilePhoneCode.STATUS_SUCCESS) {
			if (!mobilePhoneCode.getCode().equals(verCode)) {
				return false;
			}
			long lastTime = mobilePhoneCode.getLastSendTime().getTime();
			long curTime = DateTools.getCurrentDate().getTime();
			int expireTime = MobileConfigUtils._mobilePhoneCode_expire_time;
			long codeExpireTime = expireTime*60*1000L; //分钟为单位
			if((lastTime + codeExpireTime)<curTime){// 过期
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	public Long registerUser(String phone, String phonePwd, String invitationCode) {
		return userService.registerUser(phone, phonePwd, invitationCode);
	}

	public void createUserLoginStatus(UserLoginStatus userLoginStatus) {
		userLoginStatusService.createUserLoginStatus(userLoginStatus);
	}

	public User findUserByLoginName(String phone) {
		return userService.findUserByLoginName(phone);
	}

	public User getUserByLoginName(String phone) {
		return userService.getUserByLoginName(phone);
	}

	public UserLoginStatus getUserLoginStatus(Long id) {
		return userLoginStatusService.getUserLoginStatus(id);
	}

	public void updateUserLoginStatus(UserLoginStatus userLoginStatus) {
		userLoginStatusService.updateUserLoginStatus(userLoginStatus);
	}

	public void updateMd5Password(Long id, String new_passwd) {
		userService.updateMd5Password(id, new_passwd);
	}

	public void updateUser(User user) {
		userService.updateUser(user);
	}

	public void modifyPhone(User user,UserLoginStatus userLoginStatus) {
		userService.modifyPhone(user,userLoginStatus);
	}

	public boolean checkMaxSendNumber(String mobilePhone) {
		return mobilePhoneCodeService.checkMaxSendNumber(mobilePhone);
	}

	public String getMobilePhoneCode(String mobilePhone, Integer type) {
		//1、随机生成6位纯数字验证码
		String code = MobilePhoneUtils.createRandom(MobilePhoneUtils.NUMBER_FLAG_TRUE, 6);
		//异步发送验证码及保存验证码
		mobilePhoneCodeService.createMobilePhoneCode(mobilePhone, code, type);
		return code;
	}

	public JSONObject getMyInfo(User user) {
		JSONObject data = new JSONObject();
		PersonalParty personalParty = userService.getPersonalPartyById(user.getPartyId());
		//头像
		data.put("imageUrl", personalParty.getImageUrl()==null?"":personalParty.getImageUrl());
		//勋章
        Map<String,Object> filter = new HashMap<>();
        filter.put("userId",user.getId());
        filter.put("status", MedalStatus.ADORN.getType());
        PersonalMedal personalMedal = medalApiFacade.getPersonalMedalByFilter(filter);
        if(personalMedal != null){
            Medal medal = medalApiFacade.getMedalById(personalMedal.getMedalId());
            if(medal != null){
                data.put("medalUrl", medal.getImageActive()==null?"":medal.getImageActive());
            }
        }else{
            data.put("medalUrl", "");
        }
		//推客号
		data.put("loginName", user.getLoginName()==null?"":user.getLoginName());
		//昵称
		data.put("name", user.getName()==null?"":user.getName());
		//个人简介
		data.put("description", personalParty.getDescription()==null?"":personalParty.getDescription());
		//性别
		data.put("gender", user.getGender()==null?"":user.getGender());
		data.put("IDCardNumber", personalParty.getIdCardNumber()==null?"":personalParty.getIdCardNumber());
		//地址
		try{
			String sAreaId = personalParty.getAddress();
			Long areaId = null;
			if(sAreaId!=null && !sAreaId.equals("")){
				areaId = Long.parseLong(personalParty.getAddress());
			}
			if(areaId!=null){
				data.put("areaId", areaId);
				Area area = areaService.getById(areaId);
				String areaName = area.getName();
				Long areaIdTwo = area.getParentId();
				Area areaTwo = areaService.getById(areaIdTwo);
				String areaNameTwo = areaTwo.getName();
				Long areaIdOne = areaTwo.getParentId();
				Area areaOne = areaService.getById(areaIdOne);
				String areaNameOne = areaOne.getName();
				data.put("address", areaNameOne+" "+areaNameTwo+" "+areaName);
			}else{
				data.put("areaId", "");
				data.put("address", "");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		//身份证信息
		data.put("IDCardStatus", personalParty.getIdCardStatus());
		//公司信息
		data.put("companyName", personalParty.getCompanyName()==null?"":personalParty.getCompanyName());
		//职位信息
		data.put("jobName", personalParty.getJobName()==null?"":personalParty.getJobName());
		//用户状态信息
		data.put("userStatus", user.getUserStatus()==null?1:user.getUserStatus());
		//用户真实姓名
		data.put("realName", user.getRealName()==null?"":user.getRealName());
		return data;
	}

	public boolean isExistInviteCode(String inviteCode) {
        return userService.isExistInviteCodeMine(inviteCode);
    }

	public void saveMyInfo(User user, PersonalParty personalParty) {
		userService.updateUser(user);
		userService.updatePersonalParty(personalParty);
	}

	public String saveIDCard(User user, MultipartFile file, int identifyType) {
		String imgUrl = ImageUtils.upload(file);
		Long partyId = user.getPartyId();
		PersonalParty personalParty = new PersonalParty();
		personalParty.setId(partyId);
		if(identifyType==1){
			personalParty.setIdentifyImgA(imgUrl);
		}else{
			personalParty.setIdentifyImgB(imgUrl);
		}
		personalParty.setIdCardStatus(IUserType.TUIKE_STATUS_VERIFY);
		userService.updatePersonalParty(personalParty);
		return imgUrl;
	}

	public String uploadHeadImage(User user, MultipartFile file) {
		String imgUrl = ImageUtils.upload(file);
		Long partyId = user.getPartyId();
		PersonalParty personalParty = new PersonalParty();
		personalParty.setId(partyId);
		personalParty.setImageUrl(imgUrl);
		userService.updatePersonalParty(personalParty);
		return imgUrl;
	}


	public JSONObject showIDCard(User user) {
		JSONObject data = new JSONObject();
		PersonalParty personalParty = userService.getPersonalPartyById(user.getPartyId());
		String identifyImgA = personalParty.getIdentifyImgA();
		data.put("identifyImgA", identifyImgA);
		String identifyImgB = personalParty.getIdentifyImgB();
		data.put("identifyImgB", identifyImgB);
		Integer idCardStatus = personalParty.getIdCardStatus();
		String refuseReason = personalParty.getRefuseReason()==null?"":personalParty.getRefuseReason();
		data.put("refuseReason", refuseReason);
		data.put("IDCardStatus", idCardStatus);
		String statusMsg = "";
		if(idCardStatus==IUserType.TUIKE_STATUS_NORMAL){
			statusMsg = "已通过";
		}else if(idCardStatus==IUserType.TUIKE_STATUS_REFUSE){
			statusMsg = "身份证审核未通过";
		}else if(idCardStatus==IUserType.TUIKE_STATUS_REGISTER){
			statusMsg = "上传身份证照片";
		}else if(idCardStatus==IUserType.TUIKE_STATUS_VERIFY){
			statusMsg = "待审核";
		}
		data.put("statusMsg", statusMsg);
		return data;
	}

	public JSONObject setting(User user) {
		JSONObject data = new JSONObject();
		if(user==null){
			data.put("phone", "未登录");
			data.put("inviteCodeMine", "无");
		}else{
			data.put("phone", user.getPhone());
			PersonalParty personalParty = userService.getPersonalPartyById(user.getPartyId());
			data.put("inviteCodeMine", personalParty.getInviteCodeMine());
		}
		return data;
	}

	public JSONObject developTK(User user) {
		JSONObject data = new JSONObject();
		if(user!=null){
			PersonalParty personalParty = userService.getPersonalPartyById(user.getPartyId());
			data.put("inviteCodeMine", personalParty.getInviteCodeMine());
			String inviteCode = personalParty.getInviteCode();
			String parentTuikeName = "";
			if(inviteCode==null || inviteCode.equals("")){
				parentTuikeName = "无";
			}else{
				PersonalParty parentParty = userService.getPersonalPartyByInviteCode(inviteCode);
				try{
					User parentUser = userService.getTuikePartyAdminUser(parentParty.getId());
					parentTuikeName = parentUser.getName()==null?parentUser.getPhone():parentUser.getName();
				}catch (Exception e){
					e.printStackTrace();
				}
			}
			data.put("onTK", parentTuikeName);
			data.put("underTk", userService.getUnderTK(personalParty.getInviteCodeMine()));
		}
		return data;
	}

	public User getUserByPartyId(Long partyId){
		return userService.getPartyAdminUser(partyId);
	}

	public PersonalParty getPersonalParty(Long partyId){
		PersonalParty personalParty = userService.getPersonalPartyById(partyId);
		return personalParty;
	}

	public boolean checkAreaId(Long areaId){
		Area area = areaService.getById(areaId);
		if(area==null){
			return false;
		}
		return true;
	}

	public JSONObject getAreaByParentId(Long parentId){
		List<Area> list = areaService.findByParent(parentId);
		JSONObject data = new JSONObject();
		JSONArray arr = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			Area area = list.get(i);
			if(area==null){
				continue;
			}
			JSONObject obj = new JSONObject();
			obj.put("areaId",area.getId());
			obj.put("areaName",area.getName());
			arr.add(obj);
		}
		data.put("list",arr);
		return data;
	}

	public JSONObject getAllArea(){
		List<Area> list = areaService.listAll();
		JSONObject data = new JSONObject();
		data.put("list",new JSONArray());
		for (int i = 0; i < list.size(); i++) {
			Area area = list.get(i);
			if(area==null){
				continue;
			}
			//如果是顶级地区，直接放入第一级列表中
			if(area.getParentId()==0){
				JSONArray arr = data.getJSONArray("list");
				JSONObject obj = new JSONObject();
				obj.put("areaId",area.getId());
				obj.put("parentId",0);
				obj.put("name",area.getName());
				obj.put("list",new JSONArray());
				arr.add(obj);
				continue;
			}
			setData(data,area);
		}
		return data;
	}

	private void setData(JSONObject obj, Area area){
		try{
			Object object = obj.get("list");
			if(object==null){
				return;
			}
			JSONArray arr = (JSONArray)object;
			for (int i = 0; i < arr.size(); i++) {
				JSONObject temp = arr.getJSONObject(i);
				String tempId = temp.getString("areaId");
				String areaId = area.getParentId().toString();
				if(tempId.equals(areaId)){
					JSONArray tempList = temp.getJSONArray("list");
					JSONObject areaObj = new JSONObject();
					areaObj.put("areaId",area.getId());
					areaObj.put("areaName",area.getName());
					areaObj.put("parentId",area.getParentId());
					if(area.getAreaLevel()!=3){
						areaObj.put("list",new JSONArray());
					}
					tempList.add(areaObj);
					break;
				}else{
					setData(temp,area);
				}
			}
		}catch (Exception e){
			System.out.println(area.getId()+" "+area.getName());
			e.printStackTrace();
		}
	}

	public void createNotice(Long userId) {
		Notice notice = new Notice();
		notice.setUserId(userId);
		notice.setTitle("系统通知");
		notice.setContent("恭喜您注册成功车客，完善信息-领取任务，即可获取佣金。");
		notice.setStatus(0);
		noticeService.insert(notice);
	}

	public void addFeedBack(FeedBack feedBack) {
        feedBackService.addFeedBack(feedBack);
	}

    public void addSearchFeedBack(SearchFeedBack searchfeedBack) {
        feedBackService.addSearchFeedBack(searchfeedBack);
    }


	public void loginCount(User user){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = format.format(new Date());
        String yesterday = format.format(DateUtils.getYesterday());

		UserCountInfo userCountInfo = getUserCountInfo(user.getId());
		if(userCountInfo == null) {
			userCountInfo = new UserCountInfo();
			userCountInfo.setId(user.getId());
			userCountInfo.setLoginCount(1);//总登录天数
			userCountInfo.setContinuousLoginCount(1);//连续登录天数
            userCountInfo.setLastLoginTime(new Date());
			userCountInfo.setStudyDaysCount(0);//学习天数
			userCountInfo.setStudyChapterCount(0);//学习章节数
			userCountInfo.setCreateTime(new Date());

            createUserCountInfo(userCountInfo);
		} else {

            String lastLoginDate = userCountInfo.getLastLoginTime()==null?"1900-01-01" :format.format(userCountInfo.getLastLoginTime());

            if( ! lastLoginDate.equals(todayDate) ){
                Integer loginCount = userCountInfo.getLoginCount() + 1;
                Integer continuousLoginCount = userCountInfo.getContinuousLoginCount();
                if(! lastLoginDate.equals(yesterday) ){//不是连续登录，设置连续登录天数为1
                    userCountInfo.setLoginCount(loginCount);//总登录天数
                    userCountInfo.setContinuousLoginCount(1);//连续登录天数
                    userCountInfo.setLastLoginTime(new Date());
                    userCountInfo.setUpdateTime(new Date());
                }else{//是连续登录，设置连续登录天数+1
                    continuousLoginCount = continuousLoginCount + 1 ;
                    userCountInfo.setLoginCount(loginCount);//总登录天数
                    userCountInfo.setContinuousLoginCount(continuousLoginCount);//连续登录天数
                    userCountInfo.setLastLoginTime(new Date());
                    userCountInfo.setUpdateTime(new Date());
                }
                //检查是否获得勋章
                String ruleKey = "LOGIN_"+continuousLoginCount+"_DAYS";//拼接勋章规则的key
                Map<String,Object> filter = new HashMap<>();
                filter.put("ruleKey",ruleKey);
                filter.put("status", MedalRuleStatus.PUBLISH.getType());

                MedalRule medalRule = medalRuleService.getMedalRuleByFilter(filter);

                addOrUpdatePersonalMedal(medalRule, continuousLoginCount, user);

                saveUserCountInfo(userCountInfo);

            }
		}
	}

	public void studyCount(User user, boolean completeStudyFlag){

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String todayDate = format.format(new Date());
		String yesterday = format.format(DateUtils.getYesterday());

		UserCountInfo userCountInfo = getUserCountInfo(user.getId());
		Integer studyChapterCount = 0;
		if(userCountInfo == null) {
			userCountInfo = new UserCountInfo();
			userCountInfo.setId(user.getId());
            userCountInfo.setLoginCount(0);//总登录天数
            userCountInfo.setContinuousLoginCount(0);//连续登录天数
			userCountInfo.setStudyDaysCount(1);//学习天数
            userCountInfo.setLastStudyTime(new Date());
            if(completeStudyFlag){
                studyChapterCount = 1;
            }
			userCountInfo.setStudyChapterCount(studyChapterCount);//学习章节数
			userCountInfo.setCreateTime(new Date());

			createUserCountInfo(userCountInfo);
		} else {
//			Date lastStudyTime = userCountInfo.getLastStudyTime()==null?new Date(0,0,0) : userCountInfo.getLastStudyTime();
//			String lastStudyDate = format.format(lastStudyTime);
            String lastStudyDate = userCountInfo.getLastStudyTime()==null ? "1900-01-01" : format.format(userCountInfo.getLastStudyTime());
            Integer studyDaysCount = userCountInfo.getStudyDaysCount();
            studyChapterCount = userCountInfo.getStudyChapterCount();
            if( ! lastStudyDate.equals(todayDate) ){

				if(! lastStudyDate.equals(yesterday) ){//不是连续学习，设置连续学习天数为1
                    userCountInfo.setStudyDaysCount(1);
					userCountInfo.setLastStudyTime(new Date());
					userCountInfo.setUpdateTime(new Date());
				}else{//是连续学习，设置连续学习天数+1
                    studyDaysCount = studyDaysCount + 1 ;
                    userCountInfo.setStudyDaysCount(studyDaysCount);
					userCountInfo.setLastStudyTime(new Date());
					userCountInfo.setUpdateTime(new Date());
				}

			}
            //检查是否获得勋章
            String ruleKey_studyDas = "STUDY_"+studyDaysCount+"_DAYS";//拼接勋章规则的key
            Map<String,Object> filter_studyDas = new HashMap<>();
            filter_studyDas.put("ruleKey",ruleKey_studyDas);
            filter_studyDas.put("status", MedalRuleStatus.PUBLISH.getType());
            MedalRule medalRule_studyDas = medalRuleService.getMedalRuleByFilter(filter_studyDas);

            addOrUpdatePersonalMedal(medalRule_studyDas, studyDaysCount, user);
			if(completeStudyFlag){
                studyChapterCount = studyChapterCount + 1;

                //检查是否获得勋章连续学习章节数勋章  STUDY_3_CHAPTERS
                String ruleKey_studyChapter = "STUDY_"+studyChapterCount+"_CHAPTERS";//拼接勋章规则的key
                Map<String,Object> filter_studyChapter = new HashMap<>();
                filter_studyChapter.put("ruleKey",ruleKey_studyChapter);
                filter_studyChapter.put("status", MedalRuleStatus.PUBLISH.getType());
                MedalRule medalRule_studyChapter = medalRuleService.getMedalRuleByFilter(filter_studyChapter);

                addOrUpdatePersonalMedal(medalRule_studyChapter, studyChapterCount, user);

            }
            userCountInfo.setStudyChapterCount(studyChapterCount);//学习章节数
            saveUserCountInfo(userCountInfo);
		}
	}

    private UserCountInfo getUserCountInfo(Long id) {
        return userCountInfoService.getUserCountInfo(id);
    }

    private void saveUserCountInfo(UserCountInfo userCountInfo) {
        userCountInfoService.updateUserCountInfo(userCountInfo);
    }

	private void createUserCountInfo(UserCountInfo userCountInfo) {
		userCountInfoService.createUserCountInfo(userCountInfo);
	}

	private void addOrUpdatePersonalMedal(MedalRule medalRule,Integer count, User user){
        if(medalRule != null){
            if(count == Integer.valueOf(medalRule.getRuleValue())){
                Map<String,Object> map = new HashMap<>();
                map.put("userId",user.getId());
                map.put("medalId",medalRule.getMedalId());
                PersonalMedal personalMedal = personalMedalService.getPersonalMedalByFilter(map);
                if(personalMedal == null){
                    personalMedal = new PersonalMedal();
                    personalMedal.setUserId(user.getId());
                    personalMedal.setMedalId(medalRule.getMedalId());
                    personalMedal.setCreateTime(new Date());
                    personalMedal.setStatus(MedalStatus.OBTAIN.getType());
                    personalMedal.setNum(1);
                    personalMedalService.addPersonalMedal(personalMedal);

                    medalApiFacade.countMedalObtainNum(medalRule.getMedalId());

                }else{
                    personalMedal.setNum(personalMedal.getNum() + 1);
                    personalMedal.setUpdateTime(new Date());
                    personalMedalService.updatePersonalMedal(personalMedal);
                }
            }
        }
    }

}
