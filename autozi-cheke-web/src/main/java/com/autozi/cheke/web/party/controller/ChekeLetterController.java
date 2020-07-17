package com.autozi.cheke.web.party.controller;

import com.autozi.cheke.party.entity.ChekeLetter;
import com.autozi.cheke.party.entity.ChekeLetterRelation;
import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.party.type.IPartyType;
import com.autozi.cheke.user.entity.PersonalParty;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.party.facade.ChekeLetterFacade;
import com.autozi.cheke.web.party.facade.PartyFacade;
import com.autozi.cheke.web.party.vo.ChekeLetterRelationView;
import com.autozi.cheke.web.party.vo.ChekeLetterView;
import com.autozi.cheke.web.party.vo.PartyView;
import com.autozi.cheke.web.user.facade.UserFacade;
import com.autozi.cheke.web.user.vo.UserView;
import com.autozi.common.core.page.Page;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by lbm on 2017/12/15.
 */
@Controller
@RequestMapping("/letter")
public class ChekeLetterController extends BaseController {
    @Autowired
    private ChekeLetterFacade chekeLetterFacade;
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private PartyFacade partyFacade;

    @RequestMapping("/cheke/list/listLetter.action")
    public String listLetter(HttpServletRequest request, Model uiModel, String ajax) throws UnsupportedEncodingException {
        Page<ChekeLetterRelation> page = buildPage(request);
        HashMap<String, Object> filters = buildFilter(request, uiModel);
        User user = getCurrentUser();
        Long partyId = user.getPartyId();
        filters.put("partyId", partyId);
        Page<ChekeLetterRelationView> viewPage = chekeLetterFacade.findForPage(page,filters);
        uiModel.addAttribute("page", viewPage);
        this.pageInfoByMap(uiModel, viewPage, filters);
        if (ajax != null) {
            return "/message/letter/listLetter_ajax";
        }
        return "/message/letter/listLetter.html";
    }

    @RequestMapping("/cheke/list/getListByRelationId.action")
    public void getListByRelationId(Long relationId, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        List<ChekeLetterView> chekeLetterList = chekeLetterFacade.getListByRelationId(relationId);
        ChekeLetterRelation relation = chekeLetterFacade.getRelationById(relationId);
        UserView user =userFacade.getTuikeUser(relation.getUserId());
        Party party =partyFacade.getPartyById(relation.getPartyId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", chekeLetterList);
        jsonObject.put("user", user);
        jsonObject.put("party", party);
        this.response(response, jsonObject.toString());
    }

    @RequestMapping("/cheke/list/checkNewLetter.action")
    public void checkNewLetter(Long relationId, Integer ran,HttpServletRequest request, HttpServletResponse response) {
        ChekeLetterRelation relation = chekeLetterFacade.getRelationById(relationId);
        JSONObject jsonObject = new JSONObject();
        ChekeLetter letter = new ChekeLetter();
        letter.setRelationId(relationId);
        letter.setToUserId(relation.getPartyId());
        letter.setCreateTime(new Date());
        List<ChekeLetterView> chekeLetterList = new ArrayList<>();
        String flag = "no";
        int i=0;
        while (true){
            try {
                Thread.currentThread().sleep(2000);
                chekeLetterList = chekeLetterFacade.checkNewLetter(letter);
                if(chekeLetterList!=null && chekeLetterList.size()>0){
                    flag = "ok";
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(flag.equals("ok")||i==10){
                break;
            }
            i++;
        }

        UserView user =userFacade.getTuikeUser(relation.getUserId());
        jsonObject.put("flag", flag);
        jsonObject.put("ran", ran);
        jsonObject.put("list", chekeLetterList);
        jsonObject.put("user", user);
        this.response(response, jsonObject.toString());
    }

    @RequestMapping("/cheke/return/returnLetter.action")
    public void returnLetter(ChekeLetter chekeLetter, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String msg = "ok";
        chekeLetterFacade.returnUser(chekeLetter);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", msg);
        this.response(response, jsonObject.toString());
    }

    /**
     * 管理端查看车客私信列表
     * @param request
     * @param uiModel
     * @param ajax
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/admin/list/listLetterAdmin.action")
    public String listLetterAdmin(HttpServletRequest request, Model uiModel, String ajax) throws UnsupportedEncodingException {
        Page<Map<String,Object>> page = buildPage(request);
        HashMap<String, Object> filters = buildFilter(request, uiModel);

        Page<PartyView> viewPage = chekeLetterFacade.listLetterAdmin(page,filters);
        uiModel.addAttribute("page", viewPage);
        this.pageInfoByMap(uiModel, viewPage, filters);
        if (ajax != null) {
            return "/admin/message/letter/listLetterAdmin_ajax";
        }
        return "/admin/message/letter/listLetterAdmin.html";
    }

    /**
     * 管理端 车客的私信列表
     * @param request
     * @param uiModel
     * @param ajax
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/admin/list/listPartyLetter.action")
    public String listPartyLetter(HttpServletRequest request, Model uiModel, String ajax) throws UnsupportedEncodingException {
        Page<ChekeLetterRelation> page = buildPage(request);
        HashMap<String, Object> filters = buildFilter(request, uiModel);
        Long partyId = Long.parseLong(request.getParameter("partyId"));

        Page<ChekeLetterRelationView> viewPage = chekeLetterFacade.findForPage(page,filters);
        uiModel.addAttribute("page", viewPage);
        uiModel.addAttribute("partyId", partyId);
        this.pageInfoByMap(uiModel, viewPage, filters);
        if (ajax != null) {
            return "/admin/message/letter/listPartyLetter_ajax";
        }
        return "/admin/message/letter/listPartyLetter.html";
    }

    @RequestMapping("/cheke/list/letterCount.action")
    public void letterCount(HttpServletRequest request,HttpServletResponse response) {
        User user = getCurrentUser();
        Long partyId = user.getPartyId();
        int count = chekeLetterFacade.getLetterCount(partyId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count", count);
        this.response(response, jsonObject.toString());
    }

}
