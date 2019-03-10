package com.xlq.hospital.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
@Configuration
public class ShiroConfig {
	/**
	 * 拦截器
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 登录成功后要跳转的链接
		//shiroFilterFactoryBean.setSuccessUrl("/home/main");
		// 未授权界面
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		// 如果不设置默认会自动寻找Web工程根目录下的"/login"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        
		// 拦截器
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置不会被拦截的链接 从上到下顺序判断
		filterChainDefinitionMap.put("/static/**", "anon");

		// 待添加拦截页面
		//配置认证通过可以访问的地址
        filterChainDefinitionMap.put("/home/*", "anon");
		// 配置退出过滤器
		//filterChainDefinitionMap.put("/logout", "logout");
		
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}
	
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(myShiroRealm());
		return securityManager;
	}
	/**
     * 身份认证realm; (账号密码校验；权限等)
     * 
     * @return
     */
	@Bean
	public MyRealm myShiroRealm() {
		MyRealm myRealm = new MyRealm();
		return myRealm;
	}
}
