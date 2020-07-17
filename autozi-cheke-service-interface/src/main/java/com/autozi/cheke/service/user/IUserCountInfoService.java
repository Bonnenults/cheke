package com.autozi.cheke.service.user;

import com.autozi.cheke.user.entity.UserCountInfo;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/6/15 17:51
 * @Description:
 */

@Component
public interface IUserCountInfoService {

    UserCountInfo getUserCountInfo(Long id);

    void updateUserCountInfo(UserCountInfo userCountInfo);

    void createUserCountInfo(UserCountInfo userCountInfo);
}
