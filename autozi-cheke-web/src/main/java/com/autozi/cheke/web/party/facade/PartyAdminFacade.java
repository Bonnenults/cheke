package com.autozi.cheke.web.party.facade;

import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.party.entity.PartyLog;
import com.autozi.cheke.party.type.IPartyType;
import com.autozi.cheke.service.party.IPartyService;
import com.autozi.cheke.service.user.IUserService;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.web.party.vo.PartyView;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.o2o.PageUtil;
import com.autozi.passcar.cache.memcached.MemcacheKeyConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by lbm on 2017/12/5.
 */
@Component
public class PartyAdminFacade {

    @Autowired
    private IPartyService partyService;
    @Autowired
    private IUserService userService;

    public Page<PartyView> findForPartyPage(Page<Map<String,Object>> page, Map<String, Object> filters) {
        page = partyService.listParty(page, filters);
        Page<PartyView> viewPage = new Page<>();
        List<PartyView> viewList = new ArrayList<>();
        for (Map obj : page.getResult()) {
            if (obj == null || obj.get("id") == null) {
                continue;
            }
            PartyView view = new PartyView();
            view.setId(Long.parseLong(obj.get("id").toString()));
            view.setName(obj.get("name").toString());
            view.setLoginName(obj.get("loginName").toString());
            view.setPartyClass((Integer)obj.get("partyClass"));
            view.setCreateTime((Date) obj.get("createTime"));
            Object oStatus = obj.get("status")==null?"1":obj.get("status");
            view.setStatus((Integer)oStatus);
            Object userStatus = obj.get("userStatus")==null?"1":obj.get("userStatus");
            view.setUserStatus((Integer)userStatus);
            viewList.add(view);
        }
        PageUtil.convertPage(page, viewPage, viewList);
        return viewPage;
    }

    public Party getPartyById(Long partyId){
        Party party = partyService.getPartyById(partyId);
        return party;
    }

    public PartyLog getPartyLogByPartyId(Long partyId){
        PartyLog partyLog = partyService.getPartyLogByPartyId(partyId);
        return partyLog;
    }

    public void passVerify(Long id){
        Party party = partyService.getPartyById(id);
        party.setStatus(IPartyType.STATUS_NORMAL);
        partyService.update(party);
        //设置user审核标志
        User user = userService.getPartyAdminUser(id);
        if(user!=null&&(user.getVerifyFlag()==null || user.getVerifyFlag()!=1)){
            user.setVerifyFlag(1);
            userService.update(user);
        }
    }

    public void refuseVerify(PartyView view){
        PartyLog partyLog = new PartyLog();
        partyLog.setPartyId(view.getPartyId());
        partyLog.setRefuseReason(view.getRefuseReason());
        partyService.refuseVerify(partyLog);
    }

    public void lockOrUnLockCheke(Long partyId, Integer status) {
        User user = userService.getPartyAdminUser(partyId);
        User temp = new User();
        temp.setId(user.getId());
        temp.setUserStatus(status);
        userService.update(temp);
    }

    public void updatePassword(Long partyId, String password) {
        User user = userService.getPartyAdminUser(partyId);
        User temp = new User();
        temp.setId(user.getId());
        String newPwd = new Md5PasswordEncoder().encodePassword(password, user.getLoginName());
        temp.setPassword(newPwd);
        userService.update(temp);
    }
}
