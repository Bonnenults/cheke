package com.autozi.cheke.service.user;

import com.autozi.cheke.party.dao.PartyDao;
import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.service.mobile.MobilePhoneCodeServiceImpl;
import com.autozi.cheke.user.dao.*;
import com.autozi.cheke.user.entity.*;
import com.autozi.cheke.user.type.IUserType;
import com.autozi.common.core.exception.B2bException;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.util1.MobilePhoneUtils;

import com.autozi.common.utils.util2.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PersonalPartyDao personalPartyDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private MobilePhoneCodeServiceImpl mobilePhoneCodeService;
    @Autowired
    private PartyDao partyDao;
    @Autowired
    private UserLoginStatusDao userLoginStatusDao;


    @Override
    public User getUserById(long id) {
        return userDao.get(id);
    }

    @Override
    public User getUserByLoginName(String loginName) {
        return userDao.getUserByLoginName(loginName);
    }

    @Override
    public Page<User> findPageForMap(Page<User> page, Map<String, Object> filters) {
        return userDao.findPageForMap(page,filters);
    }
    @Override
    public List<User> listUsers(Map<String, Object> filters) {
        return userDao.findList(filters);
    }

    @Override
    public void save(User user) {
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        userDao.insert(user);
    }

    @Override
    public void registerCheke(User user,Party party) {
        party.setCreateTime(new Date());
        party.setUpdateTime(new Date());
        partyDao.insert(party);
        long partyId=party.getId();

        user.setPartyId(partyId);
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        userDao.insert(user);

        if(user.getUserType()== IUserType.USER_TYPE_CHEKE){
            saveUserRoleForCK(user.getId());
        }
    }

    @Override
    public void update(User user){
        user.setUpdateTime(new Date());
        userDao.update(user);
    }

    @Override
    public boolean checkLoginName(String loginName) {
        User user = userDao.getUserByLoginName(loginName);
        if(user==null){
            return false;
        }

        return true;
    }

    @Override
    public boolean checkMobileExists(User user) {
        Map<String,Object> filters = new HashMap<>();
        filters.put("phone",user.getPhone());
        List<User> list = userDao.findList(filters);
        //代表新增检测
        if(user.getId()==null){
            if(list!=null && list.size()>0){
                return true;
            }
        }else{
            Long userId = user.getId();
            for(User temp :list){
                if(temp.getId() != user.getId().longValue()){
                    return true;
                }
            }
        }
        return false;
    }

    private void saveUserRoleForCK(long userId){
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(2L);
        userRoleDao.insert(userRole);
    }

    @Override
    public Role getRoleByUserId(Long userId) {
        return roleService.findRolesByUserId(userId);
    }

	@Override
	public Long registerUser(String phone, String phonePwd,String inviteCode) {
		//同时更新手机验证码的状态
		Long userId = this.register(phone, phonePwd,inviteCode);
		mobilePhoneCodeService.checkCodeSuccess(phone);
		return userId;
	}

	private Long register(String phone, String phonePwd,String inviteCode) {
		if (!MobilePhoneUtils.checkPhone(phone)) {
            throw new B2bException("手机号码格式不正确！");
        }
        User dbUser = userDao.getUserByLoginName(phone);
        if (dbUser != null) {
            return dbUser.getId();
        }
        //插入用户party
        PersonalParty party = new PersonalParty();
        String inviteCodeMine = "";
        String ck = "CK";
        String year = DateUtils.formatDate(new Date(),"yyyy");
        String code = MobilePhoneUtils.createRandom(true, 6);
        inviteCodeMine = ck+year+code;
        boolean isExist = isExistInviteCodeMine(inviteCodeMine);
        while(isExist){
            inviteCodeMine =ck+year+MobilePhoneUtils.createRandom(true, 6);
            isExist = isExistInviteCodeMine(inviteCodeMine);
            if(!isExist){
                break;
            }
        }
        party.setInviteCodeMine(inviteCodeMine);
        if(inviteCode!=null){
            party.setInviteCode(inviteCode);
        }
        party.setSourceType(IUserType.TUIKE_SOURCE_TYPE_REGISTER);
        party.setCreateTime(new Date());
        party.setIdCardStatus(IUserType.TUIKE_STATUS_REGISTER);
        party.setUpdateTime(new Date());
        personalPartyDao.insert(party);

        User user = new User();
        user.setPhone(phone);
        String password = new Md5PasswordEncoder().encodePassword(phonePwd, phone);
        user.setPassword(password);
        user.setPhonePassword(password);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setUserType(IUserType.USER_TYPE_TUIKE);
        user.setPartyId(party.getId());
        user.setUserStatus(IUserType.STATUS_NORMAL);
        userDao.insert(user);
        return user.getId();
    }

    @Override
    public boolean isExistInviteCodeMine(String inviteCodeMine){
        Map<String, Object> filters = new HashMap<>();
        filters.put("inviteCodeMine",inviteCodeMine);
        List<PersonalParty> list = personalPartyDao.findListForMap(filters);
        if(list!=null && list.size()>0){
            return true;
        }
        return false;
    }

	@Override
	public User findUserByLoginName(String phone) {
		return userDao.findUserByLoginName(phone);
	}

	@Override
	public void updateMd5Password(Long id, String new_passwd) {
		//更新密码
		this.saveMd5Password(id, new_passwd);
		//修改验证码状态
		User user = this.getUserById(id);
		mobilePhoneCodeService.checkCodeSuccess(user.getPhone());
	}

	private void saveMd5Password(Long id, String new_passwd) {
        User user = this.getUserById(id);
        user.setPassword(new_passwd);
        update(user);
	}

	@Override
	public void updateUser(User user) {
        user.setUpdateTime(new Date());
		userDao.update(user);
	}

    @Override
    public PersonalParty getPersonalPartyById(Long id) {
        return personalPartyDao.get(id);
    }

    @Override
    public void updatePersonalParty(PersonalParty personalParty) {
        personalParty.setUpdateTime(new Date());
        personalPartyDao.update(personalParty);
    }

    @Override
    public void savePersonalParty(PersonalParty personalParty) {
        personalPartyDao.insert(personalParty);
    }

    @Override
    public void lockOrUnLockUser(Long userId, Integer status,Long partyId) {
        User user = userDao.get(userId);
        if(!user.getPartyId().equals(partyId)){
           return;
        } else{
            if(user.getAdmin() ==1){
               return;
            }
            user.setUserStatus(status);
            userDao.update(user);
        }
    }

    @Override
    public void savePassword(User user, Long userId, String password1, String password2) {
        User updateUser = getUserById(userId);
        if (!user.getPartyId().equals(updateUser.getPartyId())) {
            throw new B2bException("用户无限修改密码");
        }
        if (!password1.equals(password2)) {
            throw new B2bException("两次输入密码不一致");
        }
        String newPwd = new Md5PasswordEncoder().encodePassword(password1, updateUser.getLoginName());
        updateUser.setPassword(newPwd);
        userDao.update(updateUser);
    }

    @Override
    public boolean checkLoginNameRepeat(String loginName, Long userId) {
        User user = getUserByLoginName(loginName);
        if (user == null) {
            return true;
        }
        if (user != null && user.getId().equals(userId)) {
            return true;
        }
        return false;
    }

    @Override
    public void saveOrUpdateUser(User currentUser, User newUser, Long roleId) {
        Date now = new Date();
        if (newUser.getId() == null) {//新增
            newUser.setCreateTime(now);
            newUser.setAdmin(IUserType.NOT_ADMIN);
            newUser.setCreateUserId(currentUser.getId());
            newUser.setUserType(currentUser.getUserType());
            newUser.setUserStatus(IUserType.STATUS_NORMAL);
            newUser.setPartyId(currentUser.getPartyId());
            String newPwd = new Md5PasswordEncoder().encodePassword(newUser.getPassword(), newUser.getLoginName());
            newUser.setPassword(newPwd);
            newUser.setUpdateTime(new Date());
            userDao.insert(newUser);
            saveUserRoleForCommonUser(newUser.getId(),roleId);
        } else {//修改
            User dbUser = userDao.get(newUser.getId());
            dbUser.setName(newUser.getName());
            dbUser.setPhone(newUser.getPhone());
            dbUser.setEmail(newUser.getEmail());
            dbUser.setUpdateTime(new Date());
            userDao.update(dbUser);
            clearUserRoles(newUser.getId());
            saveUserRoleForCommonUser(newUser.getId(),roleId);

        }
    }

    private void saveUserRoleForCommonUser(long userId, Long roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRoleDao.insert(userRole);
    }

    public void clearUserRoles(Long userId) {
        userDao.clearUserRole(userId);
    }

	@Override
	public int getUnderTK(String inviteCode) {
        return personalPartyDao.getInviteCount(inviteCode);
	}

    @Override
    public void modifyPhone(User user, UserLoginStatus userLoginStatus) {
        userDao.update(user);
        userLoginStatusDao.update(userLoginStatus);
    }

    @Override
    public User getPartyAdminUser(Long partyId) {
        if(partyId==null){
            return null;
        }
        User user = userDao.getUserByPartyId(partyId);
        return user;
    }

    @Override
    public User getTuikePartyAdminUser(Long partyId) {
        if(partyId==null){
            return null;
        }
        User user = userDao.getTuikeUserByPartyId(partyId);
        return user;
    }

    @Override
    public PersonalParty getPersonalPartyByInviteCode(String inviteCode) {
        Map<String, Object> filters = new HashMap<String, Object>();
        filters.put("inviteCodeMine", inviteCode);
        List<PersonalParty> list = personalPartyDao.findListForMap(filters);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public boolean isExistTopTuike(Long userId) {
        User user = userDao.get(userId);
        if(user==null){
            return false;
        }
        if(user.getPartyId()==null){
            return false;
        }
        PersonalParty personalParty =  personalPartyDao.get(user.getPartyId());
        if(personalParty.getInviteCode()==null || "".equals(personalParty.getInviteCode())){
            return false;
        }
        //根据邀请码获取用户
        PersonalParty personalParty1 = getPersonalPartyByInviteCode(personalParty.getInviteCode());
        if(personalParty1!=null){
            return true;
        }
        return false;
    }

    @Override
    public Long getTopTuikeId(Long userId) {
        User user = userDao.get(userId);
        if(user==null){
            return null;
        }
        if(user.getPartyId()==null){
            return null;
        }
        PersonalParty personalParty =  personalPartyDao.get(user.getPartyId());
        if(personalParty.getInviteCode()==null || "".equals(personalParty.getInviteCode())){
            return null;
        }
        //根据邀请码获取用户
        PersonalParty personalParty1 = getPersonalPartyByInviteCode(personalParty.getInviteCode());
        if(personalParty1==null){
            return null;
        }
        Long parentPartyId = personalParty1.getId();
        Map<String, Object> filters = new HashMap<String, Object>();
        filters.put("partyId", parentPartyId);
        List<User> list = userDao.findListForMap(filters);
        if(list!=null && list.size()>0){
            return list.get(0).getId();
        }
        return null;
    }

}
