package com.health.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.UserService;
import com.health.pojo.Permission;
import com.health.pojo.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author HMR
 * @create 2021/8/22 9:30
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.health.pojo.User  user=userService.findByUsername(username);
        if(user==null){
            return null;
        }
        List<GrantedAuthority> list=new ArrayList<>();
        Set<Role> role=user.getRoles();
        for(Role role1 : role){
            //授予角色
            list.add(new SimpleGrantedAuthority(role1.getKeyword()));
            Set<Permission> permissions = role1.getPermissions();

            for(Permission permission: permissions){
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }

        return new User(username,user.getPassword(),list);

    }
}
