package com.autozi.cheke.service.user;

import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.user.entity.*;
import com.autozi.common.core.page.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 *
 */
@Component
public interface IUserService {

    public User getUserById(long userId);

    User getUserByLoginName(String loginName);

    /**
     * 分页查询
     *
     * @param page    分页
     * @param filters 查询条件
     * @return 用户列表
     */
    public Page<User> findPageForMap(Page<User> page, Map<String, Object> filters);

    /**
     * 查询用户列表
     *
     * @param filters 查询条件
     * @return 用户列表
     */
    public List<User> listUsers(Map<String, Object> filters);

    public void save(User user);

    public void registerCheke(User user,Party party);

    public void update(User user);

    public boolean checkLoginName(String loginName);

    public boolean checkMobileExists(User user);

    public Role getRoleByUserId(Long userId);

    //********************推客用户接口 begin****************************
	public Long registerUser(String phone, String phonePwd,String inviteCode);

	public User findUserByLoginName(String phone);

	public void updateMd5Password(Long id, String new_passwd);

	public void updateUser(User user);

    public PersonalParty getPersonalPartyById(Long id);

    public void updatePersonalParty(PersonalParty personalParty);

    public void savePersonalParty(PersonalParty personalParty);

    public boolean isExistInviteCodeMine(String inviteCode);
    
    public int getUnderTK(String inviteCode);

    public void modifyPhone(User user, UserLoginStatus userLoginStatus);

    public User getPartyAdminUser(Long partyId);

    public User getTuikePartyAdminUser(Long partyId);

    public PersonalParty getPersonalPartyByInviteCode(String inviteCode);

    public boolean isExistTopTuike(Long userId);

    public Long getTopTuikeId(Long userId);
    //********************推客用户接口 end****************************

    void lockOrUnLockUser(Long userId, Integer status,Long partyId);

    void savePassword(User user, Long userId, String password1, String password2);

    boolean checkLoginNameRepeat(String loginName, Long userId);

    void saveOrUpdateUser(User currentUser, User newUser, Long roleId);

}
