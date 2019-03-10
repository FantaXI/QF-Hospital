package com.xlq.hospital.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.xlq.hospital.model.User;
import com.xlq.hospital.service.IUserService;

public class MyRealm extends AuthorizingRealm {
	@Autowired
	private IUserService userService;
	
	//权限验证
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		 User user = (User) principals.getPrimaryPrincipal();
	     SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
	     String roleType = user.getRoleType();
	     if ("1".equals(roleType)) {
	    	 authorizationInfo.addRole("patient");
		}else if ("2".equals(roleType)) {
			authorizationInfo.addRole("doctor");
		}else if ("3".equals(roleType)) {
			authorizationInfo.addRole("admin");
		}     
		return authorizationInfo;
	}

	//登录验证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 获取用户输入的用户名(手机号码)和密码
        String userName = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        //通过用户名从数据库中查找用户
		User user = userService.getUserByMobile(userName);
		if (user == null) {
			throw new UnknownAccountException("该用户不存在！");
		}
		if (!password.equals(user.getPassword())) {
			throw new IncorrectCredentialsException("用户名或密码输入错误！");
		}
		if ("X".equals(user.getState())) {
			throw new LockedAccountException("账号已被锁定,请联系管理员！");
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
		return info;
	}

}
