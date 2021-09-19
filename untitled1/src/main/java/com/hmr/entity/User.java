package com.hmr.entity;

import lombok.Data;

/**
 * @author HMR
 * @create 2021/9/7 11:41
 */
@Data
public class User {
    private String name;
    private Integer password;


    public User(String name, Integer password) {
        this.name=name;
        this.password=password;
    }
}
