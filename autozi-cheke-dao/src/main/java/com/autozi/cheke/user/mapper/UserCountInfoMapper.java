package com.autozi.cheke.user.mapper;

import com.autozi.cheke.user.entity.UserCountInfo;
import com.autozi.common.dal.mapper.BaseMapper;


public interface UserCountInfoMapper extends BaseMapper<UserCountInfo> {
    public UserCountInfo getUserCountInfoByUserId(Long userId);
}
