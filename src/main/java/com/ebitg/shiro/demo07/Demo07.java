package com.ebitg.shiro.demo07;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

import com.ebitg.shiro.util.LoginUtils;

/**
 * 对授权进行测试
 * 
 * @author Administrator
 *
 */
public class Demo07 {
	@Test
	public void testIsPermitted() {
		String fileName = "classpath:com/ebitg/shiro/demo07/shiro.ini";
		LoginUtils.login(fileName, "zhang", "123");
		Subject subject = SecurityUtils.getSubject();
		Assert.assertTrue(subject.isPermitted("user:create"));
		Assert.assertTrue(subject.isPermittedAll("user:create", "user:update"));
	}

	@Test
	public void testIsPermittedNo() {
		String fileName = "classpath:com/ebitg/shiro/demo07/shiro.ini";
		LoginUtils.login(fileName, "zhang", "123");
		Subject subject = SecurityUtils.getSubject();
		Assert.assertFalse(subject.isPermitted("user:view"));
	}

	@Test
	public void testCheckPermission() {
		String fileName = "classpath:com/ebitg/shiro/demo07/shiro.ini";
		LoginUtils.login(fileName, "zhang", "123");
		Subject subject = SecurityUtils.getSubject();
		subject.checkPermission("user:create");
	}

	@Test(expected = UnauthorizedException.class)
	public void testCheckPermissionException() {
		String fileName = "classpath:com/ebitg/shiro/demo07/shiro.ini";
		LoginUtils.login(fileName, "zhang", "123");
		Subject subject = SecurityUtils.getSubject();
		subject.checkPermission("user:view");
	}
}
