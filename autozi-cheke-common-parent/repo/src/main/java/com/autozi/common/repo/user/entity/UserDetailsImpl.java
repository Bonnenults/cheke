package com.autozi.common.repo.user.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * Author: clover_4l
 * Date: 11-5-13
 * Time: 上午10:44
 */
public class UserDetailsImpl extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 3244770350501840299L;

    private long userId;
    private long partyId;
//    private int partyType;
    private int userType;

    public UserDetailsImpl(IUser user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getLoginName(), user.getPassword(), true, true, true, true, authorities);
        this.userId = user.getId();
        this.partyId = user.getPartyId();
//        this.partyType = user.getPartyType() == null ? 0 : user.getPartyType();
        this.userType = user.getUserType();
    }

    public long getUserId() {
        return userId;
    }

    public long getPartyId() {
        return partyId;
    }

//    public int getPartyType() {
//        return partyType;
//    }
    
    public int getUserType() {
      return userType;
  }


}
