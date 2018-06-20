package com.ebitg.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class LoginUtils {
	/**
	 * 通用登录方法
	 * 
	 * @param fileName
	 * @param password
	 * @param name
	 */
	public static void login(String fileName, String name, String password) {
		try {
			// 1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
			Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(fileName);
			// 2、得到SecurityManager实例 并绑定给SecurityUtils
			org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
			SecurityUtils.setSecurityManager(securityManager);
			// 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(name, password);
			// 4、登录，即身份验证
			subject.login(token);
		} catch (AuthenticationException e) {
		}
	}
}
