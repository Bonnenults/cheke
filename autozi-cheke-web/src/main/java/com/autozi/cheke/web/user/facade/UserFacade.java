package com.autozi.cheke.web.user.facade;

import com.autozi.cheke.basic.entity.Area;
import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.party.type.IPartyType;
import com.autozi.cheke.service.basic.IAreaService;
import com.autozi.cheke.service.party.IPartyService;
import com.autozi.cheke.service.settle.IAccountService;
import com.autozi.cheke.service.user.IRoleService;
import com.autozi.cheke.service.user.IUserService;
import com.autozi.cheke.settle.type.IAccountConstant;
import com.autozi.cheke.user.entity.PersonalParty;
import com.autozi.cheke.user.entity.Role;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.user.type.IUserType;
import com.autozi.cheke.web.user.vo.UserView;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.o2o.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lbm on 2017/11/28.
 */
@Component
public class UserFacade {
    @Autowired
    private IUserService userService;
    @Autowired
    private IPartyService partyService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IAreaService areaService;

    public void register(UserView userView){

        Party party = new Party();
        party.setName(userView.getPartyName());
        party.setStatus(IPartyType.STATUS_REGISTER);
        party.setPartyType(IPartyType.PARTY_TYPE_CHEKE);

        //添加用户中的partyId
        User user = new User();
        BeanUtils.copyProperties(userView,user);
        user.setAdmin(1);
        user.setUserStatus(IUserType.STATUS_NORMAL);
        user.setUserType(IUserType.USER_TYPE_CHEKE);//车客营销类型用户
        user.setPassword(new Md5PasswordEncoder().encodePassword(userView.getPassword(), userView.getLoginName()));
        userService.registerCheke(user,party);
    }

    public boolean checkLoginName(UserView userView){
        return userService.checkLoginName(userView.getLoginName());
    }

    public Page<UserView> findForUserPage(Page<User> page, Map<String, Object> filters) {
        page = userService.findPageForMap(page, filters);
        Page<UserView> viewPage = new Page<UserView>();
        List<UserView> viewList = new ArrayList<UserView>();
        for (User obj : page.getResult()) {
            if (obj == null || obj.getId() == null) {
                continue;
            }
            UserView view = new UserView();
            BeanUtils.copyProperties(obj,view);
            view.setUserStatusCN(IUserType.userStatusMap.get(obj.getUserStatus().toString()));
            Role role = userService.getRoleByUserId(obj.getId());
            String roleName = "";
            if(role!=null){
                roleName = role.getName();
            }
            view.setRoleName(roleName);
            viewList.add(view);
        }
        PageUtil.convertPage(page, viewPage, viewList);
        return viewPage;
    }

    public User getUserById(long userId){
        User user = userService.getUserById(userId);
        return user;
    }

    /**
     * 通过用户ID查询 role
     * @param userId
     * @return
     */
    public Role getUserRoleByUserId(long userId) {
        Role role = userService.getRoleByUserId(userId);
        return role;
    }

