package com.ebitg.shiro.demo09.entity;

import lombok.Data;

@Data
public class User {
	public String salt;
	public String password;
	public String credentialsSalt;
	private String username;

	public String getCredentialsSalt() {
		return username + salt;
	}
}
