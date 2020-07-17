package com.autozi.cheke.web.system;


import com.autozi.cheke.service.user.IUserService;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.user.type.IUserType;
import com.autozi.cheke.util.web.UserDetailsImpl;
import com.autozi.common.core.utils.ApplicationContextProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

/**
 * spring  用户实现类
 * @author long.jin
 */
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
         if(username==null||username==""){
			 throw new BadCredentialsException("请输入账户名和密码！");
		 }
		User user = ApplicationContextProvider.getBean(IUserService.class).getUserByLoginName(username);
		if (user == null) {
			throw new BadCredentialsException("用户不存在！");
		}
		if (user.getUserType().intValue() == IUserType.USER_TYPE_TUIKE) {
			throw new BadCredentialsException("用户不存在！");
		}
		if (user.getUserStatus().equals(IUserType.STATUS_STOP)) {
			throw new BadCredentialsException("用户已锁定，不能登录！");
		}
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
//            for (String systemRole : systemRoles) {
//                grantedAuthorities.add(new GrantedAuthorityImpl(systemRole));
//            }
        return new UserDetailsImpl(user, grantedAuthorities);
//        } else {
//            throw new AuthenticationServiceException("用户“" + username
//                    + "”被锁定或删除！请联系管理员");
//        }
       
	}
	


}
