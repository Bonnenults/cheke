package com.autozi.cheke.web.medal.controller;


import com.autozi.cheke.user.entity.Medal;
import com.autozi.cheke.user.entity.MedalRule;
import com.autozi.cheke.user.type.MedalStatus;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.medal.facade.MedalAdminFacade;
import com.autozi.cheke.web.medal.vo.MedalRuleVo;
import com.autozi.cheke.web.medal.vo.MedalVo;
import com.autozi.common.core.page.Page;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *
 *@author mingxin li
 *@data 2018/7/2
 *
 */
@Controller
@RequestMapping("/medal/admin/")
public class MedalAdminController extends BaseController{


    @Autowired
    private MedalAdminFacade medalAdminFacade;


    @RequestMapping("/list/listMedal.action")
    public String listMedal(Model uiModel,HttpServletRequest request,String ajax) throws Exception{
        Map<String,Object> filter = buildFilter(request,uiModel);
        Page<Medal> page = buildPage(request);

        //第一次进入默认是待发布
        if(filter.get("status") == null) {
            filter.put("status", MedalStatus.WAITING.getType());
            uiModel.addAttribute("status",filter.get("status") == null ? "0" : filter.get("status").toString());
        }else if(Integer.parseInt(filter.get("status").toString()) == -100){
            uiModel.addAttribute("status",filter.get("status") == null ? "-100" : filter.get("status").toString());
            filter.remove("status");
        }else{
            uiModel.addAttribute("status",filter.get("status") == null ? "-100" : filter.get("status").toString());
        }


        Page<MedalVo> voPage = medalAdminFacade.findMedalPage(page,filter);

        this.pageInfoByMap(uiModel,voPage,filter);
        uiModel.addAttribute("page",voPage);

        if(ajax != null) {
            return "/admin/medal/listMedal_ajax";
        }
        return "/admin/medal/listMedal.html";
    }

    @RequestMapping("/list/listMedalRule.action")
    public String listMedalRule(Model uiModel,HttpServletRequest request,String ajax) throws Exception{
        Map<String,Object> filter = buildFilter(request,uiModel);
        Page<MedalRule> page = buildPage(request);

        //第一次进入默认是待发布
        if(filter.get("status") == null) {
            filter.put("status", MedalStatus.WAITING.getType());
            uiModel.addAttribute("status",filter.get("status") == null ? "0" : filter.get("status").toString());
        }else if(Integer.parseInt(filter.get("status").toString()) == -100){
            uiModel.addAttribute("status",filter.get("status") == null ? "-100" : filter.get("status").toString());
            filter.remove("status");
        }else{
            uiModel.addAttribute("status",filter.get("status") == null ? "-100" : filter.get("status").toString());
        }


        Page<MedalRuleVo> voPage = medalAdminFacade.findMedalRulePage(page,filter);

        this.pageInfoByMap(uiModel,voPage,filter);
        uiModel.addAttribute("page",voPage);

        if(ajax != null) {
            return "/admin/medal/listMedalRule_ajax";
        }
        return "/admin/medal/listMedalRule.html";
    }

    @RequestMapping("/create/createMedal.action")
    public String createMedal(@RequestParam(required = false) Long id, Model uiModel) throws Exception{
        Medal medal = new Medal();
        if(id != null) {
            medal = medalAdminFacade.getMedalById(id);
        }
        uiModel.addAttribute("vo",medal);

        return "/admin/medal/createOrUpdateMedal.html";
    }


    /**
     * 新增或修改课程
     * @param medal
     * @param response
     */
    @RequestMapping("/create/createOrUpdateMedal.action")
    public void createOrUpdateMedal(Medal medal, HttpServletResponse response) throws Exception{
        //新增
        if(medal.getId() == null) {
            medal.setCreateTime(new Date());
            medal.setCreateUserId(getCurrentUserId());
            medal.setStatus(0);
            medal.setObtainNum(0);
            medal.setAdornNum(0);
            int num = medalAdminFacade.addMedal(medal);
            if(num > 0) {
                responseJson(response,"ok","新增成功");
            }else {
                responseJson(response,"err","新增失败");
            }

        }else {
            //修改
            medal.setUpdateTime(new Date());
            int num = medalAdminFacade.updateMedal(medal);
            if(num > 0) {
                responseJson(response,"ok","修改成功");
            }else {
                responseJson(response,"err","修改失败");
            }

        }
    }


