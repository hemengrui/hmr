package com.health.dao;

import com.health.pojo.Permission;

import java.util.Set;

/**
 * @author HMR
 * @create 2021/8/22 9:51
 */
public interface PermissionDao {
    Set<Permission> findByRoleId(int roleId);

}
