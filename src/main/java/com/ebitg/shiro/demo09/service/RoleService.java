package com.ebitg.shiro.demo09.service;

import com.ebitg.shiro.demo09.entity.Role;

public interface RoleService {
	public Role createRole(Role role);

	public void deleteRole(Long roleId);

	public void correlationPermission(Long roleId, Long... permissionIds);

	public void uncorrelationPermission(Long roleId, Long... permissionIds);
}
