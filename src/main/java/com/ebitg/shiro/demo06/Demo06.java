package com.ebitg.shiro.demo06;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
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
public class Demo06 {
	@Test
	public void testHasRole() {
		String fileName = "classpath:com/ebitg/shiro/demo06/shiro.ini";
		LoginUtils.login(fileName, "zhang", "123");
		Subject subject = SecurityUtils.getSubject();
		Assert.assertTrue(subject.hasRole("role1"));
		Assert.assertTrue(subject.hasAllRoles(Arrays.asList("role1", "role2")));
		Assert.assertArrayEquals(new boolean[] { true, true, false },
				subject.hasRoles(Arrays.asList("role1", "role2", "role3")));
	}

	@Test()
	public void testCheckRole() {
		String fileName = "classpath:com/ebitg/shiro/demo06/shiro.ini";
		LoginUtils.login(fileName, "zhang", "123");
		Subject subject = SecurityUtils.getSubject();
		subject.checkRole("role1");
	}

	@Test(expected = UnauthorizedException.class)
	public void testCheckRoles() {
		String fileName = "classpath:com/ebitg/shiro/demo06/shiro.ini";
		LoginUtils.login(fileName, "zhang", "123");
		Subject subject = SecurityUtils.getSubject();
		subject.checkRoles("role1", "role3");
	}
}
