package com.autozi.cheke.web.party.controller;

import com.autozi.cheke.basic.entity.Area;
import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.party.entity.PartyLog;
import com.autozi.cheke.party.type.IPartyType;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.util.web.MobilePhoneUtils;
import com.autozi.cheke.web.basic.facade.AreaFacade;
import com.autozi.cheke.web.party.facade.PartyFacade;
import com.autozi.cheke.web.party.vo.PartyView;
import com.autozi.cheke.web.user.facade.UserFacade;
import com.autozi.common.utils.util3.SMSUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by lbm on 2017/11/28.
 */
@RequestMapping("/cheke/party")
@Controller
public class PartyController extends BaseController {
    @Autowired
    private PartyFacade partyFacade;
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private AreaFacade areaFacade;

    @RequestMapping("/edit/editParty.action")
    public void editParty(PartyView partyView, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject jsonObject = new JSONObject();
        String msg = "ok";
        String phoneCode = partyView.getValidateCode();
        String sessionCode = (String) request.getSession().getAttribute(MobilePhoneUtils.PHONE_VALIDATE_SESSION);
        if (phoneCode == null || "".equals(phoneCode)) {
            msg = "手机验证码不能为空";
            jsonObject.put("msg", msg);
            this.response(response, jsonObject.toString());
            return;
        }
        if (!phoneCode.equals(sessionCode)) {
            msg = "手机验证码错误";
            jsonObject.put("msg", msg);
            this.response(response, jsonObject.toString());
            return;
        }
        partyFacade.editParty(partyView);
        jsonObject.put("msg", msg);
        this.response(response, jsonObject.toString());
    }

    @RequestMapping("/edit/getPhoneValidateCode.action")
    public void getPhoneValidateCode(String mobile, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject jsonObject = new JSONObject();
        String msg = "ok";
        if (mobile == null) {
            msg = "手机号不能为空";
            this.response(response, jsonObject.toString());
            return;
        }
        String code = MobilePhoneUtils.createRandom(MobilePhoneUtils.NUMBER_FLAG_TRUE, 6);
        request.getSession().setAttribute(MobilePhoneUtils.PHONE_VALIDATE_SESSION, code);
        SMSUtils.sendMessage(mobile, new String[]{code},SMSUtils.MODEL_ID_CK_ZC);
        jsonObject.put("msg", msg);
        this.response(response, jsonObject.toString());
    }

    @RequestMapping("/edit/userInfo.action")
    public String userInfo(Model uiModel) throws Exception {
        //获取用户和主体信息
        User user = getCurrentUser();
        if (user == null) {
            return "/index/login";
        }
        Long userId = user.getId();
        Long partyId = user.getPartyId();
        Party party = partyFacade.getPartyById(partyId);
        PartyLog partyLog = partyFacade.getPartyLogByPartyId(partyId);
        PartyView partyView = new PartyView();
        if(partyLog!=null){
            partyView.setRefuseReason(partyLog.getRefuseReason()==null?"":partyLog.getRefuseReason());
        }
        BeanUtils.copyProperties(party,partyView);
        int fansCount = partyFacade.getFansCount(partyId);
        uiModel.addAttribute("user", user);
        uiModel.addAttribute("party", partyView);
        uiModel.addAttribute("fansCount", fansCount);
        Integer status = party.getStatus();
        if (status == IPartyType.STATUS_REGISTER) {
            return "/user/userInfo.html";
        }

        //******以下为进入修改页面********
        Integer companyType = party.getCompanyType();
        Integer mediaType = party.getMediaType();//媒介属性和个人职位类别共用字段
        Integer partyClass = party.getPartyClass();

        String url = "";
        Map<String, String> companyTypeMap = null;
        Map<String, String> mediaTypeMap = null;
        String companyTypeName = "";
        if (partyClass == IPartyType.PARTY_CLASS_COMPANY) {
            companyTypeMap = IPartyType.COMPANY_CLASS_COMPANY;
            if (companyType != null && companyType != 0) {
                companyTypeName = IPartyType.COMPANY_CLASS_COMPANY.get(String.valueOf(companyType));
            }
            url = "/user/company.html";
        } else if (partyClass == IPartyType.PARTY_CLASS_GOVERNMENT) {
            companyTypeMap = IPartyType.COMPANY_CLASS_ORGAN;
            if (companyType != null && companyType != 0) {
                companyTypeName = IPartyType.COMPANY_CLASS_ORGAN.get(String.valueOf(companyType));
            }
            url = "/user/organ.html";
        } else if (partyClass == IPartyType.PARTY_CLASS_MEDIA) {
            companyTypeMap = IPartyType.COMPANY_CLASS_MEDIA;
            mediaTypeMap = IPartyType.mediaType;
            if (companyType != null && companyType != 0) {
                companyTypeName = IPartyType.COMPANY_CLASS_MEDIA.get(String.valueOf(companyType));
            }
            url = "/user/media.html";
        } else if (partyClass == IPartyType.PARTY_CLASS_PERSON) {
            companyTypeMap = IPartyType.COMPANY_CLASS_PERSON;
            mediaTypeMap = IPartyType.personType;
            if (companyType != null && companyType != 0) {
                companyTypeName = IPartyType.COMPANY_CLASS_PERSON.get(String.valueOf(companyType));
            }
            if (mediaType != null && mediaType != 0) {
                uiModel.addAttribute("mediaTypeName", IPartyType.personType.get(String.valueOf(mediaType)));
            }
            url = "/user/person.html";
        }

        uiModel.addAttribute("companyType", companyTypeMap);
        uiModel.addAttribute("mediaType", mediaTypeMap);
        uiModel.addAttribute("companyTypeName", companyTypeName);

        Long areaId = party.getAreaId();
        String areaNameOne = "";//地区一级名称
        String areaNameTwo = "";//地区二级名称
        String areaName = "";//地区三级名称
        if (areaId != null && areaId != 0) {
            Area area = areaFacade.getAreaById(areaId);//获取地区名称
            areaName = area.getName();
            Long areaIdTwo = area.getParentId();
            Area areaTwo = areaFacade.getAreaById(areaIdTwo);
            areaNameTwo = areaTwo.getName();
            Area areaOne = areaFacade.getAreaById(areaTwo.getParentId());
            areaNameOne = areaOne.getName();
        }
        uiModel.addAttribute("areaName", areaName);
        uiModel.addAttribute("areaNameTwo", areaNameTwo);
        uiModel.addAttribute("areaNameOne", areaNameOne);

        return url;
    }

