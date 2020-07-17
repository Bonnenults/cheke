package com.autozi.cheke.web.party.facade;

import com.autozi.cheke.service.party.IChekeLetterService;
import com.autozi.cheke.service.party.IPartyService;
import com.autozi.cheke.service.user.IUserService;
import com.autozi.common.cache.jedis.proxy.RedisProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lbm on 2018/2/23.
 */
@Component
public class ChekeLetterRedisFacade {
    @Autowired
    private RedisProxy redisProxy;
    @Autowired
    private IChekeLetterService chekeLetterService;
    @Autowired
    private IPartyService partyService;
    @Autowired
    private IUserService userService;
}
