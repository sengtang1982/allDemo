package com.ebitg.shiro.demo05;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ����֤���Խ��в���
 * 
 * @author Administrator
 *
 */
public class Demo05 {
	private static final Logger LOG = LoggerFactory.getLogger(Demo05.class);

	@Test
	public void testAllSuccessfullStrategyWithSuccess() {
		login("classpath:com/ebitg/shiro/demo05/shiro1.ini", "zhang", "123");
		Subject subject = SecurityUtils.getSubject();
		PrincipalCollection principals = subject.getPrincipals();
		for (Object principal : principals) {
			LOG.debug(principal.toString());
		}
	}

	@Test
	public void testAllSuccessfullStrategyWithFail() {
		login("classpath:com/ebitg/shiro/demo05/shiro1.ini", "wang", "123");
		Subject subject = SecurityUtils.getSubject();
		PrincipalCollection principals = subject.getPrincipals();
		Assert.assertNull(principals);
	}

	@Test
	public void testAtLeastOneSuccessfulStrategyWithFail() {
		login("classpath:com/ebitg/shiro/demo05/shiro2.ini", "wang", "123");
		Subject subject = SecurityUtils.getSubject();
		PrincipalCollection principals = subject.getPrincipals();
		for (Object principal : principals) {
			LOG.debug(principal.toString());
		}
	}

	/**
	 * ͨ�õ�¼����
	 * 
	 * @param fileName
	 * @param password
	 * @param name
	 */
	public void login(String fileName, String name, String password) {
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
