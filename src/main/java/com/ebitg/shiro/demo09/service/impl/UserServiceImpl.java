package com.ebitg.shiro.demo09.service.impl;

import java.util.Set;

import com.ebitg.shiro.demo09.dao.UserDao;
import com.ebitg.shiro.demo09.entity.User;
import com.ebitg.shiro.demo09.service.PasswordHelper;
import com.ebitg.shiro.demo09.service.UserService;

public class UserServiceImpl implements UserService {

	private UserDao userDao;
	private PasswordHelper passwordHelper;

	@Override
	public User createUser(User user) {
		passwordHelper.encryptPassword(user);
		return userDao.createUser(user);
	}

	@Override
	public void changePassword(Long userId, String newPassword) {
		User user = userDao.findOne(userId);
		user.setPassword(newPassword);
		passwordHelper.encryptPassword(user);
		userDao.updateUser(user);
	}

	@Override
	public void correlationRoles(Long userId, Long... roleIds) {
		// TODO Auto-generated method stub

	}

	@Override
	public void uncorrelationRoles(Long userId, Long... roleIds) {
		// TODO Auto-generated method stub

	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> findRoles(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> findPermissions(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
