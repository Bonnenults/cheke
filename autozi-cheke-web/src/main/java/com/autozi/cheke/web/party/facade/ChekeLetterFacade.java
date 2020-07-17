package com.autozi.cheke.web.party.facade;

import com.autozi.cheke.party.entity.ChekeLetter;
import com.autozi.cheke.party.entity.ChekeLetterRelation;
import com.autozi.cheke.service.party.IChekeLetterService;
import com.autozi.cheke.service.party.IPartyService;
import com.autozi.cheke.service.user.IUserService;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.web.party.vo.ChekeLetterRelationView;
import com.autozi.cheke.web.party.vo.ChekeLetterView;
import com.autozi.cheke.web.party.vo.PartyView;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.o2o.PageUtil;
import com.autozi.common.utils.util2.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lbm on 2017/12/15.
 */
@Component
public class ChekeLetterFacade{
    @Autowired
    private IChekeLetterService chekeLetterService;
    @Autowired
    private IPartyService partyService;
    @Autowired
    private IUserService userService;

    public Page<ChekeLetterRelationView> findForPage(Page<ChekeLetterRelation> page, Map<String, Object> filters) {
//        filters=DateUtils.String2Date(filters);
        page = chekeLetterService.findPageForMap(page, filters);
        Page<ChekeLetterRelationView> viewPage = new Page<>();
        List<ChekeLetterRelationView> viewList = new ArrayList<>();
        for (ChekeLetterRelation obj : page.getResult()) {
            if (obj == null) {
                continue;
            }
            ChekeLetterRelationView view = new ChekeLetterRelationView();
            BeanUtils.copyProperties(obj,view);
            Long userId = obj.getUserId();
            User user = userService.getUserById(userId);
            String loginName = "";
            if(user!=null){
                loginName = user.getLoginName()==null?"":user.getLoginName();
            }
            view.setUserName(loginName);
            viewList.add(view);
        }
        PageUtil.convertPage(page, viewPage, viewList);
        return viewPage;
    }

    public List<ChekeLetterView> getListByRelationId(Long letterId) {
        List<ChekeLetter> list = chekeLetterService.getListByRelationId(letterId);
        List<ChekeLetterView> viewList = new ArrayList<>();
        for(ChekeLetter obj:list){
            if (obj == null) {
                continue;
            }
            ChekeLetterView view = new ChekeLetterView();
            BeanUtils.copyProperties(obj,view);
            view.setCreateTime(DateUtils.formatDate(obj.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
            viewList.add(view);
        }
        return viewList;
    }

    public List<ChekeLetterView> checkNewLetter(ChekeLetter chekeLetter) {
        List<ChekeLetter> list = chekeLetterService.checkNewLetter(chekeLetter);
        List<ChekeLetterView> viewList = new ArrayList<>();
        for(ChekeLetter obj:list){
            if (obj == null) {
                continue;
            }
            ChekeLetterView view = new ChekeLetterView();
            BeanUtils.copyProperties(obj,view);
            view.setCreateTime(DateUtils.formatDate(obj.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
            viewList.add(view);
        }
        return viewList;
    }

    public void returnUser(ChekeLetter chekeLetter){
        chekeLetterService.toUser(chekeLetter);
    }

    public ChekeLetterRelation getRelationById(Long relationId){
        return chekeLetterService.getChekeLetterRelationById(relationId);
    }

    //************************管理平台端接口***********************
    public Page<PartyView> listLetterAdmin(Page<Map<String,Object>> page, Map<String, Object> filters) {
        page = chekeLetterService.findPartyLetterList(page, filters);
        Page<PartyView> viewPage = new Page<>();
        List<PartyView> viewList = new ArrayList<>();
        for (Map<String,Object> obj : page.getResult()) {
            if (obj == null) {
                continue;
            }
            PartyView view = new PartyView();
            view.setId(Long.parseLong(obj.get("party_id").toString()));
            view.setName(obj.get("party_name").toString());
            Object object = obj.get("party_class");
            if(object!=null){
                view.setPartyClass(Integer.parseInt(object.toString()));
            }
            view.setLetterCount(obj.get("letterCount").toString());
            viewList.add(view);
        }
        PageUtil.convertPage(page, viewPage, viewList);
        return viewPage;
    }

    public int getLetterCount(Long partyId) {
        return chekeLetterService.getLetterCount(partyId,1);
    }
}
