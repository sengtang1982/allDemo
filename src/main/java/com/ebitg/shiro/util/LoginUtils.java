package com.ebitg.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class LoginUtils {
	/**
	 * ͨ�õ�¼����
	 * 
	 * @param fileName
	 * @param password
	 * @param name
	 */
	public static void login(String fileName, String name, String password) {
		try {
			// 1����ȡSecurityManager�������˴�ʹ��Ini�����ļ���ʼ��SecurityManager
			Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(fileName);
			// 2���õ�SecurityManagerʵ�� ���󶨸�SecurityUtils
			org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
			SecurityUtils.setSecurityManager(securityManager);
			// 3���õ�Subject�������û���/���������֤Token�����û����/ƾ֤��
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(name, password);
			// 4����¼���������֤
			subject.login(token);
		} catch (AuthenticationException e) {
		}
	}
}
