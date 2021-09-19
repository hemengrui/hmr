package com.health.entity;

/**
 * @author HMR
 * @create 2021/9/7 14:41
 */
public class Cao {
    private String name;
    private String pass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    public Cao() {
    }
    public Cao(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }
}
