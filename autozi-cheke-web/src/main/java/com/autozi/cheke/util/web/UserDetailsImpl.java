package com.autozi.cheke.util.web;

import com.autozi.cheke.user.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 继承security user信息完善自定义user交给security处理
 * @author shixin.zhang
 *
 */
public class UserDetailsImpl extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 3244770350501840299L;

    private long userId;
    private long partyId;
//    private int partyType;
    private int userType;

    public UserDetailsImpl(User user, Collection<? extends GrantedAuthority> authorities) {
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
