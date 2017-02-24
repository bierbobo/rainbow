package com.bierbobo.rainbow.data.nosql.es.main;

import com.bierbobo.rainbow.data.nosql.es.domain.UserBean;
import com.bierbobo.rainbow.data.nosql.es.repository.OrderRepositoryImpl;
import com.bierbobo.rainbow.data.nosql.es.repository.UserRepository;
import com.bierbobo.rainbow.data.orm.mybatis.dao.UserMapper;
import com.bierbobo.rainbow.data.orm.mybatis.main.DBTools;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

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

        UserRepository userRepository = (UserRepository) context.getBean("userRepository");
        UserBean user = new UserBean("11","张三", "1314520", 7000.0);
        userRepository.save(user);

        System.out.println(userRepository.findAll());
    }




}
