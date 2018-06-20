package com.ebitg.shiro.demo01;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.env.DefaultEnvironment;
import org.apache.shiro.env.Environment;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 * �������Shiro ʹ�� Subject.login(AuthentictionToken)���е�¼�ķ���
 * 
 * @author Administrator
 *
 */
public class Demo01 {
	@Test
	public void test01() {
		// 1����ȡSecurityManager�������˴�ʹ��Ini�����ļ���ʼ��SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:com/ebitg/shiro/demo01/shiro.ini");
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

	@Test
	public void test02() {
		// 1����ȡSecurityManager�������˴�ʹ��Ini�����ļ���ʼ��SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:com/ebitg/shiro/demo01/shiro.ini");
		// 2���õ�SecurityManagerʵ�� ���󶨸�SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		// 3���õ�Subject�������û���/���������֤Token�����û����/ƾ֤��
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("li", "123");
		try {
			// 4����¼���������֤
			subject.login(token);
		} catch (AuthenticationException e) {
			// 5�������֤ʧ��
		}
		Assert.assertEquals(false, subject.isAuthenticated()); // �����û��Ѿ���¼
		// 6���˳�
		subject.logout();
	}

	@Test
	public void test03() {
		// 1����ȡSecurityManager�������˴�ʹ��Ini�����ļ���ʼ��SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:com/ebitg/shiro/demo01/shiro.ini");
		DefaultEnvironment env = new DefaultEnvironment();
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
}
