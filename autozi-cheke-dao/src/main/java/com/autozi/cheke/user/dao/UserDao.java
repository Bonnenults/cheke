package com.autozi.cheke.user.dao;
import com.autozi.cheke.user.mapper.UserMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import com.autozi.cheke.user.entity.User;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDao extends MyBatisDao<User> {
    public User getUserByLoginName(String loginName){
        return getMapper(UserMapper.class).getUserByLoginName(loginName);
    }

    public List<User> findList(Map<String,Object> filters){
        return getMapper(UserMapper.class).findList(filters);
    }

	public User findUserByLoginName(String phone) {
        return getMapper(UserMapper.class).findByPhone(phone);
	}

    public void clearUserRole(Long userId) {
        getMapper(UserMapper.class).deleteUserRole(userId);
    }

    public User getUserByPartyId(Long partyId) {
        return getMapper(UserMapper.class).getUserByPartyId(partyId);
    }

    public User getTuikeUserByPartyId(Long partyId) {
        return getMapper(UserMapper.class).getTuikeUserByPartyId(partyId);
    }
}
