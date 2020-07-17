package com.autozi.cheke.mobile.web;

import com.autozi.cheke.mobile.constant.MobileConstant;
import com.autozi.cheke.mobile.facade.UserApiFacade;
import com.autozi.cheke.mobile.util.CheckKeyUtils;
import com.autozi.cheke.mobile.util.IDCardValidateUtils;
import com.autozi.cheke.mobile.util.ImageUtils;
import com.autozi.cheke.service.user.IUserLoginStatusService;
import com.autozi.cheke.user.entity.*;
import com.autozi.cheke.user.type.IUserType;
import com.autozi.common.core.exception.B2bException;
import com.autozi.common.utils.util1.MobilePhoneUtils;
import com.autozi.common.utils.util2.DateTools;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 推客App端_登录_注册_个人信息修改
 * @author 魏唯
 *
 */
@RequestMapping("/user/tk/")
@Controller
public class UserApiController extends BaseApiController {

	@Autowired
	private UserApiFacade userApiFacade;

	@Autowired
	private IUserLoginStatusService userLoginStatusService;

	//注册
	@RequestMapping("register.api")
	public void register(HttpServletRequest request, HttpServletResponse response){
		try {
			//手机号
	        String phone = request.getParameter("phone");
	        //验证码
	        String verCode = request.getParameter("verCode");
	        //密码
	        String phonePwd  = request.getParameter("phonePwd");
	        //确认密码
	        String confirmPwd  = request.getParameter("confirmPwd");
	        //邀请码
	        String invitationCode = request.getParameter("invitationCode");
	        //注册信息非空验证
	        if (StringUtils.isBlank(phone)) {
	            this.response(request,response, MobileConstant.Error._mobilePhoneIsEmpty, MobileConstant.Error._mobilePhoneIsEmpty_msg);
	            return;
	        }
	        if (StringUtils.isBlank(verCode)) {
	            this.response(request,response, MobileConstant.Error._mobilePhoneCodeIsEmpty, MobileConstant.Error._mobilePhoneCodeIsEmpty_msg);
	            return;
	        }
	        if (StringUtils.isBlank(phonePwd)) {
	            this.response(request,response, MobileConstant.Error._passwdIsEnpty, MobileConstant.Error._passwdIsEnpty_msg);
	            return;
	        }
	        if (StringUtils.isBlank(confirmPwd)) {
	            this.response(request,response, MobileConstant.Error._confirmPwdIsEnpty, MobileConstant.Error._confirmPwdIsEnpty_msg);
	            return;
	        }
	        //手机号码格式验证
            if (!MobilePhoneUtils.checkPhone(phone)) {
                this.response(response, MobileConstant.Error._mobilePhoneIsError, MobileConstant.Error._mobilePhoneIsError_msg);
                return;
            }
            //手机号码已经存在
            if (userApiFacade.checkMobileExists(phone)) {
                this.response(response, MobileConstant.Error._mobilePhoneExist, MobileConstant.Error._mobilePhoneExist_msg);
                return;
            }
            //验证手机验证码
            if (!userApiFacade.checkMobilePhoneCode(phone, verCode)) {
                this.response(response, MobileConstant.Error._mobilePhoneCodeIsError, MobileConstant.Error._mobilePhoneCodeIsError_msg);
                return;
            }
	        //两次密码是否一致验证     加密前验证?
	        if (!phonePwd.equals(confirmPwd)) {
	        	this.response(request,response, MobileConstant.Error._dupNewIsDiff, MobileConstant.Error._dupNewIsDiff_msg);
	            return;
			}
	        //邀请码是否正确验证
	        if(StringUtils.isNotBlank(invitationCode)){
	        	if(!userApiFacade.isExistInviteCode(invitationCode)){
	        		this.response(request,response, MobileConstant.Error._invitationCodeIsError, MobileConstant.Error._invitationCodeIsError_msg);
	        		return;
	        	}
	        }
	        //对用户信息加密保存
	        Long userId = userApiFacade.registerUser(phone, phonePwd,invitationCode);
	        UserLoginStatus userLoginStatus = new UserLoginStatus();
            userLoginStatus.setId(userId);
            userLoginStatus.setToken(CheckKeyUtils.createToken(phone));
            userLoginStatus.setLastLoginTime(DateTools.getCurrentDate());
            userLoginStatus.setCreateTime(DateTools.getCurrentDate());
            userApiFacade.createUserLoginStatus(userLoginStatus);
			//添加系统通知
			userApiFacade.createNotice(userId);
            JSONObject data = new JSONObject();
            data.put("token", userLoginStatus.getToken());
			data.put("name", phone);
			data.put("phone", phone);
			data.put("invitationCode",invitationCode==null?"":invitationCode);
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
		}catch (B2bException e){
           e.printStackTrace(); 
           this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
	
	//登录
	@RequestMapping("login.api")
	public void login(HttpServletRequest request, HttpServletResponse response){
		try {
			String phone = request.getParameter("phone");
			String phonePwd = request.getParameter("phonePwd");
			//用户名和密码非空验证
			if (StringUtils.isBlank(phone)) {
	            this.response(request,response, MobileConstant.Error._loginNameIsEmpty, MobileConstant.Error._loginNameIsEmpty_msg);
	            return;
	        }
	        if (StringUtils.isBlank(phonePwd)) {
	            this.response(request,response, MobileConstant.Error._passwdIsEnpty, MobileConstant.Error._passwdIsEnpty_msg);
	            return;
	        }
	        //手机号码格式验证
//	        if (!MobilePhoneUtils.checkPhone(phone)) {
//	            this.response(response, MobileConstant.Error._mobilePhoneIsError, MobileConstant.Error._mobilePhoneIsError_msg);
//	            return;
//	        }
	        //用户名和密码正确性验证
	        User user = userApiFacade.findUserByLoginName(phone);
			boolean tokenFlag = true;
			if(user==null){
				user = userApiFacade.getUserByLoginName(phone);
				if(user!=null){
					tokenFlag = false;
				}
			}
	        if (user == null) {
	            this.response(request,response, MobileConstant.Error._userPasswdIsError, MobileConstant.Error._userPasswdIsError_msg);
	            return;
	        }
			//判断是否是推客登录的
			if(IUserType.USER_TYPE_TUIKE != user.getUserType()){
				this.response(request,response, MobileConstant.Error._userNotLogin, MobileConstant.Error._userNotLogin_msg);
				return;
			}
			//判断失败登录次数
			Date lastLoginTime1 = user.getLastLoginTime()==null?new Date():user.getLastLoginTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String lastLoginDate1 = format.format(lastLoginTime1);
			String todayDate1 = format.format(new Date());
			int failedCount = user.getLoginFailedCount()==null?0:user.getLoginFailedCount();
			if(todayDate1.equals(lastLoginDate1) && failedCount>=5){
				this.response(request,response, MobileConstant.Error._loginFailedCount, MobileConstant.Error._loginFailedCount_msg);
				return;
			}
			// 验证密码是否正确
			String db_passwd = new Md5PasswordEncoder().encodePassword(phonePwd, user.getPhone());
			if (!db_passwd.equals(user.getPassword())) {
				//计算登录错误
				Date lastLoginTime = user.getLastLoginTime()==null?new Date():user.getLastLoginTime();
				String lastLoginDate = format.format(lastLoginTime);
				String todayDate = format.format(new Date());
				if(lastLoginDate.equals(todayDate)){
					failedCount++;
				}else{
					failedCount=1;
				}
				User temp = new User();
				temp.setId(user.getId());
				temp.setLastLoginTime(new Date());
				temp.setLoginFailedCount(failedCount);
				userApiFacade.updateUser(temp);
				String failedMsg = String.valueOf(5-failedCount);
				this.response(request,response, MobileConstant.Error._userPasswdIsError, MobileConstant.Error._userPasswdIsError_msg+",今日还有"+failedMsg+"次登录机会");
				return;
			}
//	        // 用户状态有效性检查
	        if (IUserType.STATUS_NORMAL != user.getUserStatus()) {
	            this.response(request,response, MobileConstant.Error._userNuEnabled, MobileConstant.Error._userNuEnabled_msg);
	            return;
	        }

//	        //用户登录状态重置  token 
	        UserLoginStatus userLoginStatus = userApiFacade.getUserLoginStatus(user.getId());
			if(tokenFlag){
				if (userLoginStatus == null) {
					userLoginStatus = new UserLoginStatus();
					userLoginStatus.setId(user.getId());
					userLoginStatus.setToken(CheckKeyUtils.createToken(phone));
					userLoginStatus.setLastLoginTime(DateTools.getCurrentDate());
					userLoginStatus.setCreateTime(DateTools.getCurrentDate());
					userApiFacade.createUserLoginStatus(userLoginStatus);
				} else {
					userLoginStatus.setToken(CheckKeyUtils.createToken(phone));
					userLoginStatus.setLastLoginTime(DateTools.getCurrentDate());
					userApiFacade.updateUserLoginStatus(userLoginStatus);
				}
			}
            //登录成功统计登录次数
            userApiFacade.loginCount(user);
			//登录成功清零登录失败次数
			User temp = new User();
			temp.setId(user.getId());
			temp.setLastLoginTime(new Date());
			temp.setLoginFailedCount(0);
			//temp.setPhonePassword(new Md5PasswordEncoder().encodePassword(phonePwd, "phone"));
			userApiFacade.updateUser(temp);

	        // 登录成功--返回Token
	        JSONObject data = new JSONObject();
	        data.put("token", userLoginStatus.getToken());
			String name = "";
			if(user.getName()!=null){
				name = user.getName();
			}else if(user.getLoginName()!=null){
				name = user.getLoginName();
			}else{
				name = user.getPhone();
			}
			data.put("name",name);
			data.put("realName",user.getRealName());
			data.put("phone",user.getPhone());
			PersonalParty personalParty = userApiFacade.getPersonalParty(user.getPartyId());
			data.put("imageUrl",personalParty.getImageUrl()==null?"":personalParty.getImageUrl());
			data.put("invitationCode",personalParty.getInviteCode()==null?"":personalParty.getInviteCode());
	        this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg, data.toString());
	        return;
		}catch (B2bException e){
           e.printStackTrace();
           this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
	
	//忘记密码
	@RequestMapping("forgetPwd.api")
	public void forgetPwd(HttpServletRequest request, HttpServletResponse response){
		try {
			//手机号
	        String phone = request.getParameter("phone");
	        //验证码
	        String verCode = request.getParameter("verCode");
	        //新密码
	        String newPhonePwd  = request.getParameter("newPhonePwd");
	        //确认新密码
	        String newConfirmPwd  = request.getParameter("newConfirmPwd");
	        //注册信息非空验证
	        if (StringUtils.isBlank(phone)) {
	            this.response(request,response, MobileConstant.Error._mobilePhoneIsEmpty, MobileConstant.Error._mobilePhoneIsEmpty_msg);
	            return;
	        }
	        if (StringUtils.isBlank(verCode)) {
	            this.response(request,response, MobileConstant.Error._mobilePhoneCodeIsEmpty, MobileConstant.Error._mobilePhoneCodeIsEmpty_msg);
	            return;
	        }
	        if (StringUtils.isBlank(newPhonePwd)) {
	            this.response(request,response, MobileConstant.Error._passwdIsEnpty, MobileConstant.Error._passwdIsEnpty_msg);
	            return;
	        }
	        if (StringUtils.isBlank(newConfirmPwd)) {
	            this.response(request,response, MobileConstant.Error._confirmPwdIsEnpty, MobileConstant.Error._confirmPwdIsEnpty_msg);
	            return;
	        }
	        User user = userApiFacade.findUserByLoginName(phone);
	        //手机号码不存在
            if (user == null) {
                this.response(response, MobileConstant.Error._userIsNotExist, MobileConstant.Error._userIsNotExist_msg);
                return;
            }
            //手机号码格式验证
            if (!MobilePhoneUtils.checkPhone(phone)) {
                this.response(response, MobileConstant.Error._mobilePhoneIsError, MobileConstant.Error._mobilePhoneIsError_msg);
                return;
            }
	        //两次密码是否一致验证     加密前验证?
	        if (!(newPhonePwd.equals(newConfirmPwd))) {
	        	this.response(request,response, MobileConstant.Error._dupNewIsDiff, MobileConstant.Error._dupNewIsDiff_msg);
	            return;
			}
	        //验证手机验证码
            if (!userApiFacade.checkMobilePhoneCode(phone, verCode)) {
                this.response(response, MobileConstant.Error._mobilePhoneCodeIsError, MobileConstant.Error._mobilePhoneCodeIsError_msg);
                return;
            }
			String new_passwd = new Md5PasswordEncoder().encodePassword(newPhonePwd, user.getPhone());
			//对用户信息加密后更新密码
			userApiFacade.updateMd5Password(user.getId(),new_passwd);
			//更新token信息
			UserLoginStatus userLoginStatus = userApiFacade.getUserLoginStatus(user.getId());
			userLoginStatus.setToken(CheckKeyUtils.createToken(phone));
			userLoginStatus.setLastLoginTime(DateTools.getCurrentDate());
			userApiFacade.updateUserLoginStatus(userLoginStatus);
			JSONObject data = new JSONObject();
			data.put("token", userLoginStatus.getToken());
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg, data.toString());
		}catch (B2bException e){
           e.printStackTrace();
           this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
	
	//修改密码
	@RequestMapping("editPwd.api")
	public void editPwd(HttpServletRequest request, HttpServletResponse response){
		try {
			super.checkToken(request, response);
	        User user = super.getUser(request);
			//手机号
	        String phone = request.getParameter("phone");
	        //验证码
	        String verCode = request.getParameter("verCode");
	        //新密码
	        String oldPhonePwd  = request.getParameter("oldPhonePwd");
	        //确认新密码
	        String newPhonePwd  = request.getParameter("newPhonePwd");
	        //信息非空验证
	        if (StringUtils.isBlank(phone)) {
	            this.response(request,response, MobileConstant.Error._mobilePhoneIsEmpty, MobileConstant.Error._mobilePhoneIsEmpty_msg);
	            return;
	        }
	        if (StringUtils.isBlank(verCode)) {
	            this.response(request,response, MobileConstant.Error._mobilePhoneCodeIsEmpty, MobileConstant.Error._mobilePhoneCodeIsEmpty_msg);
	            return;
	        }
	        if (StringUtils.isBlank(oldPhonePwd)) {
	            this.response(request,response, MobileConstant.Error._oldPdMust, MobileConstant.Error._oldPdMust_msg);
	            return;
	        }
	        if (StringUtils.isBlank(newPhonePwd)) {
	            this.response(request,response, MobileConstant.Error._newPdMust, MobileConstant.Error._newPdMust_msg);
	            return;
	        }
	        //旧密码正确性验证
	        String db_passwd = new Md5PasswordEncoder().encodePassword(oldPhonePwd, user.getPhone());
	        if (!db_passwd.equals(user.getPassword())) {
	            this.response(request,response, MobileConstant.Error._userPasswdIsError, MobileConstant.Error._userPasswdIsError_msg);
	            return;
	        }
	        //新旧密码不能一致  用验证么还是让前台验证?
	        //改完用退出登录么?
	        
	        String new_passwd = new Md5PasswordEncoder().encodePassword(newPhonePwd, user.getPhone());
            userApiFacade.updateMd5Password(user.getId(), new_passwd);
			//更新token信息
			UserLoginStatus userLoginStatus = userApiFacade.getUserLoginStatus(user.getId());
			userLoginStatus.setToken(CheckKeyUtils.createToken(phone));
			userLoginStatus.setLastLoginTime(DateTools.getCurrentDate());
			userApiFacade.updateUserLoginStatus(userLoginStatus);
			JSONObject data = new JSONObject();
			data.put("token", userLoginStatus.getToken());
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg, data.toString());
		}catch (B2bException e){
           e.printStackTrace();
           this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
	
	//修改手机号
	@RequestMapping("editPhone.api")
	public void editPhone(HttpServletRequest request, HttpServletResponse response){
		try {
			super.checkToken(request, response);
	        User user = this.getUser(request);
			//是否登录
			if(user==null){
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
	        String phone = request.getParameter("phone");
	        String mobilePhoneCode = request.getParameter("verCode");
			String password = request.getParameter("password");
            if (StringUtils.isBlank(phone)) {
                this.response(response, MobileConstant.Error._mobilePhoneIsEmpty, MobileConstant.Error._mobilePhoneIsEmpty_msg);
                return;
            }
            //手机号码格式验证
            if (!MobilePhoneUtils.checkPhone(phone)) {
                this.response(response, MobileConstant.Error._mobilePhoneIsError, MobileConstant.Error._mobilePhoneIsError_msg);
                return;
            }
            //手机号码已经存在
            if (userApiFacade.findUserByLoginName(phone) != null) {
                this.response(response, MobileConstant.Error._mobilePhoneExist, MobileConstant.Error._mobilePhoneExist_msg);
                return;
            }
            //手机验证码必须输入
            if (StringUtils.isBlank(mobilePhoneCode)) {
                this.response(response, MobileConstant.Error._mobilePhoneCodeIsEmpty, MobileConstant.Error._mobilePhoneCodeIsEmpty_msg);
                return;
            }
            //验证手机验证码
            if (!userApiFacade.checkMobilePhoneCode(phone, mobilePhoneCode)) {
                this.response(response, MobileConstant.Error._mobilePhoneCodeIsError, MobileConstant.Error._mobilePhoneCodeIsError_msg);
                return;
            }

			//验证原来的密码是否正确
			// 验证密码是否正确
			String db_passwd = new Md5PasswordEncoder().encodePassword(password, user.getPhone());
			if (!db_passwd.equals(user.getPassword())) {
				this.response(request,response, MobileConstant.Error._userPasswdIsError, MobileConstant.Error._userPasswdIsError_msg);
				return;
			}
			user.setPassword( new Md5PasswordEncoder().encodePassword(password, phone));
            user.setPhone(phone);
            userApiFacade.updateUser(user);
			//更新token信息
			UserLoginStatus userLoginStatus = userApiFacade.getUserLoginStatus(user.getId());
			userLoginStatus.setToken(CheckKeyUtils.createToken(phone));
			userLoginStatus.setLastLoginTime(DateTools.getCurrentDate());
			userApiFacade.updateUserLoginStatus(userLoginStatus);
			JSONObject data = new JSONObject();
			data.put("token", userLoginStatus.getToken());
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg, data.toString());
			}catch (B2bException e){
	            e.printStackTrace();
	            this.response(request,response, MobileConstant.Error._error, e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			    this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
	
	//个人主页
	@RequestMapping("myInfo.api")
	public void myInfo(HttpServletRequest request, HttpServletResponse response){
		try {
			this.checkToken(request, response);
			User user = this.getUser(request);
			if (user == null) {
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
			JSONObject data = userApiFacade.getMyInfo(user);
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
		}catch (B2bException e){
           e.printStackTrace();
           this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
	
	//个人信息修改
	@RequestMapping("updateMyInfo.api")
	public void updateMyInfo(HttpServletRequest request, HttpServletResponse response){
		try {
			this.checkToken(request, response);
			User user = this.getUser(request);
			if (user == null) {
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
			String name = request.getParameter("name");
			String realName = request.getParameter("realName");
			String loginName = request.getParameter("loginName");
			String description = request.getParameter("description");
			String gender = request.getParameter("gender");
			String address = request.getParameter("areaId");//将areaId存入address字段
			String companyName = request.getParameter("companyName");
			String jobName = request.getParameter("jobName");
			String IDCardNumber = request.getParameter("IDCardNumber");
			PersonalParty personalParty = userApiFacade.getPersonalParty(user.getPartyId());

			if(name!=null && !name.equals("")){
				if(name.length()>15||name.length()<2){
					this.response(request,response, MobileConstant.Error._nameLength,MobileConstant.Error._nameLength_msg);
					return;
				}
				user.setName(name);
			}
			if(realName!=null && !realName.equals("")){
				if(realName.length()>30||realName.length()<2){
					this.response(request,response, MobileConstant.Error._realNameLength,MobileConstant.Error._realNameLength_msg);
					return;
				}
				user.setRealName(realName);
			}
			if(loginName!=null && !loginName.equals("")){
				if(loginName.length()>20||loginName.length()<2){
					this.response(request,response, MobileConstant.Error._loginNameLength,MobileConstant.Error._loginNameLength_msg);
					return;
				}
				if(user.getLoginName()!=null && !user.getLoginName().equals("")){
					this.response(request,response, MobileConstant.Error._tkOnlyOne,MobileConstant.Error._tkOnlyOne_msg);
					return;
				}
				User userTemp = userApiFacade.getUserByLoginName(loginName);
				if(userTemp!=null){
					this.response(request,response, MobileConstant.Error._loginNameOnly,MobileConstant.Error._loginNameOnly_msg);
					return;
				}
				user.setLoginName(loginName);
			}
			if(description!=null && !description.equals("")){
				if(description.length()>30){
					this.response(request,response, MobileConstant.Error._descriptionLength,MobileConstant.Error._descriptionLength_msg);
					return;
				}
				personalParty.setDescription(description);
			}
			if(gender!=null && !gender.equals("")){
				user.setGender(gender);
			}
			if(address!=null && !address.equals("")){
				if(address.length()>50){
					this.response(request,response, MobileConstant.Error._addressLength,MobileConstant.Error._addressLength_msg);
					return;
				}
				personalParty.setAddress(address);
			}
			if(companyName!=null && !companyName.equals("")){
				if(companyName.length()>50){
					this.response(request,response, MobileConstant.Error._companyNameLength,MobileConstant.Error._companyNameLength_msg);
					return;
				}
				personalParty.setCompanyName(companyName);
			}
			if(jobName!=null && !jobName.equals("")){
				if(jobName.length()>50){
					this.response(request,response, MobileConstant.Error._jobNameLength,MobileConstant.Error._jobNameLength_msg);
					return;
				}
				personalParty.setJobName(jobName);
			}
			if(IDCardNumber!=null && !IDCardNumber.equals("")){
				if(IDCardNumber.length()!=15&&IDCardNumber.length()!=18){
					this.response(request,response, MobileConstant.Error._IDCardNumberLength,MobileConstant.Error._IDCardNumberLength_msg);
					return;
				}
				IDCardValidateUtils iv = new IDCardValidateUtils();
				if(!iv.isValidatedAllIdcard(IDCardNumber)){
					this.response(request,response, MobileConstant.Error._IDCardValidate,MobileConstant.Error._IDCardValidate_msg);
					return;
				}
				personalParty.setIdCardNumber(IDCardNumber);
			}
			userApiFacade.saveMyInfo(user,personalParty);
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg);
		}catch (B2bException e){
           e.printStackTrace();
           this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
	
	//身份证显示
	@RequestMapping("showIDCard.api")
	public void showIDCard(HttpServletRequest request, HttpServletResponse response){
		try {
			User user = this.getUser(request);
			if (user == null) {
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			} 
			JSONObject data = userApiFacade.showIDCard(user);
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
		}catch (B2bException e){
           e.printStackTrace();
           this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
	
	//上传身份证
	@RequestMapping("saveIDCard.api")
	public void saveIDCard(HttpServletRequest request, HttpServletResponse response, MultipartFile file,Integer identifyType){
		try {
			User user = this.getUser(request);
			if (user == null) {
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
			//是否是图片校验
			if (!ImageUtils.isImage(file.getBytes())) {
				this.response(request,response, MobileConstant.Error._isNotImg,MobileConstant.Error._isNotImg_msg);
				return;
			}
			//图片大小
			if (file.getSize() > 1024 * 1024 * 3) {
				this.response(request,response, MobileConstant.Error._imgIsBig,MobileConstant.Error._imgIsBig_msg);
				return;
			}
			JSONObject data = new JSONObject();
			String imageUrl = userApiFacade.saveIDCard(user, file,identifyType);
			data.put("imageUrl",imageUrl );
			data.put("IDCardStatus",IUserType.TUIKE_STATUS_VERIFY);
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
		}catch (B2bException e){
           e.printStackTrace();
           this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}

	//上传头像
	@RequestMapping("uploadHeadImage.api")
	public void uploadHeadImage(HttpServletRequest request, HttpServletResponse response){
		try {
			User user = this.getUser(request);
			if (user == null) {
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
			MultipartHttpServletRequest mulRequest = null;
			if (request instanceof MultipartHttpServletRequest) {
				mulRequest = (MultipartHttpServletRequest)request;
				MultipartFile file = mulRequest.getFile("file");
				//是否是图片校验
				if (!ImageUtils.isImage(file.getBytes())) {
					this.response(request,response, MobileConstant.Error._isNotImg,MobileConstant.Error._isNotImg_msg);
					return;
				}
				//图片大小
				if (file.getSize() > 1024 * 1024 * 3) {
					this.response(request,response, MobileConstant.Error._imgIsBig,MobileConstant.Error._imgIsBig_msg);
					return;
				}
				JSONObject data = new JSONObject();
				String imageUrl = userApiFacade.uploadHeadImage(user, file);
				data.put("imageUrl",imageUrl );
				this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
			}
		}catch (B2bException e){
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
	
	//获取手机验证码
    @RequestMapping("/getMobilePhoneCode.api")
    public void getMobilePhoneCode(HttpServletRequest request, HttpServletResponse response) {
        String mobilePhone = request.getParameter("mobilePhone");
        JSONObject data = new JSONObject();
        try {
            if (StringUtils.isBlank(mobilePhone)) {
                this.response(response, MobileConstant.Error._mobilePhoneIsEmpty, MobileConstant.Error._mobilePhoneIsEmpty_msg);
                return;
            }
            //手机号码格式验证
            if (!MobilePhoneUtils.checkPhone(mobilePhone)) {
                this.response(response, MobileConstant.Error._mobilePhoneIsError, MobileConstant.Error._mobilePhoneIsError_msg);
                return;
            }
            String type = request.getParameter("type");
            if (StringUtils.isNotBlank(type) && (type.endsWith("1") || type.endsWith("3"))) {//注册或者修改手机号码
                //手机号码已经存在
                if (userApiFacade.findUserByLoginName(mobilePhone) != null) {
                    this.response(response, MobileConstant.Error._mobilePhoneExist, MobileConstant.Error._mobilePhoneExist_msg);
                    return;
                }
            } else if (StringUtils.isNotBlank(type) && type.endsWith("2")) {//找回密码
                if (userApiFacade.findUserByLoginName(mobilePhone) == null) {
                    this.response(response, MobileConstant.Error._userIsNotExist, MobileConstant.Error._userIsNotExist_msg);
                    return;
                }
            }
            //验证一天内，发送验证码的次数是否超过最大限制
            if (!userApiFacade.checkMaxSendNumber(mobilePhone)) {
                this.response(response, MobileConstant.Error._mobilePhoneCodeMaxSend, MobileConstant.Error._mobilePhoneCodeMaxSend_msg, data.toString());
                return;
            }
            if (StringUtils.isBlank(type)) {
                type = "4";
            }
            String code = userApiFacade.getMobilePhoneCode(mobilePhone, Integer.valueOf(type));
            data.put("mobilePhoneCode", code);
            this.response(response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg, data.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            this.response(response, MobileConstant.Error._error, MobileConstant.Error._error_msg, data.toString());
        }
    }

	//获取手机验证码
	@RequestMapping("/getRegisterMobilePhoneCode.api")
	public void getRegisterMobilePhoneCode(HttpServletRequest request, HttpServletResponse response) {
		String mobilePhone = request.getParameter("mobilePhone");
		JSONObject data = new JSONObject();
		try {
			if (StringUtils.isBlank(mobilePhone)) {
				this.response(response, MobileConstant.Error._mobilePhoneIsEmpty, MobileConstant.Error._mobilePhoneIsEmpty_msg);
				return;
			}
			//手机号码格式验证
			if (!MobilePhoneUtils.checkPhone(mobilePhone)) {
				this.response(response, MobileConstant.Error._mobilePhoneIsError, MobileConstant.Error._mobilePhoneIsError_msg);
				return;
			}
			if (userApiFacade.findUserByLoginName(mobilePhone) != null) {
				this.response(response, MobileConstant.Error._mobilePhoneExist, MobileConstant.Error._mobilePhoneExist_msg);
				return;
			}
			//验证一天内，发送验证码的次数是否超过最大限制
			if (!userApiFacade.checkMaxSendNumber(mobilePhone)) {
				this.response(response, MobileConstant.Error._mobilePhoneCodeMaxSend, MobileConstant.Error._mobilePhoneCodeMaxSend_msg, data.toString());
				return;
			}
			String code = userApiFacade.getMobilePhoneCode(mobilePhone, 1);
			data.put("mobilePhoneCode", code);
			this.response(response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg, data.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			this.response(response, MobileConstant.Error._error, MobileConstant.Error._error_msg, data.toString());
		}
	}
	
    //设置
    @RequestMapping("setting.api")
	public void setting(HttpServletRequest request, HttpServletResponse response){
		try {
			User user = this.getUser(request);
			//是否登录
			if(user==null){
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
			JSONObject data = userApiFacade.setting(user);
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
		}catch (B2bException e){
           e.printStackTrace();
           this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}
    
    //发展推客
    @RequestMapping("developTK.api")
	public void developTK(HttpServletRequest request, HttpServletResponse response){
		try {
			User user = this.getUser(request);
			//是否登录
			if(user==null){
				this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
				return;
			}
			JSONObject data = userApiFacade.developTK(user);
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
		}catch (B2bException e){
           e.printStackTrace();
           this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}

	//地区选择
	@RequestMapping("getArea.api")
	public void getArea(HttpServletRequest request, HttpServletResponse response,Long parentId){
		try {
			if(parentId==null){
				parentId = 0l;
			}
			JSONObject data = userApiFacade.getAreaByParentId(parentId);
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
		}catch (B2bException e){
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}

	//获取所有area
	@RequestMapping("area.api")
	public void area(HttpServletRequest request, HttpServletResponse response){
		try {
			JSONObject data = userApiFacade.getAllArea();
			this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
		}catch (B2bException e){
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
		}
	}

	//意见反馈
    @RequestMapping("feedBack.api")
	public void feedBack(HttpServletRequest request , HttpServletResponse response){
        try {
            User user = this.getUser(request);
            //是否登录
            if(user==null){
                this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
                return;
            }
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String contact_info = request.getParameter("contact_info")==null ? "" : request.getParameter("contact_info");
            if(title == null || "".equals(title)
                    ||content == null || "".equals(content)){
                this.response(request,response, MobileConstant.Error._nullInput, MobileConstant.Error._nullInput_msg);
                return;
            }
			if(title.length()>30){
				this.response(request,response, MobileConstant.Error._titleLength,MobileConstant.Error._titleLength_msg);
				return;
			}
            if(content.length()>200){
                this.response(request,response, MobileConstant.Error._feedbackLength,MobileConstant.Error._feedbackLength_msg);
                return;
            }
            FeedBack feedBack = new FeedBack();
            feedBack.setTitle(title);
            feedBack.setContent(content);
            feedBack.setContactInfo(contact_info);
            feedBack.setCreateTime(new Date());
            feedBack.setCreateUserId(user.getId());

            userApiFacade.addFeedBack(feedBack);
            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg);
        }catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
	}

    //搜索反馈
    @RequestMapping("feedBackForsearch.api")
    public void feedBackForsearch(HttpServletRequest request , HttpServletResponse response){
        try {
            User user = this.getUser(request);
            //是否登录
            if(user==null){
                this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
                return;
            }
            String content = request.getParameter("content");

            if(content.length()>200){
                this.response(request,response, MobileConstant.Error._feedbackLength,MobileConstant.Error._feedbackLength_msg);
                return;
            }
            SearchFeedBack searchfeedBack = new SearchFeedBack();
            searchfeedBack.setContent(content);
            searchfeedBack.setCreateTime(new Date());
            searchfeedBack.setUserId(user.getId());

            userApiFacade.addSearchFeedBack(searchfeedBack);
            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg);
        }catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }

    @RequestMapping("loginCount.api")
    public void loginCount(HttpServletRequest request , HttpServletResponse response){
        try {
            User user = this.getUser(request);
            //是否登录
            if(user==null){
                this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
                return;
            }

            userApiFacade.loginCount(user);
            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg);
        }catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }
}
