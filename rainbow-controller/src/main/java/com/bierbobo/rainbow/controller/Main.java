package com.bierbobo.rainbow.controller;

import com.bierbobo.rainbow.service.X2Service;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by lifubo on 2016/11/28.
 */

/*

1. 起名规则
2. 如何扫描、注入： context
3. sping es的无法发现
4. beans各个属性及其含义
5. 事务理解

Autowired 属性及其注册机制


注解的使用
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented

@interface


使用spring 4
controll  大写
service  小写
没有配置 annotation-config

 */

public class Main {
    public static void main(String[] args) {

        try {
            ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("/spring.xml");
            XXXController bean = appContext.getBean(XXXController.class);
           XXXController ss = (XXXController) appContext.getBean("XXXController");
            X2Service ss1 = (X2Service) appContext.getBean("x2Service");
            System.out.println(bean);
            System.out.println(ss1);
            System.out.println(ss.baseService);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
