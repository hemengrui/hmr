package com.hmr.beanfactorypostprocessort;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Import;

/**
 * @author HMR
 * @create 2021/9/12 12:21
 */

public class BFPPT implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("我实现了扩展postProcessBeanFactory方法");
    }
}
