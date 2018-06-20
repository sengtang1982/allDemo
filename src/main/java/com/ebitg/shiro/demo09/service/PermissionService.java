package com.ebitg.shiro.demo09.service;

import org.apache.shiro.authz.Permission;

public interface PermissionService {
	public Permission createPermission(Permission permission);

	public void deletePermission(Long permissionId);
}