    /**
     * 跳转到新增主体信息
     *
     * @param uiModel
     * @param partyClass
     * @return
     * @throws Exception
     */
    @RequestMapping("/add/toAddParty.action")
    public String toAddParty(Model uiModel, Integer partyClass) throws Exception {
        //获取用户和主体信息
        User user = getCurrentUser();
        Long partyId = user.getPartyId();
        Party party = partyFacade.getPartyById(partyId);

        //如果partyClass为空，默认为1
        if (partyClass == null) {
            partyClass = 1;
        }
        //根据主体类型不同，跳转到不同页面
        String typeName = "公司";
        String url = "/party/company.html";
        Map<String, String> companyTypeMap = IPartyType.COMPANY_CLASS_COMPANY;
        Map<String, String> mediaTypeMap = IPartyType.COMPANY_CLASS_MEDIA;
        if (partyClass == 2) {
            typeName = "机构";
            companyTypeMap = IPartyType.COMPANY_CLASS_ORGAN;
            url = "/party/organ.html";
        } else if (partyClass == 3) {
            typeName = "媒介";
            companyTypeMap = IPartyType.COMPANY_CLASS_MEDIA;
            url = "/party/media.html";
        } else if (partyClass == 4) {
            typeName = "个人";
            companyTypeMap = IPartyType.COMPANY_CLASS_PERSON;
            mediaTypeMap = IPartyType.personType;
            url = "/party/person.html";
        }

        uiModel.addAttribute("typeName", user);
        uiModel.addAttribute("user", user);
        uiModel.addAttribute("mediaType", mediaTypeMap);
        uiModel.addAttribute("companyType", companyTypeMap);
        uiModel.addAttribute("party", party);

        return url;
    }

