package com.autozi.cheke.service.user;


import com.autozi.cheke.user.dao.UserCountInfoDao;
import com.autozi.cheke.user.entity.UserCountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/6/15 17:51
 * @Description:
 */
@Service
public class UserCountInfoService implements IUserCountInfoService{
    @Autowired
    private UserCountInfoDao userCountInfoDao;


    @Override
    public UserCountInfo getUserCountInfo(Long id) {
        return userCountInfoDao.get(id);
    }

    @Override
    public void updateUserCountInfo(UserCountInfo userCountInfo){
        userCountInfoDao.update(userCountInfo);
    }

    @Override
    public void createUserCountInfo(UserCountInfo userCountInfo){
        userCountInfoDao.insert(userCountInfo);
    }


}