    /**
     * 下线操作
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping("/update/offlineMedal.action")
    public void offlineMedal(Long id,HttpServletResponse response) throws Exception{
        int num = medalAdminFacade.offLineMedal(id);
        if(num > 0) {
            responseJson(response,"ok","下线成功");
        }else {
            responseJson(response,"err","下线失败");
        }
    }

    /**
     * 上线操作
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping("/update/onlineMedal.action")
    public void onlineMedal(Long id,HttpServletResponse response) throws Exception{
        int num = medalAdminFacade.onlineMedal(id);
        if(num > 0) {
            responseJson(response,"ok","下线成功");
        }else {
            responseJson(response,"err","下线失败");
        }
    }

    @RequestMapping("/delete/deleteMedal.action")
    public void deleteMedal(Long id,HttpServletResponse response) {

        if(medalAdminFacade.checkedIsRef(id)) {
            responseJson(response,"err","不能删除有人获得的勋章");
            return;
        }

        int num = medalAdminFacade.deleteMedal(id);
        if(num > 0) {
            responseJson(response,"ok","删除成功");
        }else {
            responseJson(response,"err","删除失败");
        }
    }

    /**
     * 规则
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping("/list/showMedalRule.action")
    public void showMedalRule(Long id,HttpServletResponse response) throws Exception{
        Map<String,Object> filter = new HashMap<>();
        filter.put("medalId",id);
        MedalRule medalRule = medalAdminFacade.getMedalRuleByFilter(filter);

        JSONObject json = new JSONObject();
        if(medalRule != null){
            json.put("id", medalRule.getId());
            json.put("ruleKey", medalRule.getRuleKey());
            json.put("ruleValue", medalRule.getRuleValue());
            json.put("status", medalRule.getStatus());
            json.put("intro", medalRule.getIntro());
            json.put("medalId", medalRule.getMedalId());
        }

        response(response,"ok","OK",json.toString());

    }

    /**
     * 新增或修改勋章规则
     * @param request
     * @param response
     */
    @RequestMapping("/create/createOrUpdateMedalRule.action")
    public void createOrUpdateMedalRule(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String id = request.getParameter("id");
        String medalId = request.getParameter("medalId");
        String ruleKey = request.getParameter("ruleKey");
        String ruleValue = request.getParameter("ruleValue");
        String intro = request.getParameter("intro");
        String status = request.getParameter("status")==null?"0" :request.getParameter("status");
        if(StringUtils.isBlank(ruleKey)
                || StringUtils.isBlank(ruleValue)
                || StringUtils.isBlank(medalId)){
            responseJson(response,"err","必要信息为空");
            return;
        }

        //新增
        if(id == null || "".equals(id)) {

            MedalRule medalRule = new MedalRule();
            medalRule.setRuleKey(ruleKey);
            medalRule.setRuleValue(ruleValue);
            medalRule.setStatus(Integer.valueOf(status));
            medalRule.setMedalId(Long.valueOf(medalId));
            medalRule.setCreateTime(new Date());
            medalRule.setCreateUserId(getCurrentUserId());
            medalRule.setIntro(intro);

            int num = medalAdminFacade.addMedalRule(medalRule);
            if(num > 0) {
                responseJson(response,"ok","新增成功");
            }else {
                responseJson(response,"err","新增失败");
            }

        }else {
            MedalRule medalRule = medalAdminFacade.getMedalRuleById(Long.valueOf(id));
            //修改
            medalRule.setRuleKey(ruleKey);
            medalRule.setRuleValue(ruleValue);
            medalRule.setStatus(Integer.valueOf(status));
            medalRule.setIntro(intro);
            medalRule.setUpdateTime(new Date());
            medalRule.setUpdateUserId(getCurrentUserId());
            int num = medalAdminFacade.updateMedalRule(medalRule);
            if(num > 0) {
                responseJson(response,"ok","修改成功");
            }else {
                responseJson(response,"err","修改失败");
            }

        }
    }

}
