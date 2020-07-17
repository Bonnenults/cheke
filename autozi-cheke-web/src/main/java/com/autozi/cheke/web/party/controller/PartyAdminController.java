package com.autozi.cheke.web.party.controller;

import com.autozi.cheke.basic.entity.Area;
import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.party.entity.PartyLog;
import com.autozi.cheke.party.type.IPartyType;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.party.facade.PartyAdminFacade;
import com.autozi.cheke.web.basic.facade.AreaFacade;
import com.autozi.cheke.web.party.vo.PartyView;
import com.autozi.cheke.web.user.facade.UserFacade;
import com.autozi.common.core.page.Page;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lbm on 2017/12/5.
 */
@Controller
@RequestMapping("/admin/party/")
public class PartyAdminController extends BaseController {
    @Autowired
    private PartyAdminFacade partyFacade;
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private AreaFacade areaFacade;

    @RequestMapping("/list/listParty.action")
    public String listParty(HttpServletRequest request, Model uiModel, String ajax) throws UnsupportedEncodingException {
        Page<Map<String,Object>> page = buildPage(request);
        HashMap<String, Object> filters = buildFilter(request, uiModel);
        filters.put("userType", IPartyType.PARTY_TYPE_CHEKE);
        filters.put("admin",1);
        String status = request.getParameter("status");
        if(status==null){
            status = "2";//默认到新增审核
        }
        filters.put("status",status);
        Page<PartyView> viewPage = partyFacade.findForPartyPage(page,filters);
        uiModel.addAttribute("page", viewPage);
        this.pageInfoByMap(uiModel, viewPage, filters);
        if (ajax != null) {
            return "/admin/party/listParty_ajax";
        }
        return "/admin/party/listParty.html";
    }

    @RequestMapping("/list/audit.action")
    public String audit(Model uiModel,Long id) throws UnsupportedEncodingException {
        Party party = partyFacade.getPartyById(id);
        PartyLog partyLog = partyFacade.getPartyLogByPartyId(id);
        uiModel.addAttribute("partyLog", party);
        String reason = "";
        if(partyLog!=null && partyLog.getRefuseReason()!=null){
            reason = partyLog.getRefuseReason();
        }
        uiModel.addAttribute("refuseReason", reason);
        Integer partyClass = party.getPartyClass();
        Integer companyType = party.getCompanyType();

        //获取地区信息、
        Long areaId = party.getAreaId();
        String areaNameOne = "";//地区一级名称
        String areaNameTwo = "";//地区二级名称
        String areaName = "";//地区三级名称
        if(areaId!=null && areaId !=0){
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

        String url= "";
        String companyTypeName = "";
        if (partyClass == IPartyType.PARTY_CLASS_GOVERNMENT) {
            url = "/admin/party/organ.html";
            companyTypeName = IPartyType.COMPANY_CLASS_ORGAN.get(String.valueOf(companyType));
        } else if (partyClass == IPartyType.PARTY_CLASS_MEDIA) {
            url = "/admin/party/media.html";
            companyTypeName = IPartyType.COMPANY_CLASS_MEDIA.get(String.valueOf(companyType));
        } else if (partyClass == IPartyType.PARTY_CLASS_PERSON) {
            url = "/admin/party/person.html";
            companyTypeName=IPartyType.COMPANY_CLASS_PERSON.get(String.valueOf(companyType));
            Integer mediaType = party.getMediaType();
            uiModel.addAttribute("mediaTypeName",IPartyType.personType.get(String.valueOf(mediaType)));
        }else{
            url = "/admin/party/company.html";
            companyTypeName=IPartyType.COMPANY_CLASS_COMPANY.get(String.valueOf(companyType));
        }
        uiModel.addAttribute("companyTypeName",companyTypeName);

        return url;
    }

    /**
     * 新增审核通过
     * @param id
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/list/passVerify.action")
    public void passVerify(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String msg="ok";
        try{
            partyFacade.passVerify(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg",msg);
        this.response(response,jsonObject.toString());
    }

    /**
     *
     * @param view
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/list/refuseVerify.action")
    public void refuseVerify(PartyView view, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String msg="ok";
        try{
            partyFacade.refuseVerify(view);
        }catch (Exception e){
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg",msg);
        this.response(response,jsonObject.toString());
    }

    @RequestMapping("/list/lockOrUnLockCheke.action")
    public void lockOrUnLockCheke(Long partyId, Integer status, HttpServletResponse response){
        String msg = "锁定成功";
        if(status!=null&&status==1){
            msg = "解锁成功";
        }
        try {
            partyFacade.lockOrUnLockCheke(partyId,status);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "出现错误";
        } finally {
            this.response(response, msg);
        }
    }

    @RequestMapping("/list/updatePassword.action")
    public void updatePassword(Long partyId,String password1,String password2, HttpServletResponse response) throws Exception {
        String msg = "ok";
        try {
            partyFacade.updatePassword(partyId,password1);
        } catch (Exception e) {
            msg = "保存出错，请重试或者联系管理员！";
            e.printStackTrace();
        }
        this.response(response, msg);
    }
}
