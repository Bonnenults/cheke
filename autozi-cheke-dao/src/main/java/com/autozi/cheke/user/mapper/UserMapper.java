package com.autozi.cheke.user.mapper;
import com.autozi.common.dal.mapper.BaseMapper;
import com.autozi.cheke.user.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User> {
    public User getUserByLoginName(String loginName);
    public List<User> findList(Map<String,Object> filters);
	public User findByPhone(String phone);
    void deleteUserRole(Long userId);
    public User getUserByPartyId(Long partyId);
    public User getTuikeUserByPartyId(Long partyId);
}
