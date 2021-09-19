package com.health.dao;

import com.health.pojo.User;

/**
 * @author HMR
 * @create 2021/8/22 9:48
 */
public interface UserDao {
      public User findByUsername(String username);

}