    /**
     * 新增主体信息
     *
     * @param partyView
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/add/addParty.action")
    public void addParty(PartyView partyView, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject jsonObject = new JSONObject();
        String msg = "ok";
        String phoneCode = partyView.getValidateCode();
        String sessionCode = (String) request.getSession().getAttribute(MobilePhoneUtils.PHONE_VALIDATE_SESSION);
        if (phoneCode == null || "".equals(phoneCode)) {
            msg = "手机验证码不能为空";
            jsonObject.put("msg", msg);
            this.response(response, jsonObject.toString());
            return;

        }
        if (!phoneCode.equals(sessionCode)) {
            msg = "手机验证码错误";
            jsonObject.put("msg", msg);
            this.response(response, jsonObject.toString());
            return;
        }
        partyFacade.addParty(partyView);
        jsonObject.put("msg", msg);
        this.response(response, jsonObject.toString());
    }

    @RequestMapping("/edit/modifyPassword.action")
    public void modifyPassword(String password1, String password2,String password,HttpServletRequest request, HttpServletResponse response) throws Exception {
        String msg = "ok";
        JSONObject jsonObject = new JSONObject();
        User user = getCurrentUser();
        String passwordOld = new Md5PasswordEncoder().encodePassword(password, user.getLoginName());
            if (!user.getPassword().equals(passwordOld)) {
                msg = "原密码不正确";
                jsonObject.put("msg", msg);
                this.response(response, jsonObject.toString());
                return;
            }
        try {
            if(password1!=null && !password1.equals(password2)){
                msg ="两次输入不一致";
            }else{
                user.setPassword(new Md5PasswordEncoder().encodePassword(password1, user.getLoginName()));
                partyFacade.modifyPassword(user);
            }
        } catch (Exception e) {
            msg = "保存出错，请重试或者联系管理员！";
            e.printStackTrace();
        }
        jsonObject.put("msg",msg);
        this.response(response, jsonObject.toString());
    }

    /**
     * 管理平台更改密码
     * @param password1
     * @param password2
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/edit/modifyAdminPassword.action")
    public void modifyAdminPassword(String password1, String password2,HttpServletRequest request, HttpServletResponse response) throws Exception {
        String msg = "ok";
        JSONObject jsonObject = new JSONObject();
        User user = getCurrentUser();
        try {
            if(password1!=null && !password1.equals(password2)){
                msg ="两次输入不一致";
            }else{
                user.setPassword(new Md5PasswordEncoder().encodePassword(password1, user.getLoginName()));
                partyFacade.modifyPassword(user);
            }
        } catch (Exception e) {
            msg = "保存出错，请重试或者联系管理员！";
            e.printStackTrace();
        }
        jsonObject.put("msg",msg);
        this.response(response, jsonObject.toString());
    }
    @RequestMapping("/edit/modifyPasswordByPhone.action")
    public void modifyPasswordByPhone(String password1, String password2,String phoneCode,HttpServletRequest request, HttpServletResponse response) throws Exception {
        String msg = "ok";
        JSONObject jsonObject = new JSONObject();
        String sessionCode = (String) request.getSession().getAttribute(MobilePhoneUtils.PHONE_VALIDATE_SESSION);
        if (phoneCode == null || "".equals(phoneCode)) {
            msg = "手机验证码不能为空";
            jsonObject.put("msg", msg);
            this.response(response, jsonObject.toString());
            return;
        }
        if (!phoneCode.equals(sessionCode)) {
            msg = "手机验证码错误";
            jsonObject.put("msg", msg);
            this.response(response, jsonObject.toString());
            return;
        }
        if(password1!=null && !password1.equals(password2)){
            msg ="两次输入不一致";
        }else{
            User user = getCurrentUser();
            user.setPassword(new Md5PasswordEncoder().encodePassword(password1, user.getLoginName()));
            partyFacade.modifyPassword(user);
        }
        jsonObject.put("msg",msg);
        this.response(response, jsonObject.toString());
    }

    @RequestMapping("/add/uploadImgUrl.action")
    public void uploadImgUrl(String imgUrl, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject jsonObject = new JSONObject();
        String msg = "ok";
        User user = getCurrentUser();
        Long partyId = user.getPartyId();
        Party party = new Party();
        party.setId(partyId);
        party.setImageUrl(imgUrl);
        partyFacade.updateParty(party);
        jsonObject.put("msg", msg);
        this.response(response, jsonObject.toString());
    }

    @RequestMapping("/add/toUploadImage.action")
    public String toUploadImage(Model uiModel) throws Exception {
        User user = getCurrentUser();
        Party party = partyFacade.getPartyById(user.getPartyId());
        uiModel.addAttribute("user", user);
        uiModel.addAttribute("party", party);
        return "/user/upload-image.html";
    }

    @RequestMapping("/list/isHasAuthor.action")
    public void isHasAuthor(Model uiModel, HttpServletResponse response) throws Exception {
        //获取用户和主体信息
        User user = getCurrentUser();
        Integer verifyFlag = user.getVerifyFlag();
        //增加判断条件，当用户为后台管理员时。设置为审核通过
        if((verifyFlag!=null&&verifyFlag.intValue()==1)||user.getUserType()==1){
            verifyFlag = 1;
        }else{
            verifyFlag = 0;
        }
        Integer status = 0;
        if(verifyFlag==0){
            Long partyId = user.getPartyId();
            Party party = partyFacade.getPartyById(partyId);
            status = party.getStatus();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", status);
        jsonObject.put("verifyFlag", verifyFlag);
        this.response(response, jsonObject.toString());
    }


    public static void main(String[] args) {
        String name = IPartyType.COMPANY_CLASS_COMPANY.get("2");
        System.out.println(name);
    }

}
