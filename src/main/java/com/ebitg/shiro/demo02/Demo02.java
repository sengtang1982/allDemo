package com.ebitg.shiro.demo02;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 * ʹ��Realm������֤
 * 
 * @author Administrator
 *
 */
public class Demo02 {
	@Test
	public void test01() {
		// 1����ȡSecurityManager�������˴�ʹ��Ini�����ļ���ʼ��SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:com/ebitg/shiro/demo02/shiro.ini");
		// 2���õ�SecurityManagerʵ�� ���󶨸�SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		// 3���õ�Subject�������û���/���������֤Token�����û����/ƾ֤��
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		try {
			// 4����¼���������֤
			subject.login(token);
		} catch (AuthenticationException e) {
			// 5�������֤ʧ��
		}
		Assert.assertEquals(true, subject.isAuthenticated()); // �����û��Ѿ���¼
		// 6���˳�
		subject.logout();
	}

	@Test(expected = IncorrectCredentialsException.class)
	public void test02() {
		// 1����ȡSecurityManager�������˴�ʹ��Ini�����ļ���ʼ��SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:com/ebitg/shiro/demo02/shiro.ini");
		// 2���õ�SecurityManagerʵ�� ���󶨸�SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		// 3���õ�Subject�������û���/���������֤Token�����û����/ƾ֤��
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "1234");
		// 4����¼���������֤
		subject.login(token);
	}

	@Test(expected = UnknownAccountException.class)
	public void test03() {
		// 1����ȡSecurityManager�������˴�ʹ��Ini�����ļ���ʼ��SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:com/ebitg/shiro/demo02/shiro.ini");
		// 2���õ�SecurityManagerʵ�� ���󶨸�SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		// 3���õ�Subject�������û���/���������֤Token�����û����/ƾ֤��
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("li", "1234");
		// 4����¼���������֤
		subject.login(token);
	}
}
