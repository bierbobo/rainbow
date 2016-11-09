package com.bierbobo.rainbow.taskschedule;

import com.bierbobo.rainbow.service.exportData.ExportServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by lifubo on 2016/10/23.
 */
public class Test {


    public static void main(String[] args) {

        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("/spring/spring-task.xml");
        ExportServiceImpl exportTask = (ExportServiceImpl) appContext.getBean("exportTask");
        try {
            System.out.println("111");
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