    public List<Role> findRoleByPartyId(long partyId) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("partyId", partyId);
        filters.put("status", Role.STATUS_NORMAL);
        List<Role> roleList = roleService.listRole(filters);
        return roleList;
    }

    public void lockOrUnLockUser(Long userId, Integer status,Long partyId) {
        userService.lockOrUnLockUser(userId,status,partyId);
    }

    public void savePassword(User user,Long userId, String password1, String password2) {
        userService.savePassword(user,userId,password1,password2);
    }

    public boolean checkLoginNameRepeat(String loginName, Long userId) {
        return userService.checkLoginNameRepeat(loginName,userId);
    }

    public void saveUser(User currentUser, Long userId, String password1, String password2, String loginName, String name, String phone, String email, Long roleId) {
        User newUser = new User();
        newUser.setId(userId);
        newUser.setPassword(password1);
        newUser.setLoginName(loginName);
        newUser.setName(name);
        newUser.setPhone(phone);
        newUser.setEmail(email);
        userService.saveOrUpdateUser(currentUser,newUser,roleId);
    }

    public Page<UserView> listTuikeUser(Page<User> page, Map<String, Object> filters) {
        page = userService.findPageForMap(page, filters);
        Page<UserView> viewPage = new Page<UserView>();
        List<UserView> viewList = new ArrayList<UserView>();
        for (User obj : page.getResult()) {
            if (obj == null || obj.getId() == null) {
                continue;
            }
            UserView view = new UserView();
            BeanUtils.copyProperties(obj,view);
            if(view.getPartyId()!=null){
                PersonalParty party = userService.getPersonalPartyById(view.getPartyId());
                if(party==null){
                    continue;
                }
                convertToView(party,view);
            }
            //获取推客用户总佣金
            Long userId = obj.getId();
            try{
                Double money = accountService.getTotalTaskMoney(userId, IAccountConstant.AccountLogSourceType.TYPE_CK_ORDER_MONEY);
                Double money1 = accountService.getTotalTaskMoney(userId, IAccountConstant.AccountLogSourceType.TYPE_TK_SHARE_MONEY);
                Double total = money+money1;
                if(total!=0){
                    DecimalFormat df = new DecimalFormat("#.00");
                    view.setMoney(df.format(total));
                }else{
                    view.setMoney("0.00");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            int total = 0;
            try{
                if(view.getInviteCodeMine()!=null&&!"".equals(view.getInviteCodeMine())){
                    total = userService.getUnderTK(view.getInviteCodeMine());
                }
                view.setUserCount(String.valueOf(total));
            }catch (Exception e){
                e.printStackTrace();
            }
            viewList.add(view);
        }
        PageUtil.convertPage(page, viewPage, viewList);
        return viewPage;
    }

    private void convertToView(PersonalParty party,UserView view){
        view.setAddress(party.getAddress());
        view.setInviteCode(party.getInviteCode());
        view.setInviteCodeMine(party.getInviteCodeMine());
        view.setCompanyName(party.getCompanyName());
        view.setImageUrl(party.getImageUrl());
        view.setJobName(party.getJobName());
        view.setSourceType(party.getSourceType());
        view.setIdCardStatus(party.getIdCardStatus());
        view.setIdentifyImgA(party.getIdentifyImgA());
        view.setIdentifyImgB(party.getIdentifyImgB());
        view.setDescription(party.getDescription());
        view.setRefuseReason(party.getRefuseReason());
    }

    public UserView getTuikeUser(Long id){
        UserView view = new UserView();
        User user = userService.getUserById(id);
        Long partyId = user.getPartyId();
        BeanUtils.copyProperties(user,view);
        if(partyId!=null){
            PersonalParty party = userService.getPersonalPartyById(partyId);
            String areaName = "";
            if(party.getAddress()!=null){
                String areaCode = party.getAddress();
                if(areaCode!=null){
                    Long areaId = Long.parseLong(areaCode);
                    String areaNameOne = "";//地区一级名称
                    String areaNameTwo = "";//地区二级名称
                    String areaNameThree = "";//地区三级名称
                    if(areaId!=null && areaId !=0){
                        Area area = areaService.getById(areaId);//获取地区名称
                        areaNameThree = area.getName();
                        Long areaIdTwo = area.getParentId();
                        Area areaTwo = areaService.getById(areaIdTwo);
                        areaNameTwo = areaTwo.getName();
                        Area areaOne = areaService.getById(areaTwo.getParentId());
                        areaNameOne = areaOne.getName();
                    }
                    areaName=areaNameOne+" "+areaNameTwo+" "+areaNameThree;
                }
            }
            convertToView(party,view);
            view.setAddress(areaName);
        }
        return view;
    }

    public void passVerify(Long userId){
        User user = userService.getUserById(userId);
        //设置user审核标志
        if(user.getVerifyFlag()==null || user.getVerifyFlag()!=1){
            user.setVerifyFlag(1);
            userService.update(user);
        }
        Long partyId = user.getPartyId();
        PersonalParty party = userService.getPersonalPartyById(partyId);
        party.setIdCardStatus(IUserType.TUIKE_STATUS_NORMAL);
        userService.updatePersonalParty(party);
    }

    public void refuseVerify(Long userId,String refuseReason){
        User user = userService.getUserById(userId);
        Long partyId = user.getPartyId();
        PersonalParty party = userService.getPersonalPartyById(partyId);
        party.setIdCardStatus(IUserType.TUIKE_STATUS_REFUSE);
        party.setRefuseReason(refuseReason);
        userService.updatePersonalParty(party);
    }

    public void lockOrUnLockTuikeUser(Long userId, Integer status) {
        User user = new User();
        user.setId(userId);
        user.setUserStatus(status);
        userService.update(user);
    }

    public static void main(String[] args) {
        Double money = -10d;
        Double money1 = 0d;
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(df.format(money+money1));
    }


}
