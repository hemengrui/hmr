package com.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.health.UserService;
import com.health.dao.PermissionDao;
import com.health.dao.RoleDao;
import com.health.dao.UserDao;
import com.health.pojo.Permission;
import com.health.pojo.Role;
import com.health.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author HMR
 * @create 2021/8/22 9:44
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao ;
    @Autowired
    private PermissionDao permissionDao ;
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
            if(user == null){
                 return null;
            }
            Integer userId = user.getId();
            Set<Role> roles = roleDao.findByUserId(userId);
            if(roles != null && roles.size() > 0){
                for(Role role : roles){
                     Integer roleId = role.getId();
                Set<Permission> permissions = permissionDao.findByRoleId(roleId);
                    if(permissions != null && permissions.size() > 0){
                        role.setPermissions(permissions);
                        }
                 }
                user.setRoles(roles);
            }
            return user;
    }

}
