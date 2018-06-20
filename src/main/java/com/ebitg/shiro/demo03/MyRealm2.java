package com.ebitg.shiro.demo03;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

public class MyRealm2 implements Realm {

	@Override
	public String getName() {
		return "myrealm1";
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal(); // �õ��û���
		String password = new String((char[]) token.getCredentials()); // �õ�����
		if (!"wang".equals(username)) {
			throw new UnknownAccountException(); // ����û�������
		}
		if (!"321".equals(password)) {
			throw new IncorrectCredentialsException(); // ����������
		}
		// ��������֤��֤�ɹ�������һ��AuthenticationInfoʵ�֣�
		return new SimpleAuthenticationInfo(username, password, getName());
	}

}
