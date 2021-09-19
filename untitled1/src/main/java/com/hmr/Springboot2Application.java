package com.hmr;


import org.springframework.context.annotation.Import;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author HMR
 * @create 2021/9/7 11:17
 */
@Import(com.hmr.beanfactorypostprocessort.BFPPT.class)
public class Springboot2Application {
    public static void main(String[] args) {
        AbstractApplicationContext classPathXmlApplicationContext =new ClassPathXmlApplicationContext("spring-D.xml");
    }
}
