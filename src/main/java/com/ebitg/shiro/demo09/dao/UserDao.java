package com.ebitg.shiro.demo09.dao;

import com.ebitg.shiro.demo09.entity.User;

public interface UserDao {

	User createUser(User user);

	User findOne(Long userId);

	void updateUser(User user);

}
