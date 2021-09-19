package com.health.dao;

import com.health.pojo.Role;

import java.util.Set;

/**
 * @author HMR
 * @create 2021/8/22 9:50
 */
public interface RoleDao {
     Set<Role> findByUserId(int id);

}
