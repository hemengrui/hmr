package com.health;

import com.health.pojo.User;

/**
 * @author HMR
 * @create 2021/8/22 9:43
 */
public interface UserService {
     public User findByUsername(String username);

}
