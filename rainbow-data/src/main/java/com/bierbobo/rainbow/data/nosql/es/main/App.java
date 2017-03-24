package com.bierbobo.rainbow.data.nosql.es.main;

import com.bierbobo.rainbow.data.nosql.es.domain.UserIndex;
import com.bierbobo.rainbow.data.nosql.es.repository.UserRepository;
import com.bierbobo.rainbow.data.nosql.es.repository.UserRepository2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {

        springRun();
//        mainRun();

    }


    private static void springRun() throws Exception {
        String paths[] = {"classpath:spring/applicationContext.xml"};
        ApplicationContext context = new ClassPathXmlApplicationContext(paths);
//        OrderRepositoryImpl s = (OrderRepositoryImpl) context.getBean("orderRepositoryImpl");
//        s.createOrderIndex();

//        UserRepository userRepository = (UserRepository) context.getBean("userRepository");
        UserRepository2 userRepository2 = (UserRepository2) context.getBean("userRepository2");
        userRepository2.deleteIndex();
//        UserIndex user = new UserIndex("11","张三", "1314520", 7000.0);
//        userRepository.save(user);
//        System.out.println(userRepository.findAll());
    }




}
